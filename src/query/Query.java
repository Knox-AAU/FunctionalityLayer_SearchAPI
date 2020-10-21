package query;

import com.bordercloud.sparql.*;
import java.net.URI;
import java.util.HashMap;

public class Query {

    private String query;
    private String endPoint;

    public Query(String query, String endPoint) {
        this.query = query;
        this.endPoint = endPoint;
    }

    public String execute() {

        String response = null;

        try {
            URI endpointURI = URI.create(endPoint);

            SparqlClient sc = new SparqlClient(false);
            sc.setEndpointRead(endpointURI);
            SparqlResult sr = sc.query(query);

            response = sr.resultRaw;
        }
        catch(SparqlClientException e){
            System.out.println(e);
            e.printStackTrace();
        }

        return response;
    }

    private static void printResult(SparqlResultModel rs , int size) {

        for (String variable : rs.getVariables()) {
            System.out.print(String.format("%-"+size+"."+size+"s", variable ) + " | ");
        }
        System.out.print("\n");
        for (HashMap<String, Object> row : rs.getRows()) {
            for (String variable : rs.getVariables()) {
                System.out.print(String.format("%-"+size+"."+size+"s", row.get(variable)) + " | ");
            }
            System.out.print("\n");
        }
    }

    @Override
    public int hashCode(){
        int result = query.hashCode();
        result += endPoint.hashCode();
        return result;
    }
}
