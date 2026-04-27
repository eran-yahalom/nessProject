package api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponsePOJO {
    private String name;
    private String job;
    private String id;
    private String createdAt;
}