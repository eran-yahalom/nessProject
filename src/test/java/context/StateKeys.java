package context;

/**
 * Central place for ALL ScenarioState keys
 * Used across all StepDefinition classes
 */
public final class StateKeys {

    private StateKeys() {
        // prevent instantiation
    }

    // =========================
    // Reqres / Users API
    // =========================
    public static final String GET_ALL_USERS = "getAllUsers";
    public static final String SINGLE_USER = "singleUser";
    public static final String LOGIN = "login";
    public static final String UPDATE_USER = "updateUser";
    public static final String DELETE_USER = "deleteUser";

    // =========================
    // PetStore API
    // =========================
    public static final String SWAGGER_CREATE_USER = "swaggerCreateUser";
    public static final String SWAGGER_UPDATE_USER = "swaggerUpdateUser";
    public static final String SWAGGER_DELETE_USER = "swaggerDeleteUser";
    public static final String SWAGGER_GET_USER = "swaggerGetUser";
    public static final String SWAGGER_CREATE_USER_LIST = "swaggerCreateUserList";

    // =========================
    // Generic / Shared
    // =========================
    public static final String STATUS_CODE = "statusCode";
    public static final String TYPE = "type";
    public static final String USER_ID = "userId";
    public static final String USER_NAME = "userName";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_PASSWORD = "userPassword";

    // =========================
    // DB / External data
    // =========================
    public static final String DB_USER_NAME = "dbUserName";
    public static final String DB_EMAIL = "dbEmail";
    public static final String DB_PASSWORD="dbPassword";
}