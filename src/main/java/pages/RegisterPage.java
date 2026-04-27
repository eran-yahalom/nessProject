package pages;

import com.google.inject.Inject;
import components.HeaderComponent;
import configurations.db.QueryExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.GeneratorUtils;
import utils.Utils;

public class RegisterPage extends BasePage {

    @FindBy(css = ".page-title h1")
    private WebElement header;

    @FindBy(css = "#gender-male")
    private WebElement genderMaleRadioButton;

    @FindBy(css = "#gender-female")
    private WebElement genderFemaleRadioButton;

    @FindBy(css = "#FirstName")
    private WebElement firstNameInput;

    @FindBy(css = "#LastName")
    private WebElement lastNameInput;

    @FindBy(css = "#Email")
    private WebElement emailInput;

    @FindBy(css = "#Password")
    private WebElement passwordInput;

    @FindBy(css = "#ConfirmPassword")
    private WebElement confirmPasswordInput;

    @FindBy(css = "#register-button")
    private WebElement registerButton;

    @FindBy(css = "[value='Continue']")
    private WebElement continueButton;

    @FindBy(css = ".page.registration-result-page .result")
    private WebElement registrationResultMessage;

    @FindBy(css = ".validation-summary-errors")
    private WebElement emailRegistrationErrorMessage;

    @FindBy(css = ".field-validation-error")
    private WebElement passwordValidationErrorMessage;

    @FindBy(css = "[data-valmsg-for='FirstName']")
    private WebElement firstNameValidationErrorMessage;

    @FindBy(css = "[data-valmsg-for='LastName']")
    private WebElement lastNameValidationErrorMessage;

    @FindBy(css = "[data-valmsg-for='Email']")
    private WebElement emailValidationErrorMessage;

    @FindBy(css = "[data-valmsg-for='Password']")
    private WebElement emptyPasswordValidationErrorMessage;

    @FindBy(css = "[data-valmsg-for='ConfirmPassword']")
    private WebElement emptyConfirmPasswordValidationErrorMessage;

    @Inject
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public boolean isOnRegisterPage() {
        return Utils.isPageHeaderCorrect(header, "Register");
    }

    public boolean clickOnRegisterButton() {
        return click(registerButton);
    }

    public boolean isGenderMaleRadioButtonSelected() {
        return genderMaleRadioButton.isSelected();
    }

    public boolean isGenderFemaleRadioButtonSelected() {
        return genderFemaleRadioButton.isSelected();
    }

    public boolean fillFirstName(String firstName) {
        return fillText(firstNameInput, firstName);
    }

    public boolean fillLastName(String lastName) {
        return fillText(lastNameInput, lastName);
    }

    public boolean fillEmail(String email) {
        return fillText(emailInput, email);
    }

    public boolean fillPassword(String password) {
        return fillText(passwordInput, password);
    }

    public boolean fillConfirmPassword(String confirmPassword) {
        return fillText(confirmPasswordInput, confirmPassword);
    }

    public boolean selectGender(String gender) {
        if (gender.equalsIgnoreCase("male")) {
            return click(genderMaleRadioButton);
        } else {
            return click(genderFemaleRadioButton);
        }
    }

    public boolean clickOnContinueButton() {
        return click(continueButton);
    }

    public String getSuccessMessage() {
        return getText(registrationResultMessage);
    }

    public String getEmailRegistrationErrorMessage() {
        return getText(emailRegistrationErrorMessage);
    }

    public String getPasswordValidationErrorMessage() {
        return getText(passwordValidationErrorMessage);
    }

    public String getFirstNameValidationErrorMessage() {
        return getText(firstNameValidationErrorMessage);
    }

    public String getLastNameValidationErrorMessage() {
        return getText(lastNameValidationErrorMessage);
    }

    public String getEmailValidationErrorMessage() {
        return getText(emailValidationErrorMessage);
    }

    public String getEmptyPasswordValidationErrorMessage() {
        return getText(emptyPasswordValidationErrorMessage);
    }

    public String getEmptyConfirmPasswordValidationErrorMessage() {
        return getText(emptyConfirmPasswordValidationErrorMessage);
    }

    public boolean registerNewUser(int numberOfUsersToAdd) {
        HeaderComponent hea = new HeaderComponent(driver);

        try {
            for (int i = 0; i < numberOfUsersToAdd; i++) {
                String email = GeneratorUtils.generateEmail();
                String password = GeneratorUtils.generatePassword();
                String firstName = GeneratorUtils.generateFirstName();
                String lastName = GeneratorUtils.generateLastName();

                hea.clickOnRegisterLink();
                fillFirstName(firstName);
                fillLastName(lastName);
                fillEmail(email);
                fillPassword(password);
                fillConfirmPassword(password);
                clickOnRegisterButton();
                clickOnContinueButton();
                QueryExecutor.executeUpdate(
                        "insert_into_users_table",
                        email, password, firstName, lastName, 1);
                hea.clickOnLogoutLink();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}




