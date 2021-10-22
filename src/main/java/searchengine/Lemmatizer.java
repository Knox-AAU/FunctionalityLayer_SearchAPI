package searchengine;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import searchengine.http.*;
import searchengine.http.lemmatizer.LemmatizerRequestBody;
import searchengine.http.lemmatizer.LemmatizerResponse;

/**
 * Wrapper for the lemmatization API
 */
public class Lemmatizer implements ILemmatizer {
    /**
     * Lemmatizes the input based on lemmatization rules for the given language.
     * Uses the lemmatizer API
     * @param input
     * @param language
     * @return Lemmatized input string | input if errors
     */
    @Override
    public String Lemmatize(String input, String language)
    {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("LEMMATIZER_URL");

        try {
            IHTTPRequest httpRequest = new HTTPRequest(url);
            httpRequest.SetMethod("POST");
            String body = JsonEncodeInputForLemmatizeRequest(input, language);
            httpRequest.SetBody(body);
            IHTTPResponse response = httpRequest.Commit();
            if (response.GetSuccess()) {
                return JsonDecodeLemmatizeResponse(response.GetContent());
            }
        }
        catch(Exception exception){
            System.err.println(exception.getMessage());
            return input;//Return raw input if not succesful lemmatization
        }
        System.err.println("Lemmatizer request was not successful.");
        return input;//Return raw input if not succesful lemmatization
    }

    /**
     * Creates the json string needed for the lemmatizer request.
     * @param input
     * @param language
     * @return json string
     * @throws JsonProcessingException
     */
    private String JsonEncodeInputForLemmatizeRequest(String input, String language) throws JsonProcessingException {
        return (new ObjectMapper()).writeValueAsString(new LemmatizerRequestBody(input, language));
    }

    /**
     * Decodes the json response from the lemmatizer API returning the lemmatized input
     * @param json
     * @return string
     * @throws JsonProcessingException
     */
    private String JsonDecodeLemmatizeResponse(String json) throws JsonProcessingException {
        return (new ObjectMapper()).readValue(json, LemmatizerResponse.class).lemmatized_string;
    }
}