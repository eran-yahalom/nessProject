package api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetAllUsersDataPOJO {

    @JsonProperty("page")
    private int page;

    @JsonProperty("per_page")
    private int perPage;

    @JsonProperty("total")
    private int total;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("data")
    private List<UserData> data;

    @JsonProperty("support")
    private Support support;

    @JsonProperty("_meta")
    private Meta meta;

    // =========================
    // Nested Classes
    // =========================

    @Data
    public static class UserData {

        @JsonProperty("id")
        private int id;

        @JsonProperty("email")
        private String email;

        @JsonProperty("first_name")
        private String firstName;

        @JsonProperty("last_name")
        private String lastName;

        @JsonProperty("avatar")
        private String avatar;
    }

    @Data
    public static class Support {

        @JsonProperty("url")
        private String url;

        @JsonProperty("text")
        private String text;
    }

    @Data
    public static class Meta {

        @JsonProperty("powered_by")
        private String poweredBy;

        @JsonProperty("docs_url")
        private String docsUrl;

        @JsonProperty("upgrade_url")
        private String upgradeUrl;

        @JsonProperty("example_url")
        private String exampleUrl;

        @JsonProperty("variant")
        private String variant;

        @JsonProperty("message")
        private String message;

        @JsonProperty("cta")
        private Cta cta;

        @JsonProperty("context")
        private String context;
    }

    @Data
    public static class Cta {

        @JsonProperty("label")
        private String label;

        @JsonProperty("url")
        private String url;
    }
}