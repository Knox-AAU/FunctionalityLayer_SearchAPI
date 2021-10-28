package searchengine.restcontrollers;

import lombok.Getter;
import org.json.JSONArray;
import org.json.JSONException;
import searchengine.ISqlConnection;
import searchengine.Lemmatizer;
import searchengine.booleanretrieval.DataSelection;
import searchengine.vsm.ScoredDocument;
import searchengine.vsm.TFIDFDocument;
import java.net.HttpURLConnection;
import searchengine.vsm.VectorSpaceModel;
import java.util.List;


/**
 * Encapsulates a search for some documents. The resulting documents are ranked and stored in the result property.
 */
public class Search {

  @Getter
  private final List<ScoredDocument> result;

  /**
   * Retrieve documents from database through the DataSelection class.
   * Uses the vector space model on the retrieved documents, and receives a list of scored documents
   * @param input The search terms that are used to find documents in wordcount database.
   *             The result list is stored in result
   * @param sources The list of sources to be searched
   */
  public Search(String input, List<String> sources)
  {
    input = new Lemmatizer().Lemmatize(input);//TODO compute language
    List<TFIDFDocument> documents = new DataSelection().retrieveDocuments(input, sources);
    result = new VectorSpaceModel(input).getScoredDocuments(documents);
  }
}