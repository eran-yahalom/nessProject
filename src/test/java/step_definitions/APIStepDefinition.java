package step_definitions;

import api.client.PetStoreSwaggerAPI;
import api.client.ReqresAPI;
import api.pojo.GetAllUsersDataPOJO;
import api.pojo.LoginResponsePOJO;
import api.pojo.SingleUserResponsePOJO;
import api.pojo.UpdateUserResponsePOJO;
import api.utils.APIUtils;
import api.utils.ApiResponse;
import com.google.inject.Inject;
import com.google.inject.Provider;
import configurations.db.QueryExecutor;
import context.ScenarioState;
import context.StateKeys;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import utils.GeneratorUtils;
import utils.ScenarioContext;

import java.util.List;
import java.util.Map;

@ScenarioScoped
public class APIStepDefinition {


    private final Provider<ReqresAPI> reqresAPIProvider;
    private final Provider<PetStoreSwaggerAPI> petStoreSwaggerAPIProvider;

    @Inject
    public APIStepDefinition(Provider<ReqresAPI> reqresAPIProvider,
                             Provider<PetStoreSwaggerAPI> petStoreSwaggerAPIProvider) {
        this.reqresAPIProvider = reqresAPIProvider;
        this.petStoreSwaggerAPIProvider = petStoreSwaggerAPIProvider;
    }


    @When("API request all users details")
    public void APIRequestAllUsersDetails() {
        ReqresAPI reqresApi = reqresAPIProvider.get();
        ApiResponse<GetAllUsersDataPOJO> response =
                reqresApi.getUsersDetails(APIUtils.getPostmanHeaders());

        ScenarioState.save(StateKeys.GET_ALL_USERS, response);
        Assert.assertNotNull(response.getBody(), "Response body should not be null");
    }

    @When("API creates a random user")
    public void APICreatesARandomUser() {
        PetStoreSwaggerAPI petStoreSwaggerAPI = petStoreSwaggerAPIProvider.get();
        Map<String, String> swaggerResult = petStoreSwaggerAPI.swaggerAPICreateNewUserWithDTO(
                APIUtils.perStoreSwaggerHeaders(),
                GeneratorUtils.generateUserName(),
                GeneratorUtils.generateFirstName(),
                GeneratorUtils.generateLastName(),
                GeneratorUtils.generateEmail(),
                GeneratorUtils.generatePassword(),
                GeneratorUtils.phoneNumber());
        ScenarioState.save(StateKeys.SWAGGER_CREATE_USER, swaggerResult);

        Assert.assertNotNull(swaggerResult);
    }

    @When("API updates user details for user")
    public void APIUpdatesUserDetails() {
        String userNameFromDB = QueryExecutor.executeQueryAsTable(
                "get_user_data_by_user_name").getFirst().get("user_bame").toString();

        PetStoreSwaggerAPI petStoreSwaggerAPI = petStoreSwaggerAPIProvider.get();

        Map<String, String> swaggerResult = petStoreSwaggerAPI.swaggerAPIUpdateUserWithDTO(
                APIUtils.perStoreSwaggerHeaders(),
                userNameFromDB,
                GeneratorUtils.generateFirstName(),
                GeneratorUtils.generateLastName(),
                GeneratorUtils.generateEmail(),
                GeneratorUtils.generatePassword(),
                GeneratorUtils.phoneNumber()
        );

        ScenarioState.save(StateKeys.SWAGGER_UPDATE_USER, swaggerResult);

        Assert.assertNotNull(swaggerResult);
    }

    @When("API deletes user with user name")
    public void APIDeletesUser() {
        String userNameFromDB = QueryExecutor.executeQueryAsTable(
                "get_user_data_by_user_name").getFirst().get("user_bame").toString();

        PetStoreSwaggerAPI petStoreSwaggerAPI = petStoreSwaggerAPIProvider.get();
        Map<String, String> swaggerResult = petStoreSwaggerAPI.swaggerAPIDeleteUser(APIUtils.perStoreSwaggerHeaders(),
                userNameFromDB);
//        ScenarioContext.save("statusCode", swaggerResult.get("statusCode"));
        ScenarioState.save(StateKeys.SWAGGER_DELETE_USER, swaggerResult);

        Assert.assertNotNull(swaggerResult);
    }


    @Then("API response should be successful with status code {string} and type {string}")
    public void APIResponseShouldBeSuccessfulWithStatusCodeAndType(String expectedStatusCode, String expectedType) {
        Map<String, String> createUserResponse =
                ScenarioState.get(StateKeys.SWAGGER_CREATE_USER, Map.class);

        Assert.assertEquals(createUserResponse.get("statusCode"), expectedStatusCode, "Expected status code does not match actual");
        Assert.assertEquals(createUserResponse.get("type"), expectedType, "Expected type does not match actual");
    }

