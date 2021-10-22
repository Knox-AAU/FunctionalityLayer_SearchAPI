package searchengine;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import searchengine.restcontrollers.Search;

import java.io.IOException;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws IOException {
		Dotenv.configure().directory("/resources");
		Dotenv.load();
		SpringApplication.run(Application.class, args);
	}
}

