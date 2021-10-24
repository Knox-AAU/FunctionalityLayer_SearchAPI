package searchengine;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvBuilder;

import java.io.File;
import java.io.FileNotFoundException;

public class Configuration {
    public String get(String key) {
        return Dotenv.configure().directory(System.getProperty("user.dir")).load().get(key);
    }

    public Configuration() throws FileNotFoundException {
        String expectedPath = System.getProperty("user.dir") + "/.env";
        File envFile = new File(expectedPath);
        if(!envFile.exists()){
            throw new FileNotFoundException(".env file not found at: " + expectedPath);
        };
    }
}
