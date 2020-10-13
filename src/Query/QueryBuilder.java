package Query;
import java.util.List;

public class QueryBuilder {

    public String GenerateQuery(List<String> terms) {

        String query = "SELECT DISTINCT ?document \n";
        query += "WHERE{\n";

        for (int i = 0; i < terms.size(); i++) {
            query += "{" + String.format("term:\"%s\" rdf:isIn ?document",terms.get(i)) + "}\n";

            if(i < terms.size() - 1) {
                query += "UNION\n";
            }
        }
        query += "}\n";
        return query;
    }
}
