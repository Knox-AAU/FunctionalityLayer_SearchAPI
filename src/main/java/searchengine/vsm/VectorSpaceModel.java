package searchengine.vsm;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.http.HttpException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import searchengine.http.HTTPRequest;
import searchengine.http.IHTTPRequest;
import searchengine.http.IHTTPResponse;

import java.io.IOException;
import java.util.*;

/**
 * Contains all methods related to the vector space model.
 */
public class VectorSpaceModel {
  /**
   * Creates a vector space model from the query.
   * @param query input query
   */
  public VectorSpaceModel(String query) {
    createQueryTF(query);
  }

  /* <Term, Frequency> */ //TODO: Add getter and setter?
  private HashMap<String, Integer> queryTFMap = new HashMap<>();
  /* <Term, Score> */
  private HashMap<String, Double> queryTFIDFMap = new HashMap<>();

  /**
   * Splits the query string into individual terms.
   * @param query: input query
   */
  private void createQueryTF(String query) {
    // Split the query into terms and places them into an array. Query is split on all whitespaces.
    String[] termArray = query.split("\\s+");

    //Counts term frequency in the query//
    for (String term : termArray) {

      if (queryTFMap.containsKey(term)) { //Term exists in map, instance is counted up
        queryTFMap.put(term, queryTFMap.get(term) + 1);
      } else { // Term doesn't exit in map
        queryTFMap.put(term, 1);
      }
    }
  }

  /**
   * Retrieves a list of scored documents from a list of documents
   * @param documents The list of documents retrieved from the database
   * @return the list of ScoredDocuments
   */
  public List<ScoredDocument> getScoredDocuments(List<TFIDFDocument> documents) {

    List<ScoredDocument> scoredDocuments = new ArrayList<>();
    calculateTFIDF(documents);

    for (TFIDFDocument doc : documents) {
      double score = cosineSimilarityScore(doc);
      scoredDocuments.add(new ScoredDocument(doc.getTitle(), score, doc.getId()));
    }

    scoredDocuments.sort(Collections.reverseOrder());
    return scoredDocuments;
  }

  /**
   * Calculates the TF-IDF value of all documents and the query.
   * @param documents list of input documents.
   */
  private void calculateTFIDF(List<TFIDFDocument> documents) {
    HashMap<String, Double> idf = calculateIDF(documents);
    int maximumFrequency = 0;

    // Assign TF-IDF for all documents
    for (TFIDFDocument document : documents) {
      HashMap<String, Double> innerMap = new HashMap<>();

      for (String term : document.getTF().keySet()) {
        int termFrequency = document.getTF().get(term);
        if (termFrequency > maximumFrequency) maximumFrequency = termFrequency;
      }

      for (String term : document.getTF().keySet()) {
        // TFIDF_td = TF_td * IDF_t
        // td: term in document, t: term
        //This is normalizing to avoid bias for large documents
        //innerMap.put(term, 0.5 + (0.5 * document.getTF().getOrDefault(term, 0) / (maximumFrequency * document.getTF().getOrDefault(term, 0))) * idf.getOrDefault(term, 1.0));
        //This is standard TFIDF
        innerMap.put(term, (double) document.getTF().getOrDefault(term, 0) * idf.get(term));
      }
      document.setTFIDF(innerMap);
    }

    // Iterates through all the terms in the query to find the highest frequency for a term
    for (String term : queryTFMap.keySet()) {
      int termFrequency = queryTFMap.get(term);
      if (termFrequency > maximumFrequency) {
        maximumFrequency = termFrequency;
      }
    }
    // Assign TF-IDF for the query
    for (String term : queryTFMap.keySet()) {
      queryTFIDFMap.put(term, queryTFMap.getOrDefault(term, 0) * idf.getOrDefault(term, 1.0));
      //This is the normal TFIDF
      //queryTFIDFMap.put(term, 0.5 + (0.5 * queryTFMap.getOrDefault(term, 0) / (maximumFrequency * queryTFMap.getOrDefault(term, 1))) * idf.getOrDefault(term, 1.0));
      //This is TFIDF with double normalization, for reducing bias for longer documents. Does not make much sense to use for the query
    }
  }

