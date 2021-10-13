package searchengine.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;

public class HTTPGetRequest implements IHTTPRequest{
    public HTTPGetRequest(String url){
        Method = "GET";
        SetUrl(url);
    }
    public HTTPGetRequest(String url, HashMap<String, String> queryParameters){
        this(url);
        SetQueryParameters(queryParameters);
    }

    private String Method;
    private String Url;
    private HashMap<String, String> QueryParameters;

    @Override
    public IHTTPResponse Commit() {
        HttpURLConnection connection;
        try {
            URL url = new URL(GetUrl());
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(GetMethod());
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
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
                    return content.toString();
                }
            };
        }
        catch(Exception exception){
            //Do nothing TODO find out if we should handle this
        }
        finally{
            //if (connection != null) connection.disconnect();
        }
        return null;
    }

    @Override
    public String GetMethod() {
        return Method;
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
}
