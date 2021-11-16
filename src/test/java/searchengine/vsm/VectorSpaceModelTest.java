package searchengine.vsm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VectorSpaceModelTest {

    static TFIDFDocument doc1;
    static TFIDFDocument doc2;
    static TFIDFDocument doc3;
    static TFIDFDocument doc4;
    static VectorSpaceModel vsm;
    static List<TFIDFDocument> TFIDFDocuments;
    static List<ScoredDocument> scoredDocuments;

    @BeforeAll
    static void setup() {
        // arrange
        TFIDFDocuments = new ArrayList<>();
        scoredDocuments = new ArrayList<>();

        doc1 = new TFIDFDocument("test1", 1);
        doc1.getTF().put("china", 1);
        doc1.getTF().put("bigger", 1);
        doc1.getTF().put("germany", 1);
        TFIDFDocuments.add(doc1);

        doc2 = new TFIDFDocument("test2", 2);
        doc2.getTF().put("russia", 1);
        doc2.getTF().put("bigger", 1);
        doc2.getTF().put("denmark", 1);
        TFIDFDocuments.add(doc2);

        doc3 = new TFIDFDocument("test3", 3);
        doc3.getTF().put("russia", 1);
        doc3.getTF().put("bigger", 1);
        doc3.getTF().put("monaco", 1);
        TFIDFDocuments.add(doc3);

        doc4 = new TFIDFDocument("test4", 4);
        doc4.getTF().put("monaco", 1);
        doc4.getTF().put("smaller", 1);
        doc4.getTF().put("germany", 1);
        TFIDFDocuments.add(doc4);
    }

    /*@Test
    void assertScore() {
        // arrange
        vsm = new VectorSpaceModel("is denmark smaller than russia");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert score
        assertEquals(0.34385784723000323, scoredDocuments.get(0).getScore());
        assertEquals(0.2356163728957791, scoredDocuments.get(1).getScore());
        assertEquals(0.10823004887457252, scoredDocuments.get(2).getScore());
        assertEquals(0.0, scoredDocuments.get(3).getScore());
    }*/

    @Test
    void assertTitle() {
        // arrange
        vsm = new VectorSpaceModel("is denmark smaller than russia");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert title
        assertEquals("test2", scoredDocuments.get(0).getTitle());
        assertEquals("test4", scoredDocuments.get(1).getTitle());
        assertEquals("test3", scoredDocuments.get(2).getTitle());
        assertEquals("test1", scoredDocuments.get(3).getTitle());
    }

    @Test
    void assertId() {
        // arrange
        vsm = new VectorSpaceModel("is denmark smaller than russia");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert filepath
        assertEquals(2, scoredDocuments.get(0).getId());
        assertEquals(4, scoredDocuments.get(1).getId());
        assertEquals(3, scoredDocuments.get(2).getId());
        assertEquals(1, scoredDocuments.get(3).getId());
    }

    @Test
    void assertTopRankedTest1() {
        // arrange
        vsm = new VectorSpaceModel("is china bigger than germany");

        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals("test1", scoredDocuments.get(0).getTitle());
    }

    @Test
    void assertTopRankedTest2() {
        // arrange
        vsm = new VectorSpaceModel("is russia bigger than denmark");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals("test2", scoredDocuments.get(0).getTitle());
    }

    @Test
    void assertTopRankedTest3() {
        // arrange
        vsm = new VectorSpaceModel("is russia bigger than monaco");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals("test3", scoredDocuments.get(0).getTitle());
    }

    @Test
    void assertTopRankedTest4() {
        // arrange
        vsm = new VectorSpaceModel("is monaco smaller than germany");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals("test4", scoredDocuments.get(0).getTitle());
    }

    /*@Test
    void scoreTest() {
        // arrange
        vsm = new VectorSpaceModel("china bigger germany");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals(0.9301177892693178, scoredDocuments.get(0).getScore());
    }

    @Test
    void scoreTest2() {
        // arrange
        vsm = new VectorSpaceModel("russia bigger denmark");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals(0.9301177892693175, scoredDocuments.get(0).getScore());
    }

    @Test
    void scoreTest3() {
        // arrange
        vsm = new VectorSpaceModel("russia bigger monaco");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals(0.9655362872834565, scoredDocuments.get(0).getScore());
    }

    @Test
    void scoreTest4() {
        // arrange
        vsm = new VectorSpaceModel("monaco smaller germany");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals(0.9715945583978263, scoredDocuments.get(0).getScore());
    }*/

    @Test
    void noMatchTest() {
        // arrange
        vsm = new VectorSpaceModel("test");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals(0.0, scoredDocuments.get(0).getScore());
        assertEquals(0.0, scoredDocuments.get(1).getScore());
        assertEquals(0.0, scoredDocuments.get(2).getScore());
        assertEquals(0.0, scoredDocuments.get(3).getScore());
    }

    @Test
    void everyTermUsedTest() {
        // arrange
        vsm = new VectorSpaceModel("china bigger germany russia denmark monaco smaller");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals("test4", scoredDocuments.get(0).getTitle());
        //assertEquals(0.6109921299502986, scoredDocuments.get(0).getScore());

        assertEquals("test1", scoredDocuments.get(1).getTitle());
        //assertEquals(0.5430663322561895, scoredDocuments.get(1).getScore());

        assertEquals("test2", scoredDocuments.get(2).getTitle());
        //assertEquals(0.5430663322561893, scoredDocuments.get(2).getScore());

        assertEquals("test3", scoredDocuments.get(3).getTitle());
        //assertEquals(0.3653415212942519, scoredDocuments.get(3).getScore());
    }

    @Test
    void repeatingTermTest() {
        // arrange
        vsm = new VectorSpaceModel("china china china china china china china china china china bigger germany russia denmark monaco smaller");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        //assert
        assertEquals("test1", scoredDocuments.get(0).getTitle());
        //assertEquals(0.7093575280501593, scoredDocuments.get(0).getScore());

        assertEquals("test4", scoredDocuments.get(1).getTitle());
        //assertEquals(0.11736775500057654, scoredDocuments.get(1).getScore());

        assertEquals("test2", scoredDocuments.get(2).getTitle());
        //assertEquals(0.10431963540756405, scoredDocuments.get(2).getScore());

        assertEquals("test3", scoredDocuments.get(3).getTitle());
        //assertEquals(0.07017981420855571, scoredDocuments.get(3).getScore());
    }

    @Test
    void singeTermSearchChina() {
        // arrange
        vsm = new VectorSpaceModel("china");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        //assert
        assertEquals("test1", scoredDocuments.get(0).getTitle());
        //assertEquals(1.3304820237218407, scoredDocuments.get(0).getScore());

        assertEquals(0.0, scoredDocuments.get(1).getScore());
        assertEquals(0.0, scoredDocuments.get(2).getScore());
        assertEquals(0.0, scoredDocuments.get(3).getScore());
    }

    @Test
    void singeTermSearchGermany() {
        // arrange
        vsm = new VectorSpaceModel("germany");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        //assert
        assertEquals("test1", scoredDocuments.get(0).getTitle());
        //assertEquals(2.1609640474436813, scoredDocuments.get(0).getScore());

        assertEquals("test4", scoredDocuments.get(1).getTitle());
        //assertEquals(2.1609640474436813, scoredDocuments.get(1).getScore());

        //assertEquals(0.0, scoredDocuments.get(2).getScore());
        //assertEquals(0.0, scoredDocuments.get(3).getScore());
    }

    @Test
    void singeTermSearchMonaco() {
        // arrange
        vsm = new VectorSpaceModel("monaco");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        //assert
        assertEquals("test3", scoredDocuments.get(0).getTitle());
        //assertEquals(2.1609640474436813, scoredDocuments.get(0).getScore());

        assertEquals("test4", scoredDocuments.get(1).getTitle());
        //assertEquals(2.1609640474436813, scoredDocuments.get(1).getScore());

        //assertEquals(0.0, scoredDocuments.get(2).getScore());
        //assertEquals(0.0, scoredDocuments.get(3).getScore());
    }

    @Test
    void foreignTextSearch() {
        // arrange
        vsm = new VectorSpaceModel("");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        //assert
        assertEquals("test1", scoredDocuments.get(0).getTitle());
    }
}