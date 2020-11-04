package searchengine.restcontrollers;

import searchengine.query.Query;
import searchengine.query.QueryBuilder;
import searchengine.tokenizer.Token;
import searchengine.tokenizer.Tokenizer;

import java.util.List;

public class Search {

    private String result;

    /*
     *
     *
     */
    public Search(String input) {
        List<Token> tokens = new Tokenizer(input).getFilteredTokens();
        Query query = new QueryBuilder().generateQuery(tokens, "");

        result = query.execute();
    }

    public String getResult() {
        return result;
    }
}


