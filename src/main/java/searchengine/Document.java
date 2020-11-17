package searchengine;

import java.util.HashMap;

public class Document {

    private HashMap<String, Integer> termFrequency;
    private HashMap<String, Double> tfidf;
    private String title;
    private double score;

    public Document(String title, String content) {

        this.title = title;
        termFrequency = new HashMap<String, Integer>();

        String[] sArray = content.split("\\s+");

        for (String s : sArray) {
            if (termFrequency.containsKey(s)) {
                termFrequency.put(s, termFrequency.get(s) + 1);
            }
            else {
                termFrequency.put(s, 1);
            }
        }
    }


    public double getScore(){
        return this.score;
    }

    public String getTitle(){
        return title;
    }

    public void setScore(double value){
        this.score = value;
    }

    public HashMap<String, Integer> getTermFrequency() {
        return termFrequency;
    }

    public HashMap<String, Double> getTfidf() {
        return tfidf;
    }

    public void setTfidf(HashMap<String, Double> tfidf) {
        this.tfidf = tfidf;
    }

}
