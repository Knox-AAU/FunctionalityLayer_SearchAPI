package searchengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import searchengine.booleanretrieval.DataSelection;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		//SpringApplication.run(Application.class, args);
		DataSelection ds = new DataSelection();
		ds.sendQuery("used forms");
	}
}

