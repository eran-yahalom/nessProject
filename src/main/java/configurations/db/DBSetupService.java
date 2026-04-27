package configurations.db;

import java.sql.Connection;
import java.sql.SQLException;

public class DBSetupService {

    public static void init(String schemaURL, String dbName, String username, String password) {
        Connection connection = DBServiceProvider.createSqliteConnection(schemaURL, dbName, username, password);
        DBThreadLocal.set(connection);
    }

    public static void close() {
        Connection connection = DBThreadLocal.get();

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        DBThreadLocal.remove();
    }
}