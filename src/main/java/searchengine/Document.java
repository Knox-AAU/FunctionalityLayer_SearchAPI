package searchengine;

import java.util.HashMap;

public class Document {

    private HashMap<String, Integer> termFrequency;
    private HashMap<String, Double> tfidf;
    private String name;
    private double score;

    public Document(String name, String content) {

        this.name = name;
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

    public void createTfidf(HashMap<String, Double> idf){
        for(String term : termFrequency.keySet()){
            tfidf.put(term, termFrequency.get(term) * idf.get(term));
        }
    }

    public double getScore(){
        return this.score;
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

}
