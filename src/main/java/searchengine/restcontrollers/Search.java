package searchengine.restcontrollers;

import searchengine.Document;
import searchengine.ScoredDocument;
import searchengine.VectorSpaceModel;
import searchengine.booleanretrieval.DataSelection;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


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


