package api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleUserResponsePOJO {

    private DataItem data;
    private Support support;

    @JsonProperty("_meta")
    private Meta meta;

    // ===================== DATA =====================
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataItem {

        private int id;
        private String email;

        @JsonProperty("first_name")
        private String firstName;

        @JsonProperty("last_name")
        private String lastName;

        private String avatar;
    }

    // ===================== SUPPORT =====================
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Support {

        private String url;
        private String text;
    }

    // ===================== META =====================
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Meta {

        @JsonProperty("powered_by")
        private String poweredBy;

        @JsonProperty("docs_url")
        private String docsUrl;

        @JsonProperty("upgrade_url")
        private String upgradeUrl;

        @JsonProperty("example_url")
        private String exampleUrl;

        private String variant;
        private String message;

        private Cta cta;
        private String context;
    }

    // ===================== CTA =====================
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Cta {

        private String label;
        private String url;
    }
}