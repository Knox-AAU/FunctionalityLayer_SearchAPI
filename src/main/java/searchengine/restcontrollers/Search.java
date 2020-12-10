package searchengine.restcontrollers;

import searchengine.Document;
import searchengine.VectorSpaceModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Search {

    List<Document> result;

    public Search(String input){
        result = new VectorSpaceModel(input, "documents.txt").retrieve(10);
    }

    public List<Document> getResult() {
        return result;
    }
}


