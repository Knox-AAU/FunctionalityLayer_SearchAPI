package searchengine.tokenizer;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {

    Tokenizer testTokenizer = new Tokenizer("test test1");

    @Test
    void getFilteredTokens() {
        // arrange
        List<Token> testList;
        List<Token> testList2 = new ArrayList<>();
        int i = 0;

        // act
        testList = testTokenizer.getFilteredTokens();
        testList2.add(new Token(TokenType.KEYWORD, "test"));
        testList2.add(new Token(TokenType.KEYWORD, "test1"));

        // assert
        for(Token t : testList){
            assertEquals(testList.get(i).getValue(), testList2.get(i).getValue());
            i++;
        }
    }

    @Test
    void getFilteredTokens1() {
        // arrange
        List<Token> testList;
        List<Token> testList2 = new ArrayList<>();
        int i = 0;

        // act
        testList = testTokenizer.getFilteredTokens();
        testList2.add(new Token(TokenType.KEYWORD, "test"));
        testList2.add(new Token(TokenType.KEYWORD, "test1"));

        // assert
        for(Token t : testList){
            assertEquals(testList.get(i).getType(), testList2.get(i).getType());
            i++;
        }
    }
}