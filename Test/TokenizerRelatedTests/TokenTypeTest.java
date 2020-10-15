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
        Token testToken1 = new Token(TokenType.SPACE, " ");
        // act + assert
        assertFalse(testToken.getType().isAuxiliary());
        assertTrue(testToken1.getType().isAuxiliary());
    }
}