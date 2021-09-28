package searchengine.vsm;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.print.Doc;
import java.util.HashMap;

/**
 * Data class.
 * Stores TF IDF title and filepath for a document
 */
public class TFIDFDocument extends Document {

    /* <Title, <Term, Amount>> */
    @Getter @Setter
    private HashMap<String, Integer> TF;
    /* <Title, <Term, Score>> */
    @Getter @Setter
    private HashMap<String, Double> TFIDF;

    /** Constructor
     * @param title: String containing the document title.
     * @param filepath: Filepath to the storage location.
     */
    public TFIDFDocument(String title, String filepath) {
        super(title,filepath);
        TFIDF = new HashMap<>();
        TF = new HashMap<>();
    }


}