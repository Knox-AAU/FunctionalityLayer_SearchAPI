package searchengine.vsm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import searchengine.Document;
import searchengine.ScoredDocument;
import searchengine.VectorSpaceModel;

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
        doc1.getTF().put("is", 1);
        doc1.getTF().put("bigger", 1);
        doc1.getTF().put("than", 1);
        doc1.getTF().put("germany", 1);
        documents.add(doc1);

        doc2 = new Document("test1", "/test/test2");
        doc2.getTF().put("russia", 1);
        doc2.getTF().put("is", 1);
        doc2.getTF().put("bigger", 1);
        doc2.getTF().put("than", 1);
        doc2.getTF().put("denmark", 1);
        documents.add(doc2);

        doc3 = new Document("test1", "/test/test3");
        doc3.getTF().put("monaco", 1);
        doc3.getTF().put("is", 1);
        doc3.getTF().put("bigger", 1);
        doc3.getTF().put("than", 1);
        doc3.getTF().put("russia", 1);
        documents.add(doc3);

        doc4 = new Document("test1", "/test/test4");
        doc4.getTF().put("monaco", 1);
        doc4.getTF().put("is", 1);
        doc4.getTF().put("smaller", 1);
        doc4.getTF().put("than", 1);
        doc4.getTF().put("germany", 1);
        documents.add(doc4);

    }

    @Test
    void getScoredDocuments() {
        // act
        scoredDocuments = vsm.getScoredDocuments(documents);
        // assert
        assertEquals(0.9833348077203219, scoredDocuments.get(0).getScore());
        assertEquals(2.410689524797079, scoredDocuments.get(1).getScore());
        assertEquals(2.5044097360019095, scoredDocuments.get(2).getScore());
        assertEquals(1.6443215345808004, scoredDocuments.get(3).getScore());
    }
}