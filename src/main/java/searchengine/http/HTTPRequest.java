package searchengine.http;

import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;

/**
 * Class for simplifying sending HTTP requests
 * Currently always sends and accepts json data
 */
public class HTTPRequest implements IHTTPRequest{
    public HTTPRequest(String url){
        SetUrl(url);
    }
    public HTTPRequest(String url, HashMap<String, String> queryParameters){
        this(url);
        SetQueryParameters(queryParameters);
    }

    private String Method = "GET";
    private String Url;
    private HashMap<String, String> QueryParameters;
    private String Body;

    /**
     * Sends the request and returns the response
     * @return IHTTPResponse from request
     */
    @Override
    public IHTTPResponse Commit() {
        try {
            String queryParameters = "";//TODO url_encode parameters
            URL url = new URL(GetUrl() + queryParameters);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(GetMethod());
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Content-Type", "application/json");

            //Write request body
            http.setDoOutput(true);
            http.getOutputStream().write(Body.getBytes(StandardCharsets.UTF_8));

            String response = ReadResponse(http);

            return new IHTTPResponse() {
                @Override
                public int GetStatus() {
                    return 200;
                }

                @Override
                public boolean GetSuccess() {
                    return true;
                }

                @Override
                public String GetContent() {
                    return response;
                }
            };
        }
        catch(Exception exception){
            //TODO Give more accurate status code and error message
            return new IHTTPResponse() {
                @Override
                public int GetStatus() {
                    return 500;
                }

                @Override
                public boolean GetSuccess() {
                    return false;
                }

                @Override
                public String GetContent() {
                    return "Internal server error";
                }
            };
        }
    }

    /**
     * Reads the response of the request and returns it
     * @param http
     * @return response from http request
     * @throws IOException
     */
    private String ReadResponse(HttpURLConnection http) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        }
        return content.toString();
    }

    @Override
    public String GetMethod() {
        return Method;
    }

    /**
     * @param method must be GET or POST (case insensitive)
     * @throws HttpRequestMethodNotSupportedException
     */
    @Override
    public void SetMethod(String method) throws HttpRequestMethodNotSupportedException {
        method = method.toUpperCase(Locale.ROOT);
        switch (method){
            case "GET":
            case "POST":
                Method = method; break;
            default:
                throw new HttpRequestMethodNotSupportedException(method + " is not a supported method.");
        }
    }

    @Override
    public String GetUrl() {
        return Url;
    }

    @Override
    public void SetUrl(String url) {
        Url = url;
    }

    @Override
    public HashMap<String, String> GetQueryParameters() {
        return QueryParameters;
    }

    @Override
    public void SetQueryParameters(HashMap<String, String> queryParameters) {
        QueryParameters = queryParameters;
    }

    @Override
    public void AddQueryParameter(String parameter, String value) {
        QueryParameters.put(parameter, value);
    }

    @Override
    public void SetBody(String body) {
        Body = body;
    }
}
