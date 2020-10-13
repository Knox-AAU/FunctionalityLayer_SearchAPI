package tokenizer;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class Tokenizer {

    private List<Token> tokens;
    private int index;

    public Tokenizer(String input) {
        tokens = new ArrayList<>();
        generateTokens(input);
    }

    // Starts at the start of a string, and generates the tokens
    private void generateTokens(String source) {
        index = 0;
        Token token = nextToken(source);

        while (token != null) {
            tokens.add(token);
            token = nextToken(source);
        }
    }

    // takes the source string as input, and returns the next token in the input string
    @Nullable
    private Token nextToken(String source) {
        for (TokenType type : TokenType.values()) {

            Matcher m = type.getPattern().matcher(source.substring(index));

            if (m.find()) {
                index += m.end();
                return new Token(type, m.group(0));
            }
        }

        return null;
    }

    // returns a list of the tokens (aka the list of keywords)
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
