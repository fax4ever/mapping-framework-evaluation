package fax.play.service;

import java.util.List;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import fax.play.entity.Person;
import fax.play.util.CreditDto;
import fax.play.util.DatasetLoader;
import fax.play.util.Platform;

@Service
public class ShowsAndMoviesService {

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
      Stream<List<CreditDto>> credits = loader.credits(platform);
      try (Session session = sessionFactory.openSession()) {
         credits.forEach(chunk -> {
            Transaction transaction = session.beginTransaction();
            try {
               for (CreditDto creditDto : chunk) {
                  load(session, creditDto);
               }
               transaction.commit();
               session.flush();
            } catch (Exception ex) {
               transaction.rollback();
            }
            session.clear();
         });
      }
   }

   private void load(EntityManager entityManager, CreditDto creditDto) {
      int personId = creditDto.personId();
      Person person = entityManager.find(Person.class, personId);
      if (person == null) {
         // add a person if it does not exist
         person = new Person(personId, creditDto.name());
         entityManager.persist(person);
      }
   }
}
