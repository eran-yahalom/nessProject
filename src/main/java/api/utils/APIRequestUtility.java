package api.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import api.utils.GenericAPIRequestBuilder.RequestMethod;

import java.util.HashMap;
import java.util.Map;

public class APIRequestUtility {

    public static <T> ApiResponse<T> makeApiRequest(
            String url,
            Map<String, String> headers,
            Object body,
            Class<T> responseType,
            RequestMethod method,
            String pathParamKey,
            Map<String, Object> pathParams
    ) {
        RequestSpecification request = RestAssured.given();

        if (headers != null) {
            request.headers(headers);
        }

        if (body != null) {
            request.body(body);
        }

        if (pathParams != null) {
            request.pathParams(pathParams);
        }

        long startTime = System.currentTimeMillis();

        Response response;

        switch (method) {
            case GET:
                response = request.get(url);
                break;
            case POST:
                response = request.post(url);
                break;
            case PUT:
                response = request.put(url);
                break;
            case DELETE:
                response = request.delete(url);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }

        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;

        if (response.getStatusCode() < 200 || response.getStatusCode() >= 300) {
            throw new RuntimeException(
                    "API FAILED! Status: " + response.getStatusCode() +
                            " Body: " + response.getBody().asString()
            );
        }

        if (response.getBody() != null && !response.getBody().asString().isEmpty()) {

            String contentType = response.getContentType();

            if (contentType == null || !contentType.contains("application/json")) {
                throw new RuntimeException("Response is not JSON!");
            }
        }

        Map<String, String> responseHeaders = new HashMap<>();
        response.getHeaders().forEach(h ->
                responseHeaders.put(h.getName(), h.getValue())
        );

        String responseString = response.getBody() != null ? response.getBody().asString() : null;

        T responseBody = null;

        if (responseType != null &&
                responseString != null &&
                !responseString.isEmpty()) {

            responseBody = response.as(responseType);
        }

        return new ApiResponse<T>(
                response.getStatusCode(),
                responseBody,
                responseHeaders,
                responseTime
        );
    }
}