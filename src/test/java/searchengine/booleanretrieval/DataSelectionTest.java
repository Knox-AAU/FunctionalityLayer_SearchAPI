package searchengine.booleanretrieval;

import org.junit.jupiter.api.Test;
import searchengine.vsm.TFIDFDocument;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
// See https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html for mockito documentation
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * 
 * This test has been removed because of an endpoint change. It was changed from using an SQLConnection to using a HTTP request to the database instead.
 * The test is yet to be updated.
 * 
 */

class DataSelectionTest {

    /** Handles the Arrange part of the tests (can't use @BeforeAll since it will give race condition errors) */
    /*static DataSelection setup() {
        // Create mock for the connection the DataSelection needs
        // And mocks for the classes returned by the mocked connection
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        try {
            // Return the mocked statement when calling createStatement on the connection
            // Regardless of the query the executeQuery is called with, return the mocked result set
            when(statement.executeQuery(anyString())).thenReturn(resultSet);
            // The mocked result set has 3 entries (the 4th call will return false indicating there is no next entry in resultSet)
            when(resultSet.next()).thenReturn(true, true, true, false);
            // The mocked entries have 4 used labels: {"wordname", "amount", "articletitle", "filepath"}
            // The documents are created based on distinct article titles (so in this mock 2 articles are returned)
            when(resultSet.getString("wordname")).thenReturn("lorem", "ipsum", "dolor");
            when(resultSet.getInt("amount")).thenReturn(5, 4, 3);
            when(resultSet.getString("articletitle")).thenReturn("testarticle1", "testarticle1", "testarticle2");
            when(resultSet.getString("filepath")).thenReturn("/test/testarticle1", "/test/testarticle1", "/test/testarticle2");

            return new DataSelection();
        }
        catch (Exception exception){
            fail(exception.getMessage());
        }
        return null;
    }*/
/*
    @Test
    void retrieveDocuments_mockedArticles_articleCount() {
        //Arrange
        DataSelection dataSelection = setup();

        //Act
        List<TFIDFDocument> documentList = dataSelection.retrieveDocuments("this string is mocked - see setup",  new ArrayList<String>());

        //Assert
        assertEquals(2, documentList.size());
    }

    @Test
    void retrieveDocuments_mockedArticles_article1() {
        //Arrange
        DataSelection dataSelection = setup();

        //Act
        List<TFIDFDocument> documentList = dataSelection.retrieveDocuments("this string is mocked - see setup", new ArrayList<String>());

        //Assert
        assertEquals(2, documentList.get(0).getTF().size());
        assertEquals(5, documentList.get(0).getTF().get("lorem"));
        assertEquals(4, documentList.get(0).getTF().get("ipsum"));
    }

    @Test
    void retrieveDocuments_mockedArticles_article2() {
        //Arrange
        DataSelection dataSelection = setup();

        //Act
        List<TFIDFDocument> documentList = dataSelection.retrieveDocuments("this string is mocked - see setup", new ArrayList<String>());

        //Assert
        assertEquals(1, documentList.get(1).getTF().size());
        assertEquals(3, documentList.get(1).getTF().get("dolor"));
    }

 */
}
