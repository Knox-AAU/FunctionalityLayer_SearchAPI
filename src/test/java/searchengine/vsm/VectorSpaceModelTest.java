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

    @Test
    void assertScore() {
        // arrange
        vsm = new VectorSpaceModel("is denmark smaller than russia");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert score
        assertEquals(1.4273547170767573, scoredDocuments.get(0).getScore());
        assertEquals(0.9870592488560553, scoredDocuments.get(1).getScore());
        assertEquals(0.731332001624914, scoredDocuments.get(2).getScore());
        assertEquals(0.0, scoredDocuments.get(3).getScore());
    }

    @Test
    void assertTitle() {
        // arrange
        vsm = new VectorSpaceModel("is denmark smaller than russia");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert title
        assertEquals("test2", scoredDocuments.get(0).getTitle());
        assertEquals("test3", scoredDocuments.get(1).getTitle());
        assertEquals("test4", scoredDocuments.get(2).getTitle());
        assertEquals("test1", scoredDocuments.get(3).getTitle());
    }

    @Test
    void assertId() {
        // arrange
        vsm = new VectorSpaceModel("is denmark smaller than russia");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert filepath
        assertEquals(1, scoredDocuments.get(0).getId());
        assertEquals(2, scoredDocuments.get(1).getId());
        assertEquals(3, scoredDocuments.get(2).getId());
        assertEquals(4, scoredDocuments.get(3).getId());
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

    @Test
    void scoreTest() {
        // arrange
        vsm = new VectorSpaceModel("china bigger germany");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals(2.5031447906311417, scoredDocuments.get(0).getScore());
    }

    @Test
    void scoreTest2() {
        // arrange
        vsm = new VectorSpaceModel("russia bigger denmark");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals(2.503144790631141, scoredDocuments.get(0).getScore());
    }

    @Test
    void scoreTest3() {
        // arrange
        vsm = new VectorSpaceModel("russia bigger monaco");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals(3.8951843062652745, scoredDocuments.get(0).getScore());
    }

    @Test
    void scoreTest4() {
        // arrange
        vsm = new VectorSpaceModel("monaco smaller germany");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals(2.3370064440880896, scoredDocuments.get(0).getScore());
    }

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
        assertEquals("test3", scoredDocuments.get(0).getTitle());
        assertEquals(2.2416054288023926, scoredDocuments.get(0).getScore());

        assertEquals("test1", scoredDocuments.get(1).getTitle());
        assertEquals(1.5700301339865201, scoredDocuments.get(1).getScore());

        assertEquals("test2", scoredDocuments.get(2).getTitle());
        assertEquals(1.5700301339865201, scoredDocuments.get(2).getScore());

        assertEquals("test4", scoredDocuments.get(3).getTitle());
        assertEquals(1.5214403057136017, scoredDocuments.get(3).getScore());
    }

    @Test
    void repeatingTermTest() {
        // arrange
        vsm = new VectorSpaceModel("china china china china china china china china china china bigger germany russia denmark monaco smaller");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        //assert
        assertEquals("test3", scoredDocuments.get(0).getTitle());
        assertEquals(2.5153784203804257, scoredDocuments.get(0).getScore());

        assertEquals("test1", scoredDocuments.get(1).getTitle());
        assertEquals(1.6460873488243788, scoredDocuments.get(1).getScore());

        assertEquals("test2", scoredDocuments.get(2).getTitle());
        assertEquals(1.6460873488243788, scoredDocuments.get(2).getScore());

        assertEquals("test4", scoredDocuments.get(3).getTitle());
        assertEquals(1.5370040701664518, scoredDocuments.get(3).getScore());
    }

    @Test
    void singeTermSearchChina() {
        // arrange
        vsm = new VectorSpaceModel("china");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        //assert
        assertEquals("test1", scoredDocuments.get(0).getTitle());
        assertEquals(1.4606636323886684, scoredDocuments.get(0).getScore());

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
        assertEquals(1.4606636323886684, scoredDocuments.get(0).getScore());

        assertEquals("test4", scoredDocuments.get(1).getTitle());
        assertEquals(1.356171465781643, scoredDocuments.get(1).getScore());

        assertEquals(0.0, scoredDocuments.get(2).getScore());
        assertEquals(0.0, scoredDocuments.get(3).getScore());
    }

    @Test
    void singeTermSearchMonaco() {
        // arrange
        vsm = new VectorSpaceModel("monaco");
        // act
        scoredDocuments = vsm.getScoredDocuments( TFIDFDocuments );
        //assert
        assertEquals("test3", scoredDocuments.get(0).getTitle());
        assertEquals(2.253900356989646, scoredDocuments.get(0).getScore());

        assertEquals("test4", scoredDocuments.get(1).getTitle());
        assertEquals(1.356171465781643, scoredDocuments.get(1).getScore());

        assertEquals(0.0, scoredDocuments.get(2).getScore());
        assertEquals(0.0, scoredDocuments.get(3).getScore());
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