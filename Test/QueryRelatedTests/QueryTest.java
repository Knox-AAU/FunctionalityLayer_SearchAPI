package QueryRelatedTests;

import org.junit.jupiter.api.Test;
import searchengine.query.Query;

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
                "}" +
                "ORDER BY DESC(?itemLabel)" +
                "LIMIT 5";
        Query query1 = new Query(querystring, "https://query.wikidata.org/sparql");

        String expected = "{\n" +
                "  \"head\" : {\n" +
                "    \"vars\" : [ \"item\", \"itemLabel\" ]\n" +
                "  },\n" +
                "  \"results\" : {\n" +
                "    \"bindings\" : [ {\n" +
                "      \"item\" : {\n" +
                "        \"type\" : \"uri\",\n" +
                "        \"value\" : \"http://www.wikidata.org/entity/Q61133276\"\n" +
                "      },\n" +
                "      \"itemLabel\" : {\n" +
                "        \"xml:lang\" : \"en\",\n" +
                "        \"type\" : \"literal\",\n" +
                "        \"value\" : \"Şero\"\n" +
                "      }\n" +
                "    }, {\n" +
                "      \"item\" : {\n" +
                "        \"type\" : \"uri\",\n" +
                "        \"value\" : \"http://www.wikidata.org/entity/Q94993819\"\n" +
                "      },\n" +
                "      \"itemLabel\" : {\n" +
                "        \"xml:lang\" : \"en\",\n" +
                "        \"type\" : \"literal\",\n" +
                "        \"value\" : \"Zipper\"\n" +
                "      }\n" +
                "    }, {\n" +
                "      \"item\" : {\n" +
                "        \"type\" : \"uri\",\n" +
                "        \"value\" : \"http://www.wikidata.org/entity/Q2300851\"\n" +
                "      },\n" +
                "      \"itemLabel\" : {\n" +
                "        \"xml:lang\" : \"en\",\n" +
                "        \"type\" : \"literal\",\n" +
                "        \"value\" : \"Winnie\"\n" +
                "      }\n" +
                "    }, {\n" +
                "      \"item\" : {\n" +
                "        \"type\" : \"uri\",\n" +
                "        \"value\" : \"http://www.wikidata.org/entity/Q10393754\"\n" +
                "      },\n" +
                "      \"itemLabel\" : {\n" +
                "        \"xml:lang\" : \"en\",\n" +
                "        \"type\" : \"literal\",\n" +
                "        \"value\" : \"Willow\"\n" +
                "      }\n" +
                "    }, {\n" +
                "      \"item\" : {\n" +
                "        \"type\" : \"uri\",\n" +
                "        \"value\" : \"http://www.wikidata.org/entity/Q1759652\"\n" +
                "      },\n" +
                "      \"itemLabel\" : {\n" +
                "        \"xml:lang\" : \"en\",\n" +
                "        \"type\" : \"literal\",\n" +
                "        \"value\" : \"Wilberforce\"\n" +
                "      }\n" +
                "    } ]\n" +
                "  }\n" +
                "}";
        // act
        String result = query1.execute();

        // assert
        assertEquals(expected, result);

    }
}