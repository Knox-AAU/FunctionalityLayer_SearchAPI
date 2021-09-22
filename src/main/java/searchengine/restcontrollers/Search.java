package searchengine.restcontrollers;

import org.json.JSONArray;
import org.json.JSONException;
import searchengine.vsm.TFIDFDocument;
import searchengine.vsm.ScoredDocument;
import searchengine.vsm.VectorSpaceModel;
import searchengine.booleanretrieval.DataSelection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


/**
 * Encapsulates a search for some documents. The resulting documents are ranked and stored in the result property.
 */
public class Search {

    private final List<ScoredDocument> result;
    // Connection to make a request to the python API
    private static HttpURLConnection connection;

    /**
     * Retrieve documents from database through the DataSelection class.
     * Uses the vector space model on the retrieved documents, and receives a list of scored documents
     * @param input: The search terms that are used to find documents in wordcount database.
     *             The result list is stored in result
     */
    public Search(String input){
        //input = lemmatize(input);
        List<TFIDFDocument> documents = new DataSelection().retrieveDocuments(input);
        result = new VectorSpaceModel(input).getScoredDocuments(documents);
    }

    public List<ScoredDocument> getResult() {
        return result;
    }


    /**
     * lemmatizes the string from the input. should always be called before sending a query to the wordcount database
    * @param input: String received through POST request
    * @return a string containing the lemmatized words
     */
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