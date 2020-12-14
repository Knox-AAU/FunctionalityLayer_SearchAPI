package searchengine.restcontrollers;

import searchengine.vsm.Document;
import searchengine.vsm.ScoredDocument;
import searchengine.vsm.VectorSpaceModel;
import searchengine.booleanretrieval.DataSelection;

import java.util.List;


public class Search {

    private List<ScoredDocument> result;

    public Search(String input){
         List<Document> documents = new DataSelection().retrieveDocuments(input);
         result = new VectorSpaceModel(input).getScoredDocuments(documents);
    }

    public List<ScoredDocument> getResult() {
        return result;
    }
}


