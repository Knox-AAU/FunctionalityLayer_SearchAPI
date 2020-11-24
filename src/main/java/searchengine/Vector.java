package searchengine;

import java.util.HashMap;
import com.github.xjavathehutt.porterstemmer.PorterStemmer;

public class Vector {
    protected HashMap<String, Integer> termFrequency;
    protected HashMap<String, Double> tfidf;

    public Vector(String content) {
        termFrequency = new HashMap<String, Integer>();

        String[] sArray = content.split("\\s+");

        for (String s : sArray) {

            s = PorterStemmer.stem(s);
            s = s.toLowerCase();

            if (termFrequency.containsKey(s)) {
                termFrequency.put(s, termFrequency.get(s) + 1);
            }
            else {
                termFrequency.put(s, 1);
            }
        }
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
