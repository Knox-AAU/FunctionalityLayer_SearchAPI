package searchengine.booleanretrieval;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.apache.http.HttpException;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import searchengine.Application;
import searchengine.http.HTTPRequest;
import searchengine.http.IHTTPRequest;
import searchengine.http.IHTTPResponse;
import searchengine.http.database.WordRatioResponseElement;
import searchengine.vsm.TFIDFDocument;

/**
 * Contains the retrieveDocuments function.
 * Is used for retrieving document names.
 */
public class DataSelection {

  /**
     * Searches the wordcount database for documents containing the terms from the input string.
     * @param input The search query in the format "term1 term2 ... termN" (space separated)
     * @param sources The list of sources to be searched
     * @return a list of documents which contained at least one of the search terms
     */
  public List<TFIDFDocument> retrieveDocuments(String input, List<String> sources) {
    List<TFIDFDocument> documents = new ArrayList<>();

    try {
      IHTTPResponse httpResponse = requestDatabaseResponse(input, sources);
      List<WordRatioResponseElement> responseElements = JSONDecodeDataResponse(httpResponse);

      documents = CreateTFIDFDocumentsFromResponseElements(responseElements);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return documents;
  }

  /**
   * Build HTTP request, send it to the database API and return the response.
   * @param input the terms that should be sent to the database api
   * @param sources the different sources that should be sent to the database api
   * @return The result af a HTTP request to the database API
   * @throws HttpException when the HTTP request is not successful
   * @throws HttpRequestMethodNotSupportedException if method is not allowed
   * @throws IOException the http request cannot be sent.
   */
  @NotNull

  private IHTTPResponse requestDatabaseResponse(String input, List<String> sources) throws HttpException, HttpRequestMethodNotSupportedException, IOException {
    String apiEndPoint = Application.configuration.get("DATABASE_API_URL");
    IHTTPRequest http = new HTTPRequest(apiEndPoint);
    http.SetMethod("GET");
    if(input != null) {
      http.AddQueryParameter("terms", input.split(" "));
    }
    if(sources != null) {
      http.AddQueryParameter("sources", sources.toArray(new String[0]));
    }
    IHTTPResponse httpResponse = http.Send();
    if(!httpResponse.GetSuccess()){
      throw new HttpException("Internal server error - " + httpResponse.GetContent());
    }
    return httpResponse;
  }

  /**
   * The responseElements are translated into a list of TF-IDF documents by iterating over the responseElements,
   * adding unique documents to the list and for each word in the documents, calculating their TF value in the document
   * @param responseElements the list of responseElements that should be translated into TF-IDF documents
   * @return a list of TF-IDF documents
   */
  private List<TFIDFDocument> CreateTFIDFDocumentsFromResponseElements(List<WordRatioResponseElement> responseElements) {
    List<TFIDFDocument> documents = new ArrayList<>();
    // The response elements must be sorted using their article title, since the following calculations depend on identical
    // titles following directly after each other.
    responseElements.sort(Comparator.comparing(WordRatioResponseElement::getTitle));

    // Go through all words (sorted by what article they appear in) and make a TF-IDF document for each new article.
    TFIDFDocument currentDocument = null;
    String currentTitle = "";
    for(WordRatioResponseElement element : responseElements) {
      String wordName = element.getWord();
      int amount = element.getCount();
      String title = element.getTitle();

      if (!currentTitle.equals(title)) {
        currentTitle = title;
        int fid = element.getArticleId();
        currentDocument = new TFIDFDocument(title, fid);
        documents.add(currentDocument);
      }

      // Store the TF value of the word in the TF-IDF document
      if(currentDocument != null) {
        currentDocument.getTFmap().put(wordName, amount);
      }
    }
    return documents;
  }

  /**
   * This function decodes from a HTTP response in Json format into WordRatioResponseElement objects
   * @param httpResponse the response to be decoded
   * @return a list of WordRatioResponseElement objects
   * @throws com.fasterxml.jackson.core.JsonProcessingException if the JSON is not legal
   */
  private List<WordRatioResponseElement> JSONDecodeDataResponse(IHTTPResponse httpResponse) throws com.fasterxml.jackson.core.JsonProcessingException {
    ObjectMapper objMapper = new ObjectMapper();
    TypeFactory typeFactory = objMapper.getTypeFactory();

    //Define that the JSON should be read into a List (List.class) of WordRatioResponseElement objects (WordRatioResponseElement.class)
    CollectionType valueType = typeFactory.constructCollectionType(List.class, WordRatioResponseElement.class);

    // Map the JSON to the value type
    return objMapper.readValue(httpResponse.GetContent(), valueType);
  }
}
