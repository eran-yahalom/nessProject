package configurations.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryExecutor {

    public static List<Map<String, Object>> executeQueryAsTable(String queryKey, Object... params) {

        String query = QueryProvider.getQuery(queryKey);

        try {
            Connection conn = DBThreadLocal.get();
            PreparedStatement stmt = conn.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            ResultSet rs = stmt.executeQuery();

            List<Map<String, Object>> results = new ArrayList<>();
            ResultSetMetaData meta = rs.getMetaData();
            int columns = meta.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();

                for (int i = 1; i <= columns; i++) {
                    row.put(meta.getColumnName(i), rs.getObject(i));
                }

                results.add(row);
            }

            return results;

        } catch (SQLException e) {
            throw new RuntimeException("Query execution failed: " + queryKey, e);
        }
    }

    // =========================
    // INSERT / UPDATE / DELETE
    // =========================
    public static int executeUpdate(String queryKey, Object... params) {

        String query = QueryProvider.getQuery(queryKey);

        try {
            Connection conn = DBThreadLocal.get();
            PreparedStatement stmt = conn.prepareStatement(query);

            bindParams(stmt, params);

            return stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("UPDATE query failed: " + queryKey, e);
        }
    }

    // =========================
    // INSERT + RETURN GENERATED KEY (optional)
    // =========================
    public static Object executeInsertAndReturnKey(String queryKey, Object... params) {

        String query = QueryProvider.getQuery(queryKey);

        try {
            Connection conn = DBThreadLocal.get();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            bindParams(stmt, params);

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getObject(1);
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("INSERT query failed: " + queryKey, e);
        }
    }

    // =========================
    // COMMON PARAM BINDER
    // =========================
    private static void bindParams(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }
}