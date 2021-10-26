package searchengine.http;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.util.UriBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

/**
 * Class for simplifying sending HTTP requests
 * Currently always sends and accepts json data
 */
public class HTTPRequest implements IHTTPRequest{
    public HTTPRequest(String url){
        Method = "GET";
        SetUrl(url);
        QueryParameters = new HashMap<String, String[]>();
    }
    public HTTPRequest(String url, HashMap<String, String[]> queryParameters){
        this(url);
        SetQueryParameters(queryParameters);
    }

    private String Method;
    private String Url;
    private HashMap<String, String[]> QueryParameters;
    private String Body;

    /**
     * Sends the request and returns the response
     * @return IHTTPResponse from request
     */
    @Override
    public IHTTPResponse Send() {
        try {
            URIBuilder uriBuilder = new URIBuilder(GetUrl());
            UrlEncodeQueryParameters(uriBuilder, GetQueryParameters());
            URL url = new URL(uriBuilder.toString());
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(GetMethod());
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Content-Type", "application/json");

            if(RequestCanContainBody()) {
                //Write request body
                http.setDoOutput(true);//Required for getting the output stream
                http.getOutputStream().write(Body.getBytes(StandardCharsets.UTF_8));//Writes the body to the output stream as UTF-8 encoded bytes
            }
            else if(!Body.isEmpty()){
                 throw new Exception(GetMethod() + " does not support a request body, but the request body was set");
            }

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
     * @param method must be POST (case insensitive)
     * @throws HttpRequestMethodNotSupportedException
     */
    @Override
    public void SetMethod(String method) throws HttpRequestMethodNotSupportedException {
        method = method.toUpperCase(Locale.ROOT);
        switch (method){
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
    public HashMap<String, String[]> GetQueryParameters() {
        return QueryParameters;
    }

    @Override
    public void SetQueryParameters(HashMap<String, String[]> queryParameters) {
        QueryParameters = queryParameters;
    }

    @Override
    public void AddQueryParameter(String parameter, String[] values) {
        QueryParameters.put(parameter, values);
    }

    @Override
    public void SetBody(String body) { Body = body; }

    /**
     * If HTTP requests of the current HTTP request method can contain a body.
     * @return true|false true if the request can contain a body
     */
    private boolean RequestCanContainBody(){
        switch (GetMethod()){
            case "POST":
                return true;
        }
        return false;
    }

    //TODO javadoc og kontroller at uribuilder kan tage arrays og lister og format korrekt
    private void UrlEncodeQueryParameters(URIBuilder uriBuilder, HashMap<String, String[]> queryParameters) {
        //Return empty string if no query parameters
        if (queryParameters.isEmpty()){ return; }

        Set<String> keySet = queryParameters.keySet();
        for(String key : keySet){
            String[] values = queryParameters.get(key);
            for(String value : values) {
                uriBuilder.addParameter(key, value);
            }
        }
    }
}
