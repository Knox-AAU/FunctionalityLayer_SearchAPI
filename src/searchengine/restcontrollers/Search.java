package searchengine.restcontrollers;

import searchengine.tokenizer.Tokenizer;

public class Search {

    private Tokenizer tokenizer;

    public Search(String input) {
        tokenizer = new Tokenizer(input);
    }

    public Tokenizer getTokenizer() {
        return tokenizer;
    }
}
