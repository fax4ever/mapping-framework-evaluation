package fax.play.service;

import java.text.MessageFormat;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fax.play.entity.Imdb;
import fax.play.entity.Person;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ShowsAndMoviesQueries {

   private static final String SCORE_AGGREGATION =
   "db.Imdb.aggregate( [ {'$lookup': {'from': 'Title', 'localField': 'title_id', 'foreignField': '_id', 'as': 'title' } }, {'$match': {'title.genres': '{0}' } }, {'$sort': {'score': -1 } } ] )";

   private final Logger logger = LoggerFactory.getLogger(ShowsAndMoviesLogger.class);

   @Autowired
   private ShowsAndMoviesService service;

   @Autowired
   private ObjectMapper objectMapper;

   public String findCreditsByPersonName(String name) {
      try (Session session = service.sessionFactory().openSession()) {
         Query<Person> query = session.createQuery("from Person where name = :name");
         query.setParameter("name", name);
         List<Person> list = query.list();
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

   public String titlesOrderByScore(String genre, int pageNumber, int pageSize) {
      try (Session session = service.sessionFactory().openSession()) {
         String query = SCORE_AGGREGATION.replace("{0}", genre);
         List<Imdb> list = session.createNativeQuery(query)
               .addEntity(Imdb.class)
               .setFirstResult((pageNumber-1) * pageSize)
               .setMaxResults(pageSize)
               .list();

         try {
            return objectMapper.writeValueAsString(list);
         } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
         }
      }
   }
}
