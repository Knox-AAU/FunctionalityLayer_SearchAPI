package searchengine.vsm;

import java.util.HashMap;

/**
 * Data class.
 * Stores TF IDF title and filepath for a document
 */
public class Document {

    /* <Title, <Term, Amount>> */
    private HashMap<String, Integer> TF = new HashMap<>();
    /* <Title, <Term, Score>> */
    private HashMap<String, Double> TFIDF = new HashMap<>();

    private String title;

    private String filepath;

    /** Constructor
     * @param title: String containing the document title.
     * @param filepath: Filepath to the storage location.
     */
    public Document(String title, String filepath) {
        this.title = title;
        this.filepath = filepath;
    }

    public HashMap<String, Double> getTFIDF() {
        return TFIDF;
    }

    public HashMap<String, Integer> getTF() {
        return TF;
    }

    public void setTFIDF(HashMap<String, Double> TFIDF) {
        this.TFIDF = TFIDF;
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