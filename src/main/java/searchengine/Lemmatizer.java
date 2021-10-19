package searchengine;

import searchengine.http.HTTPGetRequest;
import searchengine.http.IHTTPResponse;

import java.io.IOException;
import java.util.HashMap;

public class Lemmatizer implements ILemmatizer {
    @Override
    public String Lemmatize(String input, String language)
    {
        String url = "https://master01.srv.aau.dk/lemmatizer/";
        HashMap<String, String> queryParameters = new HashMap<>();
        queryParameters.put("language", language);
        queryParameters.put("text", input);

        IHTTPResponse response = new HTTPGetRequest(url, queryParameters).Commit();
        if (response.GetSuccess()) {
            return response.GetContent();
        }
        //TODO possibly log failed requests
        return input;//Return raw input if not succesful lemmatization
    }
}
