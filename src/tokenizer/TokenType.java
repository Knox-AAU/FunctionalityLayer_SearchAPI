package tokenizer;

import java.util.regex.Pattern;

public enum TokenType {

    SPACE("^ "),

    KEYWORD("([^\\s]+)");

    private final Pattern pattern;

    TokenType(final String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public Pattern getPattern() {
        return this.pattern;
    }

    /* Is used to remove unnecessary tokens */
    public boolean isAuxiliary() {
        return this == SPACE;
    }
}
