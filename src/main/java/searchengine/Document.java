package searchengine;

public class Document extends Vector {

    private String title;
    private double score;

    public Document(String title, String content) {
        super(content);

        this.title = title;
    }

    public double getScore(){
        return this.score;
    }

    public void setScore(double value){
        this.score = value;
    }

    public String getTitle(){
        return title;
    }
}
