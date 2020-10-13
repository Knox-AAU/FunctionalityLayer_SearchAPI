import tokenizer.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // Initializes an instance of tokenizer with input string
        // gets a list of tokens from the input string
        Tokenizer t = new Tokenizer("Kul til grill");
        List<Token> tokens = t.getFilteredTokens();
        
        for (Token a : tokens) {
            System.out.println(a.getValue());
        }
    }
}
