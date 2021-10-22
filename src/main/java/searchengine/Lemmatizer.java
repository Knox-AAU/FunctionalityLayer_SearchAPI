package searchengine;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.http.protocol.HTTP;
import searchengine.http.HTTPRequest;
import searchengine.http.IHTTPRequest;
import searchengine.http.IHTTPResponse;

import java.util.HashMap;

public class Lemmatizer implements ILemmatizer {
    @Override
    public String Lemmatize(String input, String language)
    {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("LEMMATIZER_URL");

        try {
            IHTTPRequest httpRequest = new HTTPRequest(url);
            httpRequest.SetMethod("POST");
            httpRequest.SetBody("{'string':'jeg er en test', 'language':'da'}");
            IHTTPResponse response = httpRequest.Commit();
            if (response.GetSuccess()) {
                return response.GetContent();
            }
        }
        catch(Exception exception){
            System.err.println(exception.getMessage());
            return input;
        }
        //TODO possibly log failed requests
        return input;//Return raw input if not succesful lemmatization
    }
}
