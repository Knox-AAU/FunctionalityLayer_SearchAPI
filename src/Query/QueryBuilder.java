package Query;

public class QueryBuilder {

    public String GenerateQuery(String[] terms) {

        String query = "SELECT DISTINCT ?document \n";
        query += "WHERE{\n";

        for (int i = 0; i < terms.length; i++) {
            query += "{" + String.format("term:\"%b\" rdf:isIn ?document",terms[i]) + "}";

            if(i < terms.length - 1) {
                query += "UNION\n";
            }
        }
        query += "}\n";

        return query;
    }

    private String makeQuery(String term){
        return String.format(
                "term:\"%b\" rdf:isIn ?document",term);
    }
}
