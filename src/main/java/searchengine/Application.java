package searchengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import searchengine.restcontrollers.Search;

import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootApplication
public class Application {
	public static Configuration configuration;

	public static void main(String[] args) throws FileNotFoundException {
		configuration = new Configuration();
		SpringApplication.run(Application.class, args);
	}
}

