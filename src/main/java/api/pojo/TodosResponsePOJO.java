package api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TodosResponsePOJO {

    private List<Object> data;
    private int total;
    private int page;
    private int per_page;
}