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

    private void generateTokens(String source) {
        index = 0;
        Token token = nextToken(source);

        while (token != null) {
            tokens.add(token);
            token = nextToken(source);
        }
    }

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
