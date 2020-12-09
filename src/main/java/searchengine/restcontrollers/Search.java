package searchengine.restcontrollers;

import searchengine.Document;
import searchengine.VectorSpaceModel;
import java.util.Collections;
import java.util.List;


public class Search {

    private String query;

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Document> getResult() {
        return new VectorSpaceModel(query, "documents.txt").retrieve(10);
    }
}


