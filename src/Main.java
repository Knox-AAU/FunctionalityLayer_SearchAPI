import tokenizer.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        Tokenizer t = new Tokenizer("Kul til grill");
        List<Token> tokens = t.getFilteredTokens();

        for (Token a : tokens) {
            System.out.println(a.getValue());
        }
    }
}
