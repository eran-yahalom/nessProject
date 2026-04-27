package api.utils;

import java.util.Map;

public class ApiResponse<T> {

    private int statusCode;
    private T body;
    private Map<String, String> headers;
    private long responseTime;

    public ApiResponse(int statusCode, T body, Map<String, String> headers, long responseTime) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
        this.responseTime = responseTime;
    }

    // Getters
    public int getStatusCode() {
        return statusCode;
    }

    public T getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public long getResponseTime() {
        return responseTime;
    }
}