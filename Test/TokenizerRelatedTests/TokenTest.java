package TokenizerRelatedTests;

import org.junit.jupiter.api.Test;
import main.tokenizer.Token;
import main.tokenizer.TokenType;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    void getType() {
        // arrange
        Token testToken = new Token(TokenType.KEYWORD, "1");
        // act + assert
        assertEquals(testToken.getType(), TokenType.KEYWORD);
    }

    @Test
    void getType1(){
        // arrange
        Token testToken = new Token(TokenType.SPACE, " ");
        // act + assert
        assertEquals(testToken.getType(), TokenType.SPACE);
    }

    @Test
    void getValue() {
        // arrange
        Token testToken = new Token(TokenType.KEYWORD, "1");
        // act + assert
        assertEquals(testToken.getValue(), "1");
    }

    @Test
    void getValue1() {
        // arrange
        Token testToken = new Token(TokenType.SPACE, " ");
        // act + assert
        assertEquals(testToken.getValue(), " ");
    }
}