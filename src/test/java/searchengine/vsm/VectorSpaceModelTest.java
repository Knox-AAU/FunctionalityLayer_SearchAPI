package searchengine.vsm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VectorSpaceModelTest {

    static Document doc1;
    static Document doc2;
    static Document doc3;
    static Document doc4;
    static VectorSpaceModel vsm;
    static List<Document> documents;
    static List<ScoredDocument> scoredDocuments;

    @BeforeAll
    static void setup() {
        // arrange
        vsm = new VectorSpaceModel("is denmark smaller than russia");

        documents = new ArrayList<>();
        scoredDocuments = new ArrayList<>();

        doc1 = new Document("test1", "/test/test1");
        doc1.getTF().put("china", 1);
        doc1.getTF().put("bigger", 1);
        doc1.getTF().put("germany", 1);
        documents.add(doc1);

        doc2 = new Document("test2", "/test/test2");
        doc2.getTF().put("russia", 1);
        doc2.getTF().put("bigger", 1);
        doc2.getTF().put("denmark", 1);
        documents.add(doc2);

        doc3 = new Document("test3", "/test/test3");
        doc3.getTF().put("russia", 1);
        doc3.getTF().put("bigger", 1);
        doc3.getTF().put("monaco", 1);
        documents.add(doc3);

        doc4 = new Document("test4", "/test/test4");
        doc4.getTF().put("monaco", 1);
        doc4.getTF().put("smaller", 1);
        doc4.getTF().put("germany", 1);
        documents.add(doc4);

        // act
        scoredDocuments = vsm.getScoredDocuments(documents);
    }

    @Test
    void assertScore() {
        // assert score
        assertEquals(0.0, scoredDocuments.get(0).getScore());
        assertEquals(1.4606636323886681, scoredDocuments.get(1).getScore());
        assertEquals(0.7513001189965488, scoredDocuments.get(2).getScore());
        assertEquals(0.9041143105210954, scoredDocuments.get(3).getScore());
    }

    @Test
    void assertTitle() {
        // assert title
        assertEquals("test1", scoredDocuments.get(0).getTitle());
        assertEquals("test2", scoredDocuments.get(1).getTitle());
        assertEquals("test3", scoredDocuments.get(2).getTitle());
        assertEquals("test4", scoredDocuments.get(3).getTitle());
    }

    @Test
    void assertFilePath() {
        // assert filepath
        assertEquals("/test/test1", scoredDocuments.get(0).getFilepath());
        assertEquals("/test/test2", scoredDocuments.get(1).getFilepath());
        assertEquals("/test/test3", scoredDocuments.get(2).getFilepath());
        assertEquals("/test/test4", scoredDocuments.get(3).getFilepath());
    }
}