package searchengine;

public class ScoredDocument {
    String title;
    double score;
    String filepath;

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

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
