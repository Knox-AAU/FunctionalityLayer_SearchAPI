package tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class Tokenizer {

    /* Holds all the tokens */
    private List<Token> tokens;

    /* Used as an index for substring */
    private int index;

    public Tokenizer(String input) {
        tokens = new ArrayList<>();
        tokenize(input);
    }

    /*
     * Generates token from a given string and adds them to a list
     *
     * @param source: The string to tokenize
     */
    private void tokenize(String source) {
        index = 0;
        Token token = extract(source);

        while (token != null) {
            tokens.add(token);
            token = extract(source);
        }
    }

    /*
     * Extracts a token from a string, and returns it while incrementing index
     * to create a substring for the next token extraction.
     *
     * @param source: the string to extract tokens from
     */
    private Token extract(String source) {
        for (TokenType type : TokenType.values()) {

            Matcher m = type.getPattern().matcher(source.substring(index));

            if (m.find()) {
                index += m.end();
                return new Token(type, m.group(0));
            }
        }

        return null;
    }

    /*
     * Returns the filtered tokens (unnecessary tokens such as space removed)
     */
    public List<Token> getFilteredTokens() {

        List<Token> filteredTokens = new ArrayList<>();

        for (Token token : tokens) {
            if (!token.getType().isAuxiliary()) {
                filteredTokens.add(token);
            }
        }

        return filteredTokens;
    }
}
