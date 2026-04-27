package api.client;

import api.dto.CreateUserDTO;
import api.pojo.CreateUserPOJO;
import api.pojo.GetUserPOJO;
import api.utils.APIRequestUtility;
import api.utils.ApiResponse;
import api.utils.GenericAPIRequestBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetStoreSwaggerAPI {

    private final String petStoreSwaggerBaseUrl = "https://petstore.swagger.io/v2";
    private final String createUserListEndpoint = "/user/createWithList";
    private final String createUser = "/user";
    private final String getUserByUsername = "/user/{username}";
    private final String updateUserByUsername = "/user/{username}";
    private final String deleteUserByUsername = "/user/{username}";


    public Map<String, String> swaggerAPICreateNewUserWithDTO(Map<String, String> headers, String username, String firstName, String lastName, String email, String password, String phone) {
        Map<String, String> responseMap = new HashMap<>();

        CreateUserDTO request = CreateUserDTO.builder()
                .id(1L)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .phone(phone)
                .userStatus(0)
                .build();

        ApiResponse<CreateUserPOJO> response = APIRequestUtility.makeApiRequest(
                petStoreSwaggerBaseUrl + createUser,
                headers,
                request,
                CreateUserPOJO.class,
                GenericAPIRequestBuilder.RequestMethod.POST,
                null,
                null
        );

        responseMap.put("statusCode", String.valueOf(response.getStatusCode()));
        responseMap.put("type", response.getBody().getType());

        return responseMap;
    }

    public Map<String, String> swaggerAPICreateNewUserListWithDTO(Map<String, String> headers, String username, String firstName, String lastName, String email, String password, String phone) {
        Map<String, String> responseMap = new HashMap<>();

        List<CreateUserDTO> request = List.of(CreateUserDTO.builder()
                .id(1L)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .phone(phone)
                .userStatus(0)
                .build());

        ApiResponse<CreateUserPOJO> response = APIRequestUtility.makeApiRequest(
                petStoreSwaggerBaseUrl + createUserListEndpoint,
                headers,
                request,
                CreateUserPOJO.class,
                GenericAPIRequestBuilder.RequestMethod.POST,
                null,
                null
        );

        responseMap.put("statusCode", String.valueOf(response.getStatusCode()));
        responseMap.put("type", response.getBody().getType());
        responseMap.put("message", response.getBody().getMessage());

        return responseMap;
    }

    public ApiResponse<CreateUserPOJO> swaggerAPICreateNewUserWithMap(Map<String, String> headers) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("id", "0");
        requestBody.put("username", "eran1");
        requestBody.put("firstName", "eran1");
        requestBody.put("lastName", "yaha1");
        requestBody.put("email", "yaha1@yopmail.com");
        requestBody.put("password", "123456");
        requestBody.put("phone", "90210");
        requestBody.put("userStatus", "0");

        return APIRequestUtility.makeApiRequest(
                petStoreSwaggerBaseUrl + createUserListEndpoint,
                headers,
                requestBody,
                CreateUserPOJO.class,
                GenericAPIRequestBuilder.RequestMethod.POST,
                null,
                null
        );
    }

    public Map<String, String> swaggerAPIGetUserDetails(Map<String, String> headers, String username) {
        Map<String, String> responseMap = new HashMap<>();

        ApiResponse<GetUserPOJO> response = APIRequestUtility.makeApiRequest(
                petStoreSwaggerBaseUrl + getUserByUsername.replace("{username}", username),
                headers,
                null,
                GetUserPOJO.class,
                GenericAPIRequestBuilder.RequestMethod.GET,
                null,
                null
        );

        responseMap.put("statusCode", String.valueOf(response.getStatusCode()));
        String userName = responseMap.put("userName", response.getBody().getUsername());
        responseMap.put("email", response.getBody().getEmail());
        responseMap.put("password", response.getBody().getPassword());


        return responseMap;
    }

    public Map<String, String> swaggerAPIUpdateUserWithDTO(Map<String, String> headers, String username, String firstName, String lastName, String email, String password, String phone) {
        Map<String, String> responseMap = new HashMap<>();

        CreateUserDTO request = CreateUserDTO.builder()
                .id(1L)
                .username(username)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(password)
                .phone(phone)
                .userStatus(0)
                .build();

        ApiResponse<CreateUserPOJO> response = APIRequestUtility.makeApiRequest(
                petStoreSwaggerBaseUrl + updateUserByUsername.replace("{username}", username),
                headers,
                request,
                CreateUserPOJO.class,
                GenericAPIRequestBuilder.RequestMethod.PUT,
                null,
                null
        );

        responseMap.put("statusCode", String.valueOf(response.getStatusCode()));
        responseMap.put("type", response.getBody().getType());

        return responseMap;
    }

    public Map<String, String> swaggerAPIDeleteUser(Map<String, String> headers, String username) {
        Map<String, String> responseMap = new HashMap<>();

        ApiResponse<CreateUserPOJO> response = APIRequestUtility.makeApiRequest(
                petStoreSwaggerBaseUrl + getUserByUsername.replace("{username}", username),
                headers,
                null,
                CreateUserPOJO.class,
                GenericAPIRequestBuilder.RequestMethod.DELETE,
                null,
                null
        );

        responseMap.put("statusCode", String.valueOf(response.getStatusCode()));

        return responseMap;
    }
}
