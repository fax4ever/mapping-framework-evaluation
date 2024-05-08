package fax.play.rest;

import java.util.Map;

import fax.play.service.ShowsAndMoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShowsAndMoviesRest {

	@Autowired
	public ShowsAndMoviesService service;

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
}
