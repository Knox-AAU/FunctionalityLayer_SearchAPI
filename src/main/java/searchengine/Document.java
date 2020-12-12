package searchengine;

import java.util.HashMap;

public class Document {

    /* <Title, <Term, Occurrences>> */
    public HashMap<String, Integer> TF = new HashMap<>();
    /* <Title, <Term, Score>> */
    public HashMap<String, Double> TFIDF = new HashMap<>();

    private String title;
    private String filepath;

    public Document(){}

    public Document(String title, String filepath) {
        this.title = title;
        this.filepath = filepath;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilepath() {
        return filepath;
    }
}