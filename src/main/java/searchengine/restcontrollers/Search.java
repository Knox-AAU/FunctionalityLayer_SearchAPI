package searchengine.restcontrollers;

import searchengine.Document;
import searchengine.VectorSpaceModel;
import java.util.Collections;
import java.util.List;


public class Search {

    private List<Document> result;

    public Search(String input) {
        result = new VectorSpaceModel(input, "documents.txt").retrieve(1);
        Collections.sort(result);
        Collections.reverse(result);
    }

    public List<Document> getResult() {
        return result;
    }

}