  /**
   * Calculates the IDF (inverse document frequency) score for all input documents.
   * @param documents list of documents
   * @return A HashMap<String, Double> of inverse document frequency values.
   */
  private HashMap<String, Double> calculateIDF(List<TFIDFDocument> documents) {
    HashMap<String, Double> idf = new HashMap<>();
    HashMap<String, Integer> df = new HashMap<>();

    // Fills the document term frequency HashMap
    // This means that all terms from all documents are added, along with their frequency across all documents
    for (TFIDFDocument document : documents) {
      for (String term : document.getTF().keySet()) { // Find all terms in document
        if (df.containsKey(term)) {
          df.put(term, df.get(term)+1);
        } else {
          df.put(term, 1);
        }
      }
    }

    // We get the document count from the database API
    int documentCount = 1;
    try {
      IHTTPResponse httpResponse = requestCountFromDB();
      documentCount = Integer.parseInt(httpResponse.GetContent().trim());
      System.out.println(documentCount);
    } catch (HttpException | HttpRequestMethodNotSupportedException | IOException e) {
      e.printStackTrace();
    }

    // Get the inverse document frequency of each term
    for (String term : df.keySet()) {
      idf.put(term, Math.log10(documentCount / (double) df.get(term))); // IDF_t = log(N/DF_t)
    }

    return idf;
  }

  /**
   * This function calls the database API and returns a response containing the amount of documents
   * @return A httpresponse that contains the document count
   * @throws HttpException
   * @throws HttpRequestMethodNotSupportedException
   * @throws IOException
   */
  private IHTTPResponse requestCountFromDB() throws HttpException, HttpRequestMethodNotSupportedException, IOException {
    Dotenv dotenv = Dotenv.load();

    String apiEndPoint = dotenv.get("DATABASE_API_COUNT_URL");
    IHTTPRequest http = new HTTPRequest(apiEndPoint);
    http.SetMethod("GET");

    searchengine.http.IHTTPResponse httpResponse = http.Send();
    if(!httpResponse.GetSuccess()){
      throw new HttpException("Internal server error - " + httpResponse.GetContent());
    }
    return httpResponse;
  }

  /**
   * Calculates the cosine similarity score of a document and the query.
   * @param doc the document to be scored
   * @return the cosine similarity score
   */
  private Double cosineSimilarityScore(TFIDFDocument doc) {

    Set<String> uniqueTerms = new HashSet<>();
    double dotProduct = 0;
    double documentTFIDF = 0;

    //Used to use all terms, but terms not in query are irrelevant. This is no problem in production, as we only get wordcount for documents that contain query words from the database, but the tests get messed up by the doc terms
    //uniqueTerms.addAll(doc.getTFIDF().keySet());
    uniqueTerms.addAll(queryTFIDFMap.keySet());

    //If there is only 1 term, closeness is defined by docTFIDF / queryTFIDF


    // The dot product of the document and the vector
    for (String term : uniqueTerms) {
      if (uniqueTerms.size() == 1) {
        dotProduct = doc.getTFIDF().getOrDefault(term, 0.0);
      } else {
        dotProduct += doc.getTFIDF().getOrDefault(term, 0.0)
            * queryTFIDFMap.getOrDefault(term, 0.0);
      }
    }
    //If there is only 1 term, the ratio is document TFIDF over query TFIDF, also be aware that IDF does nothing for 1 term but be a scalar
    if (uniqueTerms.size() == 1) return dotProduct / getLength(queryTFIDFMap);
    //If there are more terms, use standard cosine similarity
    return dotProduct / (getLength(doc.getTFIDF()) * getLength(queryTFIDFMap));
  }

  /**
   * Calculates the length of a document represented as a vector
   * @param tfidf the TF-IDF hashmap of a document
   * @return the length of the document
   */
  private double getLength(HashMap<String, Double> tfidf) {

    double length = 0.0;

    for (String term : tfidf.keySet()) {
      length += Math.pow(tfidf.get(term), 2);
    }

    return Math.sqrt(length);
  }
}