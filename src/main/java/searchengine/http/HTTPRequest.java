package searchengine.http;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
        method = "GET";
        SetUrl(url);
        queryParameters = new HashMap<>();
    }

    public HTTPRequest(String url, HashMap<String, String[]> queryParameters){
        this(url);
        SetQueryParameters(queryParameters);
    }

    private String method;
    private String url;
    private HashMap<String, String[]> queryParameters;
    private String Body;

    /**
     * Sends the request and returns the response
     * @return IHTTPResponse from request
     */
    @Override
    public IHTTPResponse Send() {
        try {
            // Set the http connection up with the correct URL, query parameters, method and content-type
            URIBuilder uriBuilder = new URIBuilder(url); // URI handles encoding of characters like æ, ø, å
            UrlEncodeQueryParameters(uriBuilder, queryParameters);
            URL url = new URL(uriBuilder.toString());
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setRequestProperty("Accept", "application/json"); // Set up to recieve JSON
            http.setRequestProperty("Content-Type", "application/json");

            // If the request is a POST, we need to allow access to the output stream
            if(RequestCanContainBody()) {
                //Write request body
                http.setDoOutput(true);//Required for getting the output stream
                http.getOutputStream().write(Body.getBytes(StandardCharsets.UTF_8));//Writes the body to the output stream as UTF-8 encoded bytes
            }
            else if(Body != null && !Body.isEmpty()){
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
     * @param http is a HTTP connection
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
        return method;
    }

    /**
     * @param method must be POST or GET (case insensitive)
     * @throws HttpRequestMethodNotSupportedException
     */
    @Override
    public void SetMethod(String method) throws HttpRequestMethodNotSupportedException {
        method = method.toUpperCase(Locale.ROOT);
        switch (method){
            case "POST":
            case "GET":
                this.method = method; break;
            default:
                throw new HttpRequestMethodNotSupportedException(method + " is not a supported method.");
        }
    }

    @Override
    public String GetUrl() {
        return url;
    }

    @Override
    public void SetUrl(String url) {
        this.url = url;
    }

    @Override
    public HashMap<String, String[]> GetQueryParameters() {
        return queryParameters;
    }

    @Override
    public void SetQueryParameters(HashMap<String, String[]> queryParameters) {
        this.queryParameters = queryParameters;
    }

    @Override
    public void AddQueryParameter(String parameter, String[] values) {
        queryParameters.put(parameter, values);
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
