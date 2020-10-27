package searchengine.restcontrollers;

import searchengine.query.*;
import searchengine.tokenizer.*;
import java.util.List;

public class Search {

    private String result;

    public Search(String input) {
        List<Token> tokens = new Tokenizer(input).getFilteredTokens();
        Query query = new QueryBuilder().generateQuery(tokens, "");

        result = query.execute();
    }

    public String getResult() {
        return result;
    }
}


