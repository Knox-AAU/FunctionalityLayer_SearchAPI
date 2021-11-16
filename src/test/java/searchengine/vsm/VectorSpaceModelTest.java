package searchengine.vsm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


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
        doc1.getTFmap().put("china", 1);
        doc1.getTFmap().put("bigger", 1);
        doc1.getTFmap().put("germany", 1);
        TFIDFDocuments.add(doc1);

        doc2 = new TFIDFDocument("test2", 2);
        doc2.getTFmap().put("russia", 1);
        doc2.getTFmap().put("bigger", 1);
        doc2.getTFmap().put("denmark", 1);
        TFIDFDocuments.add(doc2);

        doc3 = new TFIDFDocument("test3", 3);
        doc3.getTFmap().put("russia", 1);
        doc3.getTFmap().put("bigger", 1);
        doc3.getTFmap().put("monaco", 1);
        TFIDFDocuments.add(doc3);

        doc4 = new TFIDFDocument("test4", 4);
        doc4.getTFmap().put("monaco", 1);
        doc4.getTFmap().put("smaller", 1);
        doc4.getTFmap().put("germany", 1);
        TFIDFDocuments.add(doc4);
    }
    @Test
    void assertTitle() {
        // arrange
        VectorSpaceModel vsmMock = spy(VectorSpaceModel.class);
        doReturn(4).when(vsmMock).getDocumentCount();
        vsmMock.createQueryTF("is denmark smaller than russia");
        // act
        scoredDocuments = vsmMock.getScoredDocuments( TFIDFDocuments );

        // assert title
        assertEquals("test2", scoredDocuments.get(0).getTitle());
        assertEquals("test4", scoredDocuments.get(1).getTitle());
        assertEquals("test3", scoredDocuments.get(2).getTitle());
        assertEquals("test1", scoredDocuments.get(3).getTitle());
    }

    @Test
    void assertId() {
        // arrange
        VectorSpaceModel vsmMock = spy(VectorSpaceModel.class);
        doReturn(4).when(vsmMock).getDocumentCount();
        vsmMock.createQueryTF("is denmark smaller than russia");
        // act
        scoredDocuments = vsmMock.getScoredDocuments( TFIDFDocuments );

        // assert filepath
        assertEquals(2, scoredDocuments.get(0).getId());
        assertEquals(4, scoredDocuments.get(1).getId());
        assertEquals(3, scoredDocuments.get(2).getId());
        assertEquals(1, scoredDocuments.get(3).getId());
    }

    @Test
    void assertTopRankedTest1() throws Exception {
        // arrange
        VectorSpaceModel vsmMock = spy(VectorSpaceModel.class);
        doReturn(4).when(vsmMock).getDocumentCount();
        vsmMock.createQueryTF("is china bigger than germany");
        // act
        scoredDocuments = vsmMock.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals("test1", scoredDocuments.get(0).getTitle());
    }

    @Test
    void assertTopRankedTest2() {
        // arrange
        VectorSpaceModel vsmMock = spy(VectorSpaceModel.class);
        doReturn(4).when(vsmMock).getDocumentCount();
        vsmMock.createQueryTF("is russia bigger than denmark");
        // act
        scoredDocuments = vsmMock.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals("test2", scoredDocuments.get(0).getTitle());
    }

    @Test
    void assertTopRankedTest3() {
        // arrange
        VectorSpaceModel vsmMock = spy(VectorSpaceModel.class);
        doReturn(4).when(vsmMock).getDocumentCount();
        vsmMock.createQueryTF("is russia bigger than monaco");
        // act
        scoredDocuments = vsmMock.getScoredDocuments( TFIDFDocuments );

        // assert
        assertEquals("test3", scoredDocuments.get(0).getTitle());
    }

    @Test
    void assertTopRankedTest4() {
        // arrange
        VectorSpaceModel vsmMock = spy(VectorSpaceModel.class);
        doReturn(4).when(vsmMock).getDocumentCount();
        vsmMock.createQueryTF("is monaco smaller than germany");
        // act
        scoredDocuments = vsmMock.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals("test4", scoredDocuments.get(0).getTitle());
    }

    @Test
    void noMatchTest() {
        // arrange
        VectorSpaceModel vsmMock = spy(VectorSpaceModel.class);
        doReturn(4).when(vsmMock).getDocumentCount();
        vsmMock.createQueryTF("test");
        // act
        scoredDocuments = vsmMock.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals(0.0, scoredDocuments.get(0).getScore());
        assertEquals(0.0, scoredDocuments.get(1).getScore());
        assertEquals(0.0, scoredDocuments.get(2).getScore());
        assertEquals(0.0, scoredDocuments.get(3).getScore());
    }

    @Test
    void everyTermUsedTest() {
        // arrange
        VectorSpaceModel vsmMock = spy(VectorSpaceModel.class);
        doReturn(4).when(vsmMock).getDocumentCount();
        vsmMock.createQueryTF("china bigger germany russia denmark monaco smaller");
        // act
        scoredDocuments = vsmMock.getScoredDocuments( TFIDFDocuments );
        // assert
        assertEquals("test4", scoredDocuments.get(0).getTitle());

        assertEquals("test1", scoredDocuments.get(1).getTitle());

        assertEquals("test2", scoredDocuments.get(2).getTitle());

        assertEquals("test3", scoredDocuments.get(3).getTitle());
    }

    @Test
    void repeatingTermTest() {

        // arrange
        VectorSpaceModel vsmMock = spy(VectorSpaceModel.class);
        doReturn(4).when(vsmMock).getDocumentCount();
        vsmMock.createQueryTF("china china china china china china china china china china bigger germany russia denmark monaco smaller");
        // act
        scoredDocuments = vsmMock.getScoredDocuments( TFIDFDocuments );
        //assert
        assertEquals("test1", scoredDocuments.get(0).getTitle());

        assertEquals("test4", scoredDocuments.get(1).getTitle());

        assertEquals("test2", scoredDocuments.get(2).getTitle());

        assertEquals("test3", scoredDocuments.get(3).getTitle());
    }

    @Test
    void singeTermSearchChina() {
        // arrange
        VectorSpaceModel vsmMock = spy(VectorSpaceModel.class);
        doReturn(4).when(vsmMock).getDocumentCount();
        vsmMock.createQueryTF("china");
        // act
        scoredDocuments = vsmMock.getScoredDocuments( TFIDFDocuments );
        //assert
        assertEquals("test1", scoredDocuments.get(0).getTitle());

        assertEquals(0.0, scoredDocuments.get(1).getScore());
        assertEquals(0.0, scoredDocuments.get(2).getScore());
        assertEquals(0.0, scoredDocuments.get(3).getScore());
    }

    @Test
    void singeTermSearchGermany() {
        // arrange
        VectorSpaceModel vsmMock = spy(VectorSpaceModel.class);
        doReturn(4).when(vsmMock).getDocumentCount();
        vsmMock.createQueryTF("germany");
        // act
        scoredDocuments = vsmMock.getScoredDocuments( TFIDFDocuments );
        //assert
        assertEquals("test1", scoredDocuments.get(0).getTitle());

        assertEquals("test4", scoredDocuments.get(1).getTitle());
    }

    @Test
    void singeTermSearchMonaco() {
        // arrange
        VectorSpaceModel vsmMock = spy(VectorSpaceModel.class);
        doReturn(4).when(vsmMock).getDocumentCount();
        vsmMock.createQueryTF("monaco");
        // act
        scoredDocuments = vsmMock.getScoredDocuments( TFIDFDocuments );
        //assert
        assertEquals("test3", scoredDocuments.get(0).getTitle());
        assertEquals("test4", scoredDocuments.get(1).getTitle());
    }

    @Test
    void foreignTextSearch() {
        // arrange
        VectorSpaceModel vsmMock = spy(VectorSpaceModel.class);
        doReturn(4).when(vsmMock).getDocumentCount();
        vsmMock.createQueryTF("");
        // act
        scoredDocuments = vsmMock.getScoredDocuments( TFIDFDocuments );
        //assert
        assertEquals("test1", scoredDocuments.get(0).getTitle());
    }
}