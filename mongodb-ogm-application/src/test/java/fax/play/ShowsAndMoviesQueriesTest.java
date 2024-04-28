package fax.play;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fax.play.entity.Person;
import fax.play.service.ShowsAndMoviesQueries;

@SpringBootTest
public class ShowsAndMoviesQueriesTest {

   @Autowired
   private ShowsAndMoviesQueries queries;

   @Test
   public void findPeopleByName() {
      List<Person> query = queries.findPeopleByName("Walt Disney");
      assertThat(query).isNotNull();
   }

   @Test
   public void test() {
      String credits = queries.findCreditsByPersonName("Walt Disney");
      assertThat(credits).isNotNull();
   }
}
