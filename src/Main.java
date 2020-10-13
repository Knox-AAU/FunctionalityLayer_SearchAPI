import Query.QueryBuilder;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        QueryBuilder q = new QueryBuilder();
        List<String> t = Arrays.asList("Pumpe", "roer", "vand");
        System.out.println(new QueryBuilder().GenerateQuery(t));
    }
}
