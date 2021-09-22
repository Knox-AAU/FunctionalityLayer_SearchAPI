package searchengine.vsm;

import javax.print.Doc;
import java.util.HashMap;

/**
 * Data class.
 * Stores TF IDF title and filepath for a document
 */
public class TFIDFDocument extends Document {

    /* <Title, <Term, Amount>> */
    private HashMap<String, Integer> TF = new HashMap<>();
    /* <Title, <Term, Score>> */
    private HashMap<String, Double> TFIDF = new HashMap<>();

    /** Constructor
     * @param title: String containing the document title.
     * @param filepath: Filepath to the storage location.
     */
    public TFIDFDocument(String title, String filepath) {
        super(title,filepath);
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

}