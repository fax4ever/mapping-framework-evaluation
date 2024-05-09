package fax.play.rest;

import java.util.Map;

import com.mongodb.util.JSON;
import fax.play.service.ShowsAndMoviesQueries;
import fax.play.service.ShowsAndMoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShowsAndMoviesRest {

	@Autowired
	public ShowsAndMoviesService service;

	@Autowired
	public ShowsAndMoviesQueries queries;

	@PostMapping("/people")
	public void people() {
		service.peoplePipeline();
	}

	@PostMapping("/titles")
	public void titles() {
		service.titlesPipeline();
	}

	@GetMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Long> report() {
		return service.report();
	}

	@PostMapping(value = "/findCredits", produces = MediaType.APPLICATION_JSON_VALUE)
	public String findCreditsByPersonName(@RequestBody String personName) {
		return JSON.serialize(queries.findCreditsByPersonName(personName));
	}

	@GetMapping(value = "/titlesOrderByScore/genre/{genre}/page/{pageNumber}/page-size/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String titlesOrderByScore(@PathVariable("genre") String genre, @PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize) {
		return JSON.serialize(queries.titlesOrderByScore(genre, pageNumber, pageSize));
	}

	@GetMapping(value = "/findMovies/platform/{platform}/start-year/{start-year}/end-year/{end-year}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String findMovies(@PathVariable("platform") String plat, @PathVariable("start-year") int startYear, @PathVariable("end-year") int endYear) {
		return JSON.serialize(queries.findMovies(plat.toUpperCase().replace( "-", "_" ), startYear, endYear));
	}
}
