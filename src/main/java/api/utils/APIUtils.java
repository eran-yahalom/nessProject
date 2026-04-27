package api.utils;

import java.util.HashMap;
import java.util.Map;

public class APIUtils {

    public static Map<String, String> getPostmanHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("x-api-key", "reqres_19e403e39f404de8b734a4acd6f8b1d0");

        return headers;
    }

    public static Map<String, String> geUTF8PostmanHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json; charset=UTF-8");
        headers.put("x-api-key", "reqres_19e403e39f404de8b734a4acd6f8b1d0");

        return headers;
    }

    public static Map<String, String> getBearerTokenHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Authorization", "Bearer 12345");

        return headers;
    }

    public static Map<String, String> perStoreSwaggerHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");
        headers.put("Content-Type", "application/json");

        return headers;
    }
}
