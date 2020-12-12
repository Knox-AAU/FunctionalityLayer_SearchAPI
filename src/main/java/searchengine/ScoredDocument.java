package searchengine;

public class ScoredDocument {
    private final String title;
    private final double score;
    private final String filepath;

    public ScoredDocument(String title, double score, String filepath){
        this.title = title;
        this.score = score;
        this.filepath = filepath;
    }

    public double getScore() {
        return score;
    }

    public String getFilepath() {
        return filepath;
    }

    public String getTitle() {
        return title;
    }

}