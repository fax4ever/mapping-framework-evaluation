package fax.play.rest;

import fax.play.util.Platform;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShowsAndMoviesRestController {

	@PostMapping("/load/{platform}")
	private void load(@PathVariable String string) {
		Platform platform = Platform.find( string );
		if (platform == null) {
			throw new RuntimeException("Platform not found: string");
		}


	}
}
