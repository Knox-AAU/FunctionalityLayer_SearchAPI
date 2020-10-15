package TokenizerRelatedTests;

import org.junit.jupiter.api.Test;
import tokenizer.Token;
import tokenizer.TokenType;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    void getType() {
        // arrange
        Token testToken = new Token(TokenType.KEYWORD, "1");
        Token testToken1 = new Token(TokenType.SPACE, " ");
        // act + assert
        assertEquals(testToken.getType(), TokenType.KEYWORD);
        assertEquals(testToken1.getType(), TokenType.SPACE);
    }

    @Test
    void getValue() {
        // arrange
        Token testToken = new Token(TokenType.KEYWORD, "1");
        Token testToken1 = new Token(TokenType.SPACE, " ");
        // act + assert
        assertEquals(testToken.getValue(), "1");
        assertEquals(testToken1.getValue(), " ");
    }
}