package fax.play.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fax.play.entity.Person;
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
         throw new RuntimeException("Platform not found: string");
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

   @PostMapping(value = "/findPeople", produces = MediaType.APPLICATION_JSON_VALUE)
   public List<Person> findPeopleByName(@RequestBody String name) {
      return queries.findPeopleByName(name);
   }
}