    @Then("API CREATE LIST response should be successful with status code {string} and type {string}")
    public void APICreateListResponseShouldBeSuccessfulWithStatusCodeAndType(String expectedStatusCode, String expectedType) {
        Map<String, String> createListResponse =
                ScenarioState.get(StateKeys.SWAGGER_CREATE_USER_LIST, Map.class);

        Assert.assertEquals(createListResponse.get("statusCode"), expectedStatusCode, "Expected status code does not match actual");
        Assert.assertEquals(createListResponse.get("type"), expectedType, "Expected type does not match actual");
    }

    @Then("API response should be successful with status code {int}")
    public void APIResponseShouldBeSuccessfulWithStatusCode(int expectedStatusCode) {
        ApiResponse<GetAllUsersDataPOJO> response =
                ScenarioState.get(StateKeys.GET_ALL_USERS, ApiResponse.class);

        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Expected status code does not match actual");
    }

    @Then("number of users in the response should be {int}")
    public void numberOfUsersInTheResponseShouldBe(int expectedUserCount) {
        ApiResponse<GetAllUsersDataPOJO> getAllUsersResponse =
                ScenarioState.get(StateKeys.GET_ALL_USERS, ApiResponse.class);

        Assert.assertEquals(getAllUsersResponse.getBody().getTotal(), expectedUserCount, "Expected user count does not match actual");
    }

    @When("API request user details with id {string}")
    public void APIRequestUserDetailsWithId(String userId) {
        ScenarioContext.save("userId", userId);
        ReqresAPI reqresApi = reqresAPIProvider.get();
        ApiResponse<SingleUserResponsePOJO> response = reqresApi.getSingleUserDetails(APIUtils.getPostmanHeaders(), userId);
        ScenarioState.save(StateKeys.SINGLE_USER, response);

        Assert.assertNotNull(response.getBody(), "Response body should not be null");
    }

    @And("API response user mail should be {string}")
    public void APIResponseUserMailShouldBe(String expectedEmail) {
        ApiResponse<SingleUserResponsePOJO> response =
                ScenarioState.get(StateKeys.SINGLE_USER, ApiResponse.class);

        Assert.assertEquals(response.getBody().getData().getEmail(), expectedEmail, "Expected email does not match actual");
    }

    @And("API response user first name should be {string} and last name should be {string}")
    public void APIResponseUserFirstNameShouldBeAndLastNameShouldBe(String expectedFirstName, String expectedLastName) {
        List<Map<String, Object>> result = QueryExecutor.executeQueryAsTable(
                "get_customer_data",
                ScenarioContext.get(StateKeys.USER_ID, String.class));

        ApiResponse<SingleUserResponsePOJO> response =
                ScenarioState.get(StateKeys.SINGLE_USER, ApiResponse.class);

        Assert.assertEquals(response.getBody().getData().getEmail(), result.getFirst().get("Email").toString(), "Expected first name does not match actual");
        Assert.assertEquals(response.getBody().getData().getFirstName(), expectedFirstName, "Expected first name does not match actual");
        Assert.assertEquals(response.getBody().getData().getLastName(), expectedLastName, "Expected last name does not match actual");
    }

    @When("API request user login with username {string} and password {string}")
    public void APIRequestUserLogin(String username, String password) {
        ReqresAPI reqresApi = reqresAPIProvider.get();
        ApiResponse<LoginResponsePOJO> request = reqresApi.userLogin(APIUtils.geUTF8PostmanHeaders(), username, password);

        ScenarioState.save(StateKeys.LOGIN, request);
        Assert.assertNotNull(request.getBody(), "Response body should not be null");
    }

    @Then("API LOGIN response should be successful")
    public void APILOGINResponseShouldBeSuccessful() {
        ApiResponse<LoginResponsePOJO> loginResponse =
                ScenarioState.get(StateKeys.LOGIN, ApiResponse.class);

        Assert.assertEquals(loginResponse.getStatusCode(), 200, "Expected status code does not match actual");
        Assert.assertNotNull(loginResponse.getBody().getToken(), "Login token should not be null");
    }

    @When("API updated user details with name {string} and job {string} for user with id {string}")
    public void APIUpdatedUserDetailsWithIdAndNameAndJob(String name, String job, String id) {
        ReqresAPI reqresApi = reqresAPIProvider.get();
        ApiResponse<UpdateUserResponsePOJO> request = reqresApi.updateUser(APIUtils.geUTF8PostmanHeaders(), name, job, id);
        ScenarioState.save(StateKeys.UPDATE_USER, request);

        Assert.assertNotNull(request.getBody(), "Response body should not be null");
    }

