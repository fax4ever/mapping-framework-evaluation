package fax.play;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fax.play.service.ShowsAndMoviesService;
import fax.play.util.Platform;

@SpringBootTest
public class ShowsAndMoviesTest {

   @Autowired
   private ShowsAndMoviesService service;

   @Test
   public void load() {
      service.load(Platform.NETFLIX);
   }
}
