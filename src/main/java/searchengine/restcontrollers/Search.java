package searchengine.restcontrollers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import searchengine.vsm.Document;
import searchengine.vsm.ScoredDocument;
import searchengine.vsm.VectorSpaceModel;
import searchengine.booleanretrieval.DataSelection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;


public class Search {

    private List<ScoredDocument> result;
    private static HttpURLConnection connection;

    public Search(String input) throws IOException, JSONException {
        //input = lemmatize(input);
        List<Document> documents = new DataSelection().retrieveDocuments(input);
        result = new VectorSpaceModel(input).getScoredDocuments(documents);
    }

    public List<ScoredDocument> getResult() {
        return result;
    }

    private String lemmatize(String input) throws IOException, JSONException {

        StringBuilder result = new StringBuilder();
        input = input.replaceAll(" ", "%20");
        URL url = new URL("http://localhost:8082/term/" + input);

        try {
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            StringBuilder content;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {

                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            JSONArray jsonArray = new JSONArray(content.toString());
            for (int i = 0; i < jsonArray.length(); i++){
                result.append(jsonArray.getJSONObject(i).getString(jsonArray.getJSONObject(i).keys().next().toString())).append(" ");
            }

        } finally {
            connection.disconnect();
        }
        return result.toString();
    }

}