package searchengine;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import searchengine.http.*;

public class Lemmatizer implements ILemmatizer {
    @Override
    public String Lemmatize(String input, String language)
    {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("LEMMATIZER_URL");

        try {
            IHTTPRequest httpRequest = new HTTPRequest(url);
            httpRequest.SetMethod("POST");
            String body = (new ObjectMapper()).writeValueAsString(new LemmatizerRequestBody(input, language));
            httpRequest.SetBody(body);
            IHTTPResponse response = httpRequest.Commit();
            if (response.GetSuccess()) {
                LemmatizerResponse lemmatizerResponse = (new ObjectMapper()).readValue(response.GetContent(), LemmatizerResponse.class);
                return lemmatizerResponse.lemmatized_string;
            }
        }
        catch(Exception exception){
            System.err.println(exception.getMessage());
            return input;
        }
        System.err.println("Lemmatizer request was not successful.");
        return input;//Return raw input if not succesful lemmatization
    }
}
