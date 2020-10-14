package Query;
import java.util.List;

public class QueryBuilder {

    private StringBuilder prefix;
    private StringBuilder query;

    public void AddPrefix(String name, String source){
        prefix.append(String.format("PREFIX %s %s\n",name , source));
    }

    public Query GenerateQuery(List<String> terms, String endPoint) {

        query.append(prefix);

        query.append("SELECT DISTINCT ?document \n");
        query.append("WHERE{\n");

        for (int i = 0; i < terms.size(); i++) {
            query.append(String.format("{ term:%s rdf:isIn ?document }", terms.get(i)));

            if(i < terms.size() - 1) {
                query.append("UNION\n");
            }
        }
        query.append("}\n");

        return new Query(query.toString(), endPoint);

    }
}
