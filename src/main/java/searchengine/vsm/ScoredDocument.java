package searchengine.vsm;

import lombok.Getter;

/**
 * Data class.
 * Stores title filepath and score for a document.
 */
public class ScoredDocument extends Document implements Comparable<ScoredDocument> {
    @Getter
    private final double score;

    /** Constructor
     * @param title: String containing the document title.
     * @param score: Cosinus similarity score
     * @param filepath: Filepath to the storage location.
     */
    public ScoredDocument(String title, double score, String filepath){
        super(title,filepath);
        this.score = score;
    }


    @Override
    public int compareTo(ScoredDocument o) {
        return this.getScore().compareTo(o.getScore());
    }
}