package searchengine.http;

public interface IHTTPResponse {
    int GetStatus();
    boolean GetSuccess();
    String GetContent();
}
