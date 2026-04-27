package configurations.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;

public class DBServiceProvider {

    public static Connection createSqliteConnection(String schemaURL, String dbName, String username, String password) {
        String finalUrl = schemaURL;

        // אם ה-URL מכיל את הנתיב הישן של המק, נחליף אותו בנתיב יחסי לפרויקט
        if (schemaURL.contains("/Users/eranyahalom/")) {
            String projectPath = System.getProperty("user.dir");
            // אנחנו מניחים שהקובץ נמצא בתוך src/test/resources/db/
            String relativePath = "/src/test/resources/db/Chinook.db";
            finalUrl = "jdbc:sqlite:" + projectPath + relativePath;

            System.out.println("⚠️ Detected hardcoded Mac path. Redirecting to: " + finalUrl);
        }

        try {
            return DriverManager.getConnection(finalUrl);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to SQLite DB at: " + finalUrl, e);
        }
    }
}