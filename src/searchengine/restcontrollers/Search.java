package searchengine.restcontrollers;

import searchengine.query.*;
import searchengine.tokenizer.*;
import java.util.List;

public class Search {

    private String title;
    private String pdfPath;
    private int rank;


    public Search(String input) {
        this.title = "TITLE";
        this.pdfPath = "placeholder.pdf";
        this.rank = 1;
    }

    public String getTitle(){
        return this.title;
    }

    public String getPdfPath(){
        return this.pdfPath;
    }

    public int getRank(){
        return this.rank;
    }
}


