package searchengine.booleanretrieval;

import searchengine.Document;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataSelection {

    public List<Document> sendQuery(String input){

        String tempTitle = "";
        List<Document> documents = new ArrayList<>();
        int i = 0;

        String[] terms = input.split("\\s+");
        StringBuilder query = new StringBuilder();

        for (int j = 0; j < terms.length; j++){
            if (j < terms.length-1){
                query.append(String.format("wordname='%s' OR ", terms[j]));
            }
            else{
                query.append(String.format("wordname='%s'", terms[j]));
            }
        }

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/wordcount","postgres","1234");
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT DISTINCT wordname, amount, articletitle, filepath, totalwordsinarticle " +
                    "FROM wordratios " +
                    "WHERE articletitle " +
                    "IN ( SELECT DISTINCT articletitle " +
                    "FROM wordratios WHERE " + query + ")" +
                    "ORDER BY articletitle;");

            while (rs.next()) {
                String wordname = rs.getString("wordname");
                int amount = rs.getInt("amount");
                String title = rs.getString("articletitle");
                String filepath = rs.getString("filepath");
                int totalWordsInArticle = rs.getInt("totalwordsinarticle");

                if (!tempTitle.equals(title)) {
                    i++;
                    documents.add(new Document(title, filepath, totalWordsInArticle));
                }
                documents.get(i-1).TF.put(wordname, amount);
                tempTitle = title;
            }

            rs.close();;
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return documents;
    }
}
