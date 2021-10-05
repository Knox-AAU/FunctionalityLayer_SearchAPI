package searchengine.booleanretrieval;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import searchengine.ISqlConnection;
import searchengine.vsm.TFIDFDocument;

/**
 * Contains the retrieveDocuments function.
 * Is used for retrieving document names.
 */
public class DataSelection {

    public DataSelection(ISqlConnection connection){
        this.connection = connection;
    }

    private ISqlConnection connection;

    /**
     * Searches the wordcount database for documents containing the terms from the input string.
     * @param input: The search query in the format "term1 term2 ... termN" (space separated)
     * @param sources: The list of sources to be searched
     * @return a list of documents which contained at least one of the search terms
     */
    public List<TFIDFDocument> retrieveDocuments(String input, List<String> sources) {
    List<TFIDFDocument> documents = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(buildQuery(input, sources));

      String tempTitle = "";
      int i = 0;

      while (rs.next()) {
        String wordname = rs.getString("wordname");
        int amount = rs.getInt("amount");
        String title = rs.getString("articletitle");
        String filepath = rs.getString("filepath");

        if (!tempTitle.equals(title)) {
          i++;
          documents.add(new TFIDFDocument(title, filepath));
        }
        documents.get(i - 1).getTF().put(wordname, amount);
        tempTitle = title;
      }

            rs.close();
            stmt.close();
            connection.close();

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
    query.append("SELECT DISTINCT wordname, amount, articletitle, filepath "); //Distinct: Only select unique values
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
    if(sources.size() > 0) {
      query.append(" AND (sourcename = '");
      query.append(String.join("' or sourcename = '", sources));
      query.append("') ");
    }
    query.append("ORDER BY articletitle;"); //Required as the function retrievedocuments(...) requires the list of articles to be ordered.
    return query.toString();
  }
}
