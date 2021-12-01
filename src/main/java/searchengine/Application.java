package searchengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;

@SpringBootApplication
public class Application {
	public static Configuration configuration;

	public static void main(String[] args) throws FileNotFoundException {
		configuration = new Configuration();
		SpringApplication.run(Application.class, args);
	}
}

