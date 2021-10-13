package searchengine.http;

import java.util.HashMap;

public interface IHTTPRequest {
    IHTTPResponse Commit();
    String GetMethod();
    String GetUrl();
    void SetUrl(String url);
    HashMap<String, String> GetQueryParameters();
    void SetQueryParameters(HashMap<String, String> queryParameters);
    void AddQueryParameter(String parameter, String value);
}
