package models.testdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchTestData {
    private String query;
    private double maxPrice;
    private int limit;
}