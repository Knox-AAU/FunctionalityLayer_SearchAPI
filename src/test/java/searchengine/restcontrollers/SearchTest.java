package searchengine.restcontrollers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {

    static Search search;

    //arrange
    @BeforeAll
    static void setup(){
        search = new Search("test.txt");
    }


    @Test
    void getResult() {
    }
}