package fax.play.rest;

import fax.play.service.ShowsAndMoviesService;
import fax.play.util.Platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShowsAndMoviesRestController {

	@Autowired
	public ShowsAndMoviesService service;

	@PostMapping("/load/{platform}")
	private void load(@PathVariable String string) {
		Platform platform = Platform.find( string );
		if (platform == null) {
			throw new RuntimeException("Platform not found: string");
		}
		service.load(platform);
	}
}
