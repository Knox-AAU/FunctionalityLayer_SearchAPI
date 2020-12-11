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

        List<Document> documents = new ArrayList<>();

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/wordcount","postgres","1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(buildQuery(input));
            /*
            "SELECT DISTINCT wordname, amount, articletitle, filepath, totalwordsinarticle " +
            "FROM wordratios " +
            "WHERE articletitle " +
            "IN ( SELECT DISTINCT articletitle " +
            "FROM wordratios WHERE " + query + ")" +
            "ORDER BY articletitle;");*/

            Document doc = null;
            String tempTitle = null;

            while (rs.next()) {
                String wordname = rs.getString("wordname");
                int amount = rs.getInt("amount");
                String title = rs.getString("articletitle");
                String filepath = rs.getString("filepath");
                int totalWordsInArticle = rs.getInt("totalwordsinarticle");

                if (tempTitle == null || !tempTitle.equals(title)) {
                    tempTitle = title;
                    documents.add(doc);
                    doc = new Document(title, filepath, totalWordsInArticle);
                }
                doc.TF.put(wordname, amount);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return documents;
    }

    private String buildQuery(String input)
    {
        StringBuilder query = new StringBuilder();
        String[] terms = input.split("\\s+");

        query.append("SELECT DISTINCT wordname, amount, articletitle, filepath, totalwordsinarticle ");
        query.append("FROM wordratios ");
        query.append("WHERE articletitle ");
        query.append("IN ( SELECT DISTINCT articletitle ");
        for (int i = 0; i < terms.length; i++){
            if (i < terms.length-1){
                query.append(String.format("wordname='%s' OR ", terms[i]));
            }
            else{
                query.append(String.format("wordname='%s'", terms[i]));
            }
        }
        query.append("ORDER BY articletitle;");

        return query.toString();
    }

}
