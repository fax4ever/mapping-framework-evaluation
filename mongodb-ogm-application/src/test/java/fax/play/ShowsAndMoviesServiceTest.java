package fax.play;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fax.play.service.ShowsAndMoviesService;
import fax.play.util.Platform;

@SpringBootTest
public class ShowsAndMoviesServiceTest {

   @Autowired
   private ShowsAndMoviesService service;

   @Test
   public void load() {
      service.load(Platform.NETFLIX);
   }

   @Test
   public void report() {
      Map<String, Integer> report = service.report();
      assertThat(report).isNotEmpty();
   }
}
