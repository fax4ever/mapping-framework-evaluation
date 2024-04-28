package fax.play.service;

import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fax.play.entity.Person;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ShowsAndMoviesQueries {

   private final Logger logger = LoggerFactory.getLogger(ShowsAndMoviesLogger.class);

   @Autowired
   private ShowsAndMoviesService service;

   @Autowired
   private ObjectMapper objectMapper;

   public List<Person> findPeopleByName(String name) {
      try (Session session = service.sessionFactory().openSession()) {
         String nativeQuery = "{ name : '" + name + "' }";
         return session.createNativeQuery(nativeQuery, Person.class).list();
      }
   }

   public String findCreditsByPersonName(String name) {
      try (Session session = service.sessionFactory().openSession()) {
         String nativeQuery = "{ name : '" + name + "' }";
         List<Person> list = session.createNativeQuery(nativeQuery, Person.class).list();
         if (list.isEmpty()) {
            return "[]";
         }

         Person person = list.get(0);
         if (list.size() > 1) {
            logger.warn("There is a homonym for the name {}, we'll return only the titles of person with id {}.", name, person.getId());
         }

         try {
            return objectMapper.writeValueAsString(person.getCredits());
         } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
         }
      }
   }
}
