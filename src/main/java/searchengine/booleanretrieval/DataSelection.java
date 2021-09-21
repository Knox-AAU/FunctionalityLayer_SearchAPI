package searchengine.booleanretrieval;

import searchengine.vsm.Document;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataSelection {

    /** Searches the wordcount database for documents containing the terms from the input string.
    *@param input: the search query in the format "term1 term2 term3 term3" (space separated)
    * @return a list of documents which contained at least one of the search terms
     */
    public List<Document> retrieveDocuments(String input){

        List<Document> documents = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/wordcount","postgres","1234");
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
                    documents.add(new Document(title, filepath));
                }
                documents.get(i-1).getTF().put(wordname, amount);
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

    /** SubFunction in retrieveDocuments. Generates an SQL query that searches for the terms.
    * @param input: the search query in the ofrmat "term1 term2 term3 term3" (space separated)
    * @return the SQL query
     */
    private String buildQuery(String input)
    {
        StringBuilder query = new StringBuilder();
        String[] terms = input.split("\\s+");

        query.append("SELECT DISTINCT wordname, amount, articletitle, filepath ");
        query.append("FROM wordratios ");
        query.append("WHERE articletitle ");
        query.append("IN ( SELECT DISTINCT articletitle ");
        query.append("FROM wordratios WHERE ");
        for (int i = 0; i < terms.length; i++){
            if (i < terms.length-1){
                query.append(String.format("wordname='%s' OR ", terms[i]));
            }
            else{
                query.append(String.format("wordname='%s' ", terms[i]));
            }
        }
        query.append(") ORDER BY articletitle;");

        return query.toString();
    }

}
