package searchengine.vsm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import searchengine.Document;

import static org.junit.jupiter.api.Assertions.*;

class DocumentTest {

    /* Arrange */
    static Document doc;
    static Document doc1;

    /* Act */
    @BeforeAll
    static void testSetUp(){
        doc = new Document(1, 0.97871);
        doc1 = new Document(2, 0.91231);
    }

    /* Assert*/
    @Test
    void getId() {
        assertEquals(1, doc.getId());
    }
    /* Assert*/
    @Test
    void getScore() {
        assertEquals(0.97871, doc.getScore());
    }
    /* Assert*/
    @Test
    void compareTo() {
        assertEquals(1  , doc.getScore().compareTo(doc1.getScore()));
    }
}