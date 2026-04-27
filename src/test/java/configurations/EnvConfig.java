package configurations;

import lombok.Data;
import java.util.Map;

@Data
public class EnvConfig {
    private Map<String, EnvironmentDetails> environments;

    @Data
    public static class EnvironmentDetails {
        private String url;
        private String schema;
        private String dbName;
        private String dbPass;
        private String username;
        private String password;
    }
}