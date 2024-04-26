package fax.play.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import fax.play.entity.Country;
import fax.play.entity.Credit;
import fax.play.entity.Genre;
import fax.play.entity.Person;
import fax.play.entity.Title;
import fax.play.util.CreditDto;
import fax.play.util.DatasetLoader;
import fax.play.util.Platform;
import fax.play.util.TitleDto;

@Service
public class ShowsAndMoviesService {

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
            session.update(title);
         }
         return;
      }

      title = new Title();
      title.setId(id);
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
   }

   private void load(Session session, CreditDto creditDto) {
      int personId = creditDto.personId();
      Person person = session.get(Person.class, personId);
      if (person == null) {
         // add a person if it does not exist
         person = new Person(personId, creditDto.name());
         session.persist(person);
      }

      String creditId = creditDto.titleId();
      Credit credit = session.load(Credit.class, creditId);
      if (credit == null) {
         credit = new Credit();
         credit.setCharacter(creditDto.character());

      }
   }
}