    @Then("API UPDATE response should be successful with updated name {string} and job {string}")
    public void APIUPDATEResponseShouldBeSuccessfulWithUpdatedNameAndJob(String expectedName, String expectedJob) {

        ApiResponse<UpdateUserResponsePOJO> updateUserResponse =
                ScenarioState.get(StateKeys.UPDATE_USER, ApiResponse.class);

        Assert.assertEquals(updateUserResponse.getStatusCode(), 200, "Expected status code does not match actual");
        Assert.assertEquals(updateUserResponse.getBody().getName(), expectedName, "Expected name does not match actual");
        Assert.assertEquals(updateUserResponse.getBody().getJob(), expectedJob, "Expected job does not match actual");
    }

    @When("API deletes user details with id {string}")
    public void APIRDeletesUserWithId(String userId) {
        ReqresAPI reqresApi = reqresAPIProvider.get();
        ApiResponse<Void> request = reqresApi.deleteUser(APIUtils.getPostmanHeaders(), userId);

        ScenarioState.save(StateKeys.DELETE_USER, request);

        Assert.assertNotNull(request, "Delete user response should not be null");
    }

    @Then("API DELETE response should be successful with status code {int}")
    public void APIDELETEResponseShouldBeSuccessfulWithStatusCode(int expectedStatusCode) {
        ApiResponse<Void> deleteUserResponse = ScenarioState.get(StateKeys.DELETE_USER, ApiResponse.class);
        Assert.assertEquals(deleteUserResponse.getStatusCode(), expectedStatusCode, "Expected status code does not match actual");
    }

    @When("API request user details with user name")
    public void APIRequestUserDetailsWithUserName() {
        PetStoreSwaggerAPI petStoreSwaggerAPI = petStoreSwaggerAPIProvider.get();

        List<Map<String, Object>> queryResults = QueryExecutor.executeQueryAsTable(
                "get_user_data_by_user_name");

        String userName = queryResults.getFirst().get("user_bame").toString();
        String email = queryResults.getFirst().get("email").toString();

        ScenarioContext.save(StateKeys.DB_USER_NAME, userName);
        ScenarioContext.save(StateKeys.DB_EMAIL, email);


        Map<String, String> swaggerResult = petStoreSwaggerAPI.swaggerAPIGetUserDetails(APIUtils.perStoreSwaggerHeaders(), userName);
        ScenarioState.save(StateKeys.SWAGGER_GET_USER, swaggerResult);
    }

    @Then("API get user details response should be successful with status code {string}")
    public void APIGetUserDetailsResponseShouldBeSuccessfulWithStatusCode(String expectedStatusCode) {

        Map<String, String> getUserDetails =
                ScenarioState.get(StateKeys.SWAGGER_GET_USER, Map.class);

        Assert.assertEquals(getUserDetails.get("statusCode"), expectedStatusCode, "Expected status code does not match actual");
        Assert.assertEquals(getUserDetails.get("userName"), ScenarioContext.get(StateKeys.DB_USER_NAME, String.class), "Expected user name does not match actual");
        Assert.assertEquals(getUserDetails.get("email"), ScenarioContext.get(StateKeys.DB_EMAIL, String.class), "Expected email does not match actual");
    }

    @Then("API update user details response should be successful with status code {string}")
    public void APIUpdateUserDetailsResponseShouldBeSuccessfulWithStatusCode(String expectedStatusCode) {

        Map<String, String> swaggerUpdateResult =
                ScenarioState.get(StateKeys.SWAGGER_UPDATE_USER, Map.class);

        Assert.assertEquals(swaggerUpdateResult.get("statusCode"), expectedStatusCode, "Expected status code does not match actual");
    }

    @Then("API delete user response should be successful with status code {string}")
    public void APIDeleteUserResponseShouldBeSuccessfulWithStatusCode(String expectedStatusCode) {
        Map<String, String> swaggerDeleteResult =
                ScenarioState.get(StateKeys.SWAGGER_DELETE_USER, Map.class);
        Assert.assertEquals(swaggerDeleteResult.get("statusCode"), expectedStatusCode, "Expected status code does not match actual");
    }

    @When("API creates a list of random users")
    public void APICreatesAListOfRandomUsers() {
        PetStoreSwaggerAPI petStoreSwaggerAPI = petStoreSwaggerAPIProvider.get();
        Map<String, String> swaggerResult = petStoreSwaggerAPI.swaggerAPICreateNewUserListWithDTO(
                APIUtils.perStoreSwaggerHeaders(),
                GeneratorUtils.generateUserName(),
                GeneratorUtils.generateFirstName(),
                GeneratorUtils.generateLastName(),
                GeneratorUtils.generateEmail(),
                GeneratorUtils.generatePassword(),
                GeneratorUtils.phoneNumber()
        );

        ScenarioState.save(StateKeys.SWAGGER_CREATE_USER_LIST, swaggerResult);

        Assert.assertNotNull(swaggerResult, "Swagger response should not be null");
    }
}