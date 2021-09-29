package searchengine.booleanretrieval;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import searchengine.vsm.TFIDFDocument;

/**
 * Contains the retrieveDocuments function.
 * Is used for retrieving document names.
 */
public class DataSelection {
  /**
   * Searches the wordcount database for documents containing the terms from the input string.
   * @param input: The search query in the format "term1 term2 ... termN" (space separated)
   * @return a list of documents which contained at least one of the search terms
   */
  public List<TFIDFDocument> retrieveDocuments(String input) {
    List<TFIDFDocument> documents = new ArrayList<>();

    try {
      //Class.forName(...) is needed for initializing the driver as jdbc
      // for more info: https://jdbc.postgresql.org/documentation/81/load.html
      Class.forName("org.postgresql.Driver");
      Connection conn = DriverManager.getConnection(
              "jdbc:postgresql://localhost:5432/wordcount",
              "postgres",
              "1234");
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(buildQuery(input));

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
      conn.close();

    } catch (Exception e) {
      e.printStackTrace();
    }

    return documents;
  }
  /*
  /** //TODO Use function when UI API is done
   * SubFunction in retrieveDocuments. Generates an SQL query that searches for the terms from the input string in the wordcount database.
   * @param input: The input string should be split with space between terms.
   * @param sources: a String array of the selected sources (databases) to search in.
   * @return the SQL query
   */
  /*
  private String buildQuery(String input, String[] sources) { //TODO: Check if UI API returns string array
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
    query.append(") ");
    for (String source : sources) { //Search in selected sources (databases)
      query.append(String.format("AND source='%s'", source)); //TODO: replace 'source' with the actual attribute
    }
    return query.toString();
  }
*/

  /** SubFunction in retrieveDocuments. Generates an SQL query that searches for the terms from the input string in the wordcount database.
   * The input string should be split with space between terms.
   * @param input: the search query in the format "term1 term2 ... termN" (space separated)
   * @return the SQL query
   */
  private String buildQuery(String input) {
    StringBuilder query = new StringBuilder();
    query.append("SELECT DISTINCT wordname, amount, articletitle, filepath "); //Distinct: select unique value
    query.append("FROM wordratios ");
    query.append("WHERE articletitle ");
    query.append("IN ( SELECT articletitle ");
    query.append("FROM wordratios WHERE ");
    String[] terms = input.split("\\s+"); //Split on whitespace
    for (int i = 0; i < terms.length; i++) { //Append terms
      if (i < terms.length - 1) {
        query.append(String.format("wordname='%s' OR ", terms[i]));
      } else {
        query.append(String.format("wordname='%s' ", terms[i]));
      }
    }
    query.append(")  ORDER BY articletitle;");
    return query.toString();
  }

}
