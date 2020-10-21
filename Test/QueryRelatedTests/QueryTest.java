package QueryRelatedTests;

import org.junit.jupiter.api.Test;
import query.Query;
import query.QueryBuilder;
import tokenizer.Tokenizer;

import static org.junit.jupiter.api.Assertions.*;

class QueryTest {

    @Test
    void execute() {
        // arrange
        String querystring = "SELECT ?item ?itemLabel \n" +
                "WHERE \n" +
                "{\n" +
                "  ?item wdt:P31 wd:Q146.\n" +
                "  SERVICE wikibase:label { bd:serviceParam wikibase:language \"[AUTO_LANGUAGE],en\". }\n" +
                "} LIMIT 5";
        Query query1 = new Query(querystring, "https://query.wikidata.org/sparql");

        String expected = "[{\"item\":\"http://www.wikidata.org/entity/Q378619\",\"itemLabel\":\"CC\"},{\"item\":\"http://www.wikidata.org/entity/Q498787\",\"itemLabel\":\"Muezza\"},{\"item\":\"http://www.wikidata.org/entity/Q677525\",\"itemLabel\":\"Orangey\"},{\"item\":\"http://www.wikidata.org/entity/Q851190\",\"itemLabel\":\"Mrs. Chippy\"},{\"item\":\"http://www.wikidata.org/entity/Q1050083\",\"itemLabel\":\"Catmando\"}]";

        // act
        String result = query1.execute();

        // assert
        assertEquals(result, expected);

    }
}