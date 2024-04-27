package fax.play.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import fax.play.entity.Country;
import fax.play.entity.Credit;
import fax.play.entity.Genre;
import fax.play.entity.Imdb;
import fax.play.entity.Person;
import fax.play.entity.Title;
import fax.play.entity.Tmdb;
import fax.play.util.CreditDto;
import fax.play.util.DatasetLoader;
import fax.play.util.Platform;
import fax.play.util.TitleDto;

@Service
public class ShowsAndMoviesService {

   private static final Class<?>[] ENTITIES =
         { Country.class, Credit.class, Genre.class, Imdb.class, Person.class, fax.play.entity.Platform.class, Title.class, Tmdb.class };

   private final static int CHUNK_SIZE = 50;

   private SessionFactory sessionFactory;

   @PostConstruct
   public void postConstruct() {
      sessionFactory = Persistence.createEntityManagerFactory("ogm-jpa-tutorial").unwrap(SessionFactory.class);
   }

   @PreDestroy
   public void preDestroy() {
      sessionFactory.close();
   }

   public Map<String, Integer> report() {
      HashMap<String, Integer> report = new LinkedHashMap<>(ENTITIES.length);
      for (Class<?> entity : ENTITIES) {
         String name = entity.getSimpleName();
         int count = count(name);
         report.put(name, count);
      }
      return report;
   }

   public int count(String entityName) {
      try (Session session = sessionFactory.openSession()) {
         Query<Integer> query = session.createQuery("select count(*) from " + entityName);
         List<Integer> list = query.list();
         if (list.isEmpty()) {
            return 0;
         }
         return list.get(0);
      }
   }

   public void load(Platform platform) {
      DatasetLoader loader = new DatasetLoader();
      Stream<List<TitleDto>> titles = loader.titles(platform, CHUNK_SIZE);
      ShowsAndMoviesLogger titlesLogger = new ShowsAndMoviesLogger("titles loaded from " + platform, CHUNK_SIZE);
      try (Session session = sessionFactory.openSession()) {
         titles.forEach(chunk -> {
            Transaction transaction = session.beginTransaction();
            try {
               for (TitleDto titleDto : chunk) {
                  load(session, titleDto, platform);
               }
               session.flush();
               session.clear();
               transaction.commit();
               titlesLogger.chunkExecuted();
            } catch (Exception ex) {
               transaction.rollback();
               titlesLogger.chunkRollback(ex);
            }
         });
         titlesLogger.complete();
      }

      Stream<List<CreditDto>> credits = loader.credits(platform, CHUNK_SIZE);
      ShowsAndMoviesLogger creditsLogger = new ShowsAndMoviesLogger("credits loaded from " + platform, CHUNK_SIZE);
      try (Session session = sessionFactory.openSession()) {
         credits.forEach(chunk -> {
            Transaction transaction = session.beginTransaction();
            try {
               for (CreditDto creditDto : chunk) {
                  load(session, creditDto);
               }
               session.flush();
               session.clear();
               transaction.commit();
               creditsLogger.chunkExecuted();
            } catch (Exception ex) {
               transaction.rollback();
               creditsLogger.chunkRollback(ex);
            }
         });
         creditsLogger.complete();
      }
   }

   private void load(Session session, TitleDto titleDto, Platform platform) {
      fax.play.entity.Platform platformEntity = session.get(fax.play.entity.Platform.class, platform.name());
      if (platformEntity == null) {
         platformEntity = new fax.play.entity.Platform();
         platformEntity.setName(platform.name());
         session.persist(platformEntity);
      }

      String id = titleDto.id();
      Title title = session.get(Title.class, id);
      if (title != null) {
         if (!title.getPlatforms().contains(platformEntity)) {
            title.getPlatforms().add(platformEntity);
            session.persist(title);
         }
         return;
      }

      title = new Title();
      title.setId(id);
      title.setTitle(titleDto.title());
      title.setType(titleDto.type());
      title.setDescription(titleDto.description());
      title.setReleaseYear(titleDto.releaseYear());
      title.setAgeCertification(titleDto.ageCertification());
      title.setRuntime(titleDto.runtime());
      title.setSeasons(titleDto.seasons());

      List<Genre> genres = titleDto.genres().stream().map(name -> {
         Genre genre = session.get(Genre.class, name);
         if (genre == null) {
            genre = new Genre();
            genre.setName(name);
            session.persist(genre);
         }
         return genre;
      }).collect(Collectors.toList());
      title.setGenres(genres);

      List<Country> countries = titleDto.productionCountries().stream().map(name -> {
         Country country = session.get(Country.class, name);
         if (country == null) {
            country = new Country();
            country.setName(name);
            session.persist(country);
         }
         return country;
      }).collect(Collectors.toList());
      title.setCountries(countries);

      ArrayList<fax.play.entity.Platform> platforms = new ArrayList<>();
      platforms.add(platformEntity);
      title.setPlatforms(platforms);
      session.persist(title);

      if (!titleDto.imdbId().isEmpty()) {
         Imdb imdb = new Imdb();
         imdb.setTitle(title);
         imdb.setId(titleDto.imdbId());
         imdb.setScore(titleDto.imdbScore());
         imdb.setVotes(titleDto.imdbVotes());
         session.persist(imdb);
      }

      if (titleDto.tmdbPopularity() != null || titleDto.tmdbScore() != null) {
         Tmdb tmdb = new Tmdb();
         tmdb.setTitle(title);
         tmdb.setId(titleDto.id());
         tmdb.setPopularity(titleDto.tmdbPopularity());
         tmdb.setScore(titleDto.tmdbScore());
         session.persist(tmdb);
      }
   }

   private void load(Session session, CreditDto creditDto) {
      String creditId = Credit.toId(creditDto.personId(), creditDto.titleId(), creditDto.role(), creditDto.character());
      Credit credit = session.get(Credit.class, creditId);
      if (credit != null) {
         // credit already loaded
         return;
      }

      int personId = creditDto.personId();
      Person person = session.get(Person.class, personId);
      if (person == null) {
         // add a person if it does not exist
         person = new Person(personId, creditDto.name());
         session.persist(person);
      }

      Title title = session.get(Title.class, creditDto.titleId());
      // title is supposed to have been imported by the load titles
      // otherwise we cannot create the credit
      if (title == null) {
         return;
      }

      credit = new Credit(person, title, creditDto.role(), creditDto.character());
      session.persist(credit);
   }
}
