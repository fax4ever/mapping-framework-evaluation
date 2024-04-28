package fax.play.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fax.play.service.ShowsAndMoviesQueries;
import fax.play.service.ShowsAndMoviesService;
import fax.play.util.Platform;

@RestController
public class ShowsAndMoviesRest {

   @Autowired
   public ShowsAndMoviesService service;

   @Autowired
   public ShowsAndMoviesQueries queries;

   @PostMapping("/load/{platform}")
   public void load(@PathVariable("platform") String string) {
      Platform platform = Platform.find(string);
      if (platform == null) {
         throw new RuntimeException("Platform not found: " +  string);
      }
      service.load(platform);
   }

   @GetMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
   public Map<String, Integer> report() {
      return service.report();
   }

   @PostMapping(value = "/findCredits", produces = MediaType.APPLICATION_JSON_VALUE)
   public String findCreditsByPersonName(@RequestBody String personName) {
      return queries.findCreditsByPersonName(personName);
   }

   @GetMapping(value = "/titlesOrderByScore/genre/{genre}/page/{pageNumber}/page-size/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
   public String titlesOrderByScore(@PathVariable("genre") String genre, @PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize) {
      return queries.titlesOrderByScore(genre, pageNumber, pageSize);
   }

   @GetMapping(value = "findMovies/platform/{platform}/start-year/{start-year}/end-year/{end-year}", produces = MediaType.APPLICATION_JSON_VALUE)
   public String findMovies(@PathVariable("platform") String string, @PathVariable("start-year") int startYear, @PathVariable("end-year") int endYear) {
      Platform platform = Platform.find(string);
      if (platform == null) {
         throw new RuntimeException("Platform not found: " +  string);
      }
      return queries.findMovies(platform.name(), startYear, endYear);
   }
}
