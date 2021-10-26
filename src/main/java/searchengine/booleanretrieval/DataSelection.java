package searchengine.booleanretrieval;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import searchengine.ISqlConnection;
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
      IHTTPRequest http = new HTTPRequest("wordratio/");
      http.SetMethod("GET");
      http.AddQueryParameter("terms", input.split(" "));
      http.AddQueryParameter("sources", (String[])sources.toArray());

      IHTTPResponse httpResponse = http.Send();
      if(!httpResponse.GetSuccess()){
        throw new Exception("Internal server error (retrieving documents failed)");
      }
      //TODO refactor/cleanup this so it is more readable
      ObjectMapper objMapper = new ObjectMapper();
      TypeFactory typeFactory = objMapper.getTypeFactory();
      var valueType = typeFactory.constructCollectionType(List.class, WordRatioResponseElement.class);
      List<WordRatioResponseElement> responseElements =  objMapper.readValue(httpResponse.GetContent(), valueType);

      TFIDFDocument currentDocument;
      String currentTitle = "";
      for(WordRatioResponseElement wordRatio : responseElements){
        //TODO refactor while loop code here
        //...
        if(!currentTitle.equals(title)){
          currentDocument = new TFIDFDocument(title, fid);
        }
      }

      while (rs.next()) {
        String wordname = rs.getString("wordname");
        int amount = rs.getInt("amount");
        String title = rs.getString("articletitle");
        int fid = rs.getInt("fid");

        if (!tempTitle.equals(title)) {
          i++;
          documents.add(new TFIDFDocument(title, fid));
        }
        documents.get(i - 1).getTF().put(wordname, amount);
        tempTitle = title;
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return documents;
  }
  /*
   * SubFunction in retrieveDocuments. Generates an SQL query that searches for the terms from the input string in the wordcount database.
   * @param input: The input string should be split with space between terms.
   * @param sources: a String array of the selected sources (databases) to search in.
   * @return the SQL query
   */

  private String buildQuery(String input, List<String> sources) { //TODO: Check if UI API returns string array
    StringBuilder query = new StringBuilder();
    query.append("SELECT DISTINCT wordname, amount, articletitle, fid "); //Distinct: Only select unique values
    query.append("FROM wordratios ");
    query.append("WHERE articletitle ");
    query.append("IN ( SELECT articletitle ");
    query.append("FROM wordratios WHERE ");
    String[] terms = input.split("\\s+"); //Split on all whitespace

    for (int i = 0; i < terms.length; i++) { //Append terms to query
      if (i < terms.length - 1) {
        query.append(String.format("wordname='%s' OR ", terms[i]));
      } else {
        query.append(String.format("wordname='%s' ", terms[i]));
      }
    }
    query.append(")");
    if (sources != null && sources.size() > 0) {
      query.append(" AND (sourcename = '");
      query.append(String.join("' OR sourcename = '", sources));
      query.append("') ");
    }
    query.append("ORDER BY articletitle;"); //Required as the function retrievedocuments(...) requires the list of articles to be ordered.
    return query.toString();
  }
}
