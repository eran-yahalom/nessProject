package api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateUserResponsePOJO {

    private String name;
    private String job;
    private String updatedAt;

    @JsonProperty("_meta")
    private Meta meta;

    @Data
    public static class Meta {
        private String powered_by;
        private String docs_url;
        private String upgrade_url;
        private String example_url;
        private String variant;
        private String message;
        private String context;
        private Cta cta;
    }

    @Data
    public static class Cta {
        private String label;
        private String url;
    }
}
