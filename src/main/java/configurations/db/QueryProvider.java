package configurations.db;

import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class QueryProvider {

    private static JSONObject queries;

    static {
        loadQueries();
    }

    private static void loadQueries() {
        try {
            InputStream is = QueryProvider.class.getClassLoader()
                    .getResourceAsStream("configurations/DBQueries.json");

            if (is == null) {
                throw new RuntimeException("DBQueries.json not found");
            }

            String json = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            queries = new JSONObject(json);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load queries", e);
        }
    }

    public static String getQuery(String key) {
        return queries.getString(key);
    }
}