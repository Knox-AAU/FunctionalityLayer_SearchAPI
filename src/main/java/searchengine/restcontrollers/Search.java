package searchengine.restcontrollers;

import searchengine.Document;
import searchengine.ScoredDocument;
import searchengine.VectorSpaceModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Search {

    List<ScoredDocument> result;

    public Search(String input){
        result = new VectorSpaceModel(input).retrieve();
    }

    public List<ScoredDocument> getResult() {
        return result;
    }
}


