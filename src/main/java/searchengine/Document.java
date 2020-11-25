package searchengine;

public class Document implements Comparable<Document> {

    private final int id;
    private final double score;

    public Document(int id, double score) {
        this.id = id;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public Double getScore() {
        return score;
    }

    @Override
    public int compareTo(Document o) {
        return this.getScore().compareTo(o.getScore());
    }
}