package searchengine.booleanretrieval;

import searchengine.Document;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

public class DataSelection {

    public void sendQuery(String input, List<Document> documents, HashMap<String, HashMap<String, Integer>> TF){

        Connection conn = null;
        Statement stmt = null;
        String[] terms = input.split("\\s+");
        StringBuilder query = new StringBuilder();

        for (int i = 0; i < terms.length; i++){
            if (i < terms.length-1){
                query.append(String.format("wordname='%s' OR ", terms[i]));
            }
            else{
                query.append(String.format("wordname='%s'", terms[i]));
            }
        }

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/wordcount","postgres","1234");

            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT wordname, amount, articletitle, filepath, totalwordsinarticle " +
                    "FROM wordratios " +
                    "WHERE articletitle " +
                    "IN ( SELECT DISTINCT articletitle " +
                    "FROM wordratios WHERE " + query + ")" +
                    "ORDER BY amount DESC;");

            while (rs.next()) {
                String wordname = rs.getString("wordname");
                int amount = rs.getInt("amount");
                String title = rs.getString("articletitle");
                String filepath = rs.getString("filepath");
                int totalWordsInArticle = rs.getInt("totalwordsinarticle");

                TF.put(title, new HashMap<>(){{put(wordname, amount);}});
            }

            rs.close();;
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
