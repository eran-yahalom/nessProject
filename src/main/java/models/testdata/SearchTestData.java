package models.testdata;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchTestData {
    private String query;
    private double maxPrice;
    private int limit;
}