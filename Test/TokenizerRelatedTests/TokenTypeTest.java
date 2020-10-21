package TokenizerRelatedTests;

import org.junit.jupiter.api.Test;
import tokenizer.Token;
import tokenizer.TokenType;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class TokenTypeTest {

    @Test
    void getPattern() {
        // arrange + act + assert
        Pattern testPattern = Pattern.compile("([^\\s]+)");
        assertEquals(TokenType.KEYWORD.getPattern().pattern(), testPattern.pattern());
    }

    @Test
    void isAuxiliary() {
        // arrange
        Token testToken = new Token(TokenType.KEYWORD, "1");
        // act + assert
        assertFalse(testToken.getType().isAuxiliary());
    }

    @Test
    void isAuxiliary1() {
        // arrange
        Token testToken = new Token(TokenType.SPACE, " ");
        // act + assert
        assertTrue(testToken.getType().isAuxiliary());
    }
}