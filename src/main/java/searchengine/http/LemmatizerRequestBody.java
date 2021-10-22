package searchengine.http;

public class LemmatizerRequestBody {
    public LemmatizerRequestBody(String string, String language){
        this.string = string;
        this.language = language;
    }
    public String string;
    public String language;
}
