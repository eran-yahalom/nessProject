package services;

import configurations.db.QueryExecutor;
import io.cucumber.guice.ScenarioScoped;
import lombok.extern.log4j.Log4j2;
import utils.GeneratorUtils;

import java.util.List;
import java.util.Map;

@Log4j2
@ScenarioScoped
public class RegistrationService {

    private String dateOfBirth;
    List<String> creds;

    public List<String> registerRandomUserInDB() {

        try {
            String email = GeneratorUtils.generateEmail();
            String password = GeneratorUtils.generatePassword();
            String first_name = GeneratorUtils.generateFirstName();
            String last_name = GeneratorUtils.generateLastName();
            String address = GeneratorUtils.address();
            String phoneNumber = GeneratorUtils.phoneNumber();
            String city = GeneratorUtils.city();
            String country = GeneratorUtils.country();
            String dateOfBirth = GeneratorUtils.dateOfBirth().toString();

            QueryExecutor.executeUpdate(
                    "set_new_customer",
                    first_name, last_name, email, phoneNumber, address, city, country);

            String id = QueryExecutor.executeQueryAsTable(
                    "get_customer_id_by_email",
                    email).getFirst().get("id").toString();


            QueryExecutor.executeUpdate(
                    "set_new_customer_auth",
                    password, 1, 0, 3, id);

            return List.of(email, password);
        } catch (Exception e) {
            throw new RuntimeException("Failed to register user in DB", e);
        }
    }

    public List<String> getRandomUserLoginCredentials() {
        try {
            Map<String, Object> credentials = QueryExecutor.executeQueryAsTable("get_random_customer_user_and_password", 1).getFirst();
            String email = credentials.get("email").toString();
            String password = credentials.get("password").toString();

            if (credentials.isEmpty()) {
                throw new RuntimeException("No credentials found for email: " + email);
            }

            return List.of(email, password);
        } catch (Exception e) {
            log.error("Fail to get random credentials {}", e.getMessage());
            throw new RuntimeException("Failed to retrieve user credentials from DB", e);
        }
    }
}
