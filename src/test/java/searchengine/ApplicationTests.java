package searchengine;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import searchengine.restcontrollers.SearchController;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ApplicationTests {

	@Autowired
	private SearchController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
