package searchengine;

import java.util.HashMap;

public class Document {

    private HashMap<String, Integer> termFrequency;

    public Document(String content) {

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

    public HashMap<String, Integer> getTermFrequency() {
        return termFrequency;
    }
}
