package searchengine.restcontrollers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {

    static Search search;

    //arrange
    @BeforeAll
    static void setup(){
        search = new Search("test");
    }

    @Test
    void getTitle() {
        //assert
        assertEquals("TITLE", search.getResult());
    }

    @Test
    void getPdfPath() {
        //assert
        //assertEquals("placeholder.pdf", search.getPdfPath());
    }

    @Test
    void getRank() {
        //assert
        //assertEquals(1, search.getRank());
    }
}