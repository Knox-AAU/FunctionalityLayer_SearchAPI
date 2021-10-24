package searchengine;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvBuilder;

import java.io.File;
import java.io.FileNotFoundException;

public class Configuration {
    private static String get(String key) {
        return Dotenv.configure().directory(System.getProperty("user.dir")).load().get(key);
    }

    public Configuration() throws FileNotFoundException {
        String expectedPath = System.getProperty("user.dir") + "/.env";
        File envFile = new File(expectedPath);
        if(!envFile.exists()){
            throw new FileNotFoundException(".env file not found at: " + expectedPath);
        };
    }

    public String DB_CONNECTION_URL = get("DB_CONNECTION_URL");
    public String DB_CONNECTION_USERNAME = get("DB_CONNECTION_USERNAME");
    public String DB_CONNECTION_PASSWORD = get("DB_CONNECTION_PASSWORD");
}
