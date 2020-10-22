package QueryRelatedTests;

import org.junit.jupiter.api.Test;
import searchengine.query.Query;
import searchengine.query.QueryBuilder;
import searchengine.tokenizer.Token;
import searchengine.tokenizer.Tokenizer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueryBuilderTest {

    Tokenizer testTokenizer = new Tokenizer("test for creating query");
    String testEndpoint = "https://example.org/sparql";

    @Test
    void generateQuery() {
        //arrange
        QueryBuilder queryBuilder = new QueryBuilder();
        List<Token> TokenList = testTokenizer.getFilteredTokens();

        //act
        Query query1 = queryBuilder.generateQuery(testTokenizer.getFilteredTokens(), testEndpoint);
        Query query2 = new Query(
                "SELECT DISTINCT ?document \n" +
                "WHERE{\n" +
                "{ test rdf:isIn ?document }" +
                "UNION\n" +
                "{ for rdf:isIn ?document }" +
                "UNION\n" +
                "{ creating rdf:isIn ?document }" +
                "UNION\n" +
                "{ query rdf:isIn ?document }}\n",
                "https://example.org/sparql");

        //assert
        assertEquals(query1.hashCode(), query2.hashCode());

    }

    @Test
    void testGenerateQuery() {
        //arrange

        //act

        //assert

    }
}