package searchengine.vsm;

public class ScoredDocument implements Comparable<ScoredDocument> {
    private final String title;
    private final Double score;
    private final String filepath;

    public ScoredDocument(String title, double score, String filepath){
        this.title = title;
        this.score = score;
        this.filepath = filepath;
    }

    public Double getScore() {
        return score;
    }

    public String getFilepath() {
        return filepath;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int compareTo(ScoredDocument o) {
        return this.getScore().compareTo(o.getScore());
    }
}