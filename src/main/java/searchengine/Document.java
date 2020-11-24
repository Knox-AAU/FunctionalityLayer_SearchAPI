package searchengine;

import javax.print.Doc;

public class Document extends Vector implements Comparable<Document> {

    private String title;
    private Double score;

    public Document(String title, String content) {
        super(content);

        this.title = title;
    }

    public Double getScore(){
        return this.score;
    }

    public void setScore(double value){
        this.score = value;
    }

    public String getTitle(){
        return title;
    }

    @Override
    public int compareTo(Document o) {
        return this.getScore().compareTo(o.getScore());
    }
}
