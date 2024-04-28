package fax.play;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fax.play.service.ShowsAndMoviesQueries;

@SpringBootTest
public class ShowsAndMoviesQueriesTest {

   @Autowired
   private ShowsAndMoviesQueries queries;

   @Test
   public void findCreditsByPersonName() {
      String credits = queries.findCreditsByPersonName("Walt Disney");
      assertThat(credits).isNotNull();
   }

   @Test
   public void titlesOrderByScore() {
      String titles = queries.titlesOrderByScore("fantasy", 1, 10);
      assertThat(titles).isNotNull();
   }
}
