package fax.play.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import fax.play.service.ShowsAndMoviesService;
import fax.play.util.Platform;

@RestController
public class ShowsAndMoviesRest {

   @Autowired
   public ShowsAndMoviesService service;

   @PostMapping("/load/{platform}")
   public void load(@PathVariable("platform") String string) {
      Platform platform = Platform.find(string);
      if (platform == null) {
         throw new RuntimeException("Platform not found: string");
      }
      service.load(platform);
   }

   @GetMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
   public Map<String, Integer> report() {
      return service.report();
   }
}
