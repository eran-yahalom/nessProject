package api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShipResponsePOJO {

    @JsonProperty("data")
    private List<DataItem> data;

    @JsonProperty("total")
    private int total;

    @JsonProperty("page")
    private int page;

    @JsonProperty("per_page")
    private int perPage;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataItem {

        private String id;
        private String title;
        private boolean completed;

        private String createdAt;
        private String updatedAt;
    }
}