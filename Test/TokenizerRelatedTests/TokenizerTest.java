package TokenizerRelatedTests;

import tokenizer.*;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {

    @Test
    void getFilteredTokens() {
        // arrange
        Tokenizer testTokenizer;
        List<Token> testList;
        List<Token> testList2 = new ArrayList<>();
        // act
        testTokenizer = new Tokenizer("test test1");
        testList = testTokenizer.getFilteredTokens();

        testList2.add(new Token(TokenType.KEYWORD, "test"));
        testList2.add(new Token(TokenType.KEYWORD, "test1"));

        // assert
        //value test
        assertEquals(testList.get(0).getValue(), testList2.get(0).getValue());
        assertEquals(testList.get(1).getValue(), testList2.get(1).getValue());
        //TokenType test
        assertEquals(testList.get(0).getType(), testList2.get(0).getType());
        assertEquals(testList.get(1).getType(), testList2.get(1).getType());

    }
}