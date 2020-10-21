package query;

import java.util.List;

public class QueryBuilder {

    private StringBuilder query;

    public Query generateQuery(List<String> terms, String endPoint){

        this.query = new StringBuilder();

        query.append("SELECT DISTINCT ?document \n");
        query.append("WHERE{\n");

        for (int i = 0; i < terms.size(); i++) {
            query.append(String.format("{ %s rdf:isIn ?document }", terms.get(i)));
            if(i < terms.size() - 1) {
                query.append("UNION\n");
            }
        }

        query.append("}\n");

        return new Query(query.toString(), endPoint);
    }

    public Query generateQuery(String query, String endPoint){

        this.query = new StringBuilder(query);
        return new Query(query.toString(), endPoint);
    }
}
