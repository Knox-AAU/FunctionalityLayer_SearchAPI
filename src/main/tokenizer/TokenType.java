package main.tokenizer;

import java.util.regex.Pattern;

public enum TokenType {

    SPACE("^ "),

    KEYWORD("([\\w]+)");

    private final Pattern pattern;

    TokenType(final String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public Pattern getPattern() {
        return this.pattern;
    }

    /*
     * Returns true if the type is auxiliary
     */
    public boolean isAuxiliary() {
        return this == SPACE;
    }
}
