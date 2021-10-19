package searchengine.http;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

public interface IHTTPRequest {
    IHTTPResponse Commit() throws IOException;
    String GetMethod();
    String GetUrl();
    void SetUrl(String url);
    HashMap<String, String> GetQueryParameters();
    void SetQueryParameters(HashMap<String, String> queryParameters);
    void AddQueryParameter(String parameter, String value);
}