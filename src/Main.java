import Query.QueryBuilder;

public class Main {

    public static void main(String[] args) {
        QueryBuilder q = new QueryBuilder();
        String[] t = {"hej", "med", "dig"};
        System.out.println(new QueryBuilder().GenerateQuery(t));
    }
}
