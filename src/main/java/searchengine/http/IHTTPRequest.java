package searchengine.http;

import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.io.IOException;
import java.util.HashMap;

public interface IHTTPRequest {
    IHTTPResponse Send() throws IOException;
    String GetMethod();
    void SetMethod(String method) throws HttpRequestMethodNotSupportedException;
    String GetUrl();
    void SetUrl(String url);
    HashMap<String, String[]> GetQueryParameters();
    void SetQueryParameters(HashMap<String, String[]> queryParameters);
    void AddQueryParameter(String parameter, String[] values);
    void SetBody(String url);
}
