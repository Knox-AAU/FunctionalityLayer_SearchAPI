package searchengine.booleanretrieval;

import org.junit.jupiter.api.Test;
import searchengine.ISqlConnection;
import searchengine.vsm.TFIDFDocument;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DataSelectionTest {

    /** Handles the Arrange part of the tests (can't use @BeforeAll since it will give race condition errors) */
    static DataSelection setup() {
        ISqlConnection connection = mock(ISqlConnection.class);
        Statement statement = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        try {
            when(connection.createStatement()).thenReturn(statement);
            when(statement.executeQuery(anyString())).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true, true, true, false);
            when(resultSet.getString("wordname")).thenReturn("lorem", "ipsum", "dolor");
            when(resultSet.getInt("amount")).thenReturn(5, 4, 3);
            when(resultSet.getString("articletitle")).thenReturn("testarticle1", "testarticle1","testarticle2");
            when(resultSet.getString("filepath")).thenReturn("/test/testarticle1", "testarticle1","/test/testarticle2");

            return new DataSelection(connection);
        }
        catch (Exception exception){
            fail(exception.getMessage());
        }
        return null;
    }

    @Test
    void retrieveDocuments_mockedArticles_articleCount() {
        //Arrange
        DataSelection dataSelection = setup();

        //Act
        List<TFIDFDocument> documentList = dataSelection.retrieveDocuments("this string is mocked - see setup");

        //Assert
        assertEquals(2, documentList.size());
    }

    @Test
    void retrieveDocuments_mockedArticles_article1() {
        //Arrange
        DataSelection dataSelection = setup();

        //Act
        List<TFIDFDocument> documentList = dataSelection.retrieveDocuments("this string is mocked - see setup");

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
        List<TFIDFDocument> documentList = dataSelection.retrieveDocuments("this string is mocked - see setup");

        //Assert
        assertEquals(1, documentList.get(1).getTF().size());
        assertEquals(3, documentList.get(1).getTF().get("dolor"));
    }
}