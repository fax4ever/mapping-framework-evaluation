package fax.play;

import org.junit.jupiter.api.Test;

import fax.play.service.ShowsAndMoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShowsAndMoviesServiceTest {

	@Autowired
	private ShowsAndMoviesService service;

	@Test
	public void peoplePipeline() {
		service.peoplePipeline();
	}

	@Test
	public void titlesPipeline() {
		service.titlesPipeline();
	}
}
