package searchengine;

import java.util.HashMap;

public class Document {

    private HashMap<String, Integer> vector;

    public Document(String content){

        vector = new HashMap<String, Integer>();

        String[] sArray = content.split("\\s+");

        for (String s : sArray) {
            if (vector.containsKey(s)) {
                vector.put(s, vector.get(s) + 1);
            } else {
                vector.put(s, 1);
            }
        }
    }
}
