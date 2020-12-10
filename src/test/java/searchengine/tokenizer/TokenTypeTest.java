package searchengine.tokenizer;

        import org.junit.jupiter.api.Test;
        import java.util.regex.Pattern;

        import static org.junit.jupiter.api.Assertions.*;

class TokenTypeTest {

    @Test
    void getPattern() {
        // arrange
        String testPattern = Pattern.compile("([\\w]+)").toString();
        // act
        String pattern = TokenType.KEYWORD.getPattern().toString();
        // assert
        assertEquals(pattern, testPattern);
    }

    @Test
    void isAuxiliary() {
        // arrange
        Token testToken = new Token(TokenType.KEYWORD, "1");
        // act
        boolean result = testToken.getType().isAuxiliary();
        // assert
        assertFalse(result);
    }

    @Test
    void isAuxiliary1() {
        // arrange
        Token testToken = new Token(TokenType.SPACE, " ");
        // act
        boolean result = testToken.getType().isAuxiliary();
        // assert
        assertTrue(result);
    }
}