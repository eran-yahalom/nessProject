package api.client;

import api.pojo.GetAllUsersDataPOJO;
import api.pojo.LoginResponsePOJO;
import api.pojo.SingleUserResponsePOJO;
import api.pojo.UpdateUserResponsePOJO;
import api.utils.APIRequestUtility;
import api.utils.ApiResponse;
import api.utils.GenericAPIRequestBuilder;

import java.util.HashMap;
import java.util.Map;


public class ReqresAPI {

    private final String baseUrl = "https://reqres.in/api/";
    private final String GETUsers = "users?page=2";
    private final String SingleUser = "users/";
    private final String userLogin = "login";

    public ApiResponse<GetAllUsersDataPOJO> getUsersDetails(Map<String, String> headers) {

        return APIRequestUtility.makeApiRequest(
                baseUrl + GETUsers,
                headers,
                null,
                GetAllUsersDataPOJO.class,
                GenericAPIRequestBuilder.RequestMethod.GET,
                null,
                null
        );
    }

    public ApiResponse<SingleUserResponsePOJO> getSingleUserDetails(Map<String, String> headers, String id) {

        return APIRequestUtility.makeApiRequest(
                baseUrl + SingleUser + id,
                headers,
                null,
                SingleUserResponsePOJO.class,
                GenericAPIRequestBuilder.RequestMethod.GET,
                null,
                null
        );
    }

    public ApiResponse<LoginResponsePOJO> userLogin(Map<String, String> headers, String email, String password) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);
        requestBody.put("password", password);

        return APIRequestUtility.makeApiRequest(
                baseUrl + userLogin,
                headers,
                requestBody,
                LoginResponsePOJO.class,
                GenericAPIRequestBuilder.RequestMethod.POST,
                null,
                null
        );
    }

    public ApiResponse<UpdateUserResponsePOJO> updateUser(Map<String, String> headers, String userName, String job, String id) {

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", userName);
        requestBody.put("job", job);

        return APIRequestUtility.makeApiRequest(
                baseUrl + SingleUser + id,
                headers,
                requestBody,
                UpdateUserResponsePOJO.class,
                GenericAPIRequestBuilder.RequestMethod.PUT,
                null,
                null
        );
    }

    public ApiResponse<Void> deleteUser(Map<String, String> headers, String id) {

        return APIRequestUtility.makeApiRequest(
                baseUrl + SingleUser + id,
                headers,
                null,
                Void.class,   // ✅ correct
                GenericAPIRequestBuilder.RequestMethod.DELETE,
                null,
                null
        );
    }
}