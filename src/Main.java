
import tokenizer.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Initializes an instance of tokenizer with input string
        // gets a list of tokens from the input string
        List<Token> tokens = new Tokenizer("Kul til grill").getFilteredTokens();
        
        for (Token t : tokens) {
            System.out.println(t.getValue());
        }
    }
}
