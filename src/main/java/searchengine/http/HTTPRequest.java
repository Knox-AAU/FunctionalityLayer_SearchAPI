package searchengine.http;

import org.apache.http.client.utils.URIBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
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
     * Sends a http request based on the fields of the HTTPRequest and returns the response.
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
            } else if(Body != null && !Body.isEmpty()) {
                 throw new Exception(GetMethod() + " does not support a request body, but the request body was set");
            }

            // Sends and reads response
            String response = ReadResponse(http);

            // The HTTP request was successful and a response can be returned
            return httpResponse(200, true, response);
        }
        catch (URISyntaxException exception) {
            return httpResponse(500, false, "Internal server error - Bad URL syntax");
        } catch (Exception e) {
            return httpResponse(500, false, e.getMessage());
        }
    }

    /**
     * Creates an IHTTPResponse object with the properties specified
     * @param status the status code to set in the response
     * @param success whether the http request that created the response
     * @param response the content of the response
     * @return a new IHTTPResponse
     */
    private IHTTPResponse httpResponse(int status, boolean success, String response) {
        return new IHTTPResponse() {
            @Override
            public int GetStatus() {
                return status;
            }

            @Override
            public boolean GetSuccess() {
                return success;
            }

            @Override
            public String GetContent() {
                return response;
            }
        };
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
     * This function sets the method of the HTTP request. Only POST and GET are currently supported
     * @param method must be POST or GET (case insensitive)
     * @throws HttpRequestMethodNotSupportedException
     */
    @Override
    public void SetMethod(String method) throws HttpRequestMethodNotSupportedException {
        method = method.toUpperCase(Locale.ROOT);
        switch (method) {
            case "POST", "GET" -> this.method = method;
            default -> throw new HttpRequestMethodNotSupportedException(method + " is not a supported method.");
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

    /**
     * Encodes a list of parameters and adds them to an uriBuilder
     * @param uriBuilder the uriBuilder which needs to have the parameters added
     * @param queryParameters the parameters to add to the uriBuilder.
     */
    private void UrlEncodeQueryParameters(URIBuilder uriBuilder, HashMap<String, String[]> queryParameters) {
        //Do nothing if there are no parameters
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
