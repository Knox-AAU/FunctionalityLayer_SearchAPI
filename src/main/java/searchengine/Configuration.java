package searchengine;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvBuilder;

import java.io.File;
import java.io.FileNotFoundException;

public class Configuration {
    /**
     * finds env file in current directory and loads from the key given
     * @param key
     * @return the value of the key in the .env file
     */
    public String get(String key) {
        // finds env file in current directory and loads from the key given
        return Dotenv.configure().directory(System.getProperty("user.dir")).load().get(key);
    }

    public Configuration() throws FileNotFoundException {
        String expectedPath = System.getProperty("user.dir") + "/.env";
        System.out.println(".env should be located at: " + System.getProperty("user.dir") + "/.env");
        File envFile = new File(expectedPath);
        if(!envFile.exists()){
            throw new FileNotFoundException(".env file not found at: " + expectedPath);
        };
    }
}
