package searchengine;

import java.util.HashMap;

public class Document {

    /* <Title, <Term, Occurrences>> */
    public HashMap<String, Integer> TF = new HashMap<>();
    /* <Title, <Term, Score>> */
    public HashMap<String, HashMap<String, Double>> TFIDF = new HashMap<>();

    private String title;
    private String filepath;
    private int totalwordsinarticle;

    public Document(){}

    public Document(String title, String filepath, int totalwordsinarticle) {
        this.title = title;
        this.filepath = filepath;
        this.totalwordsinarticle = totalwordsinarticle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}