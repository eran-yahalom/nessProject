package pages;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WelcomePage extends BasePage {

    @FindBy(css = ".ico-register")
    private WebElement registerLink;

    @FindBy(css = ".ico-login")
    private WebElement loginLink;

    @FindBy(css = "#Email")
    private WebElement emailInput;

    @FindBy(css = "#Password")
    private WebElement passwordInput;

    @FindBy(css = "#RememberMe")
    private WebElement rememberMeCheckbox;

    @FindBy(css = ".validation-summary-errors")
    private WebElement loginErrorMessage;

    @FindBy(css = ".forgot-password a")
    private WebElement forgotPasswordLink;

    @FindBy(css = "[value='Log in']")
    private WebElement loginButton;

    @FindBy(css = ".validation-summary-errors")
    private WebElement loginValidationErrorMessage;

    @FindBy(css = ".topic-html-content-header")
    private WebElement welcomeMessage;

    @Inject
    public WelcomePage(WebDriver driver) {
        super(driver);
    }

    public boolean clickOnRegisterLink() {
        return click(registerLink);
    }

    public boolean clickOnLoginLink() {
        return click(loginLink);
    }

    public boolean fillEmail(String email) {
        return fillText(emailInput, email);
    }

    public boolean fillPassword(String password) {
        return fillText(passwordInput, password);
    }

    public boolean checkRememberMe() {
        try {
            if (!rememberMeCheckbox.isSelected()) {
                return click(rememberMeCheckbox);
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean clickOnLoginButton() {
        return click(loginButton);
    }

    public String getInvalidLoginErrorMessage() {
        return getText(loginValidationErrorMessage);
    }

    public boolean isCorrectErrorMessageDisplayed(String expectedMessage) {
        String actualMessage = getInvalidLoginErrorMessage();
        return expectedMessage.equalsIgnoreCase(actualMessage);
    }

    public boolean isWelcomeMessageDisplayed() {
        return isDisplayed(welcomeMessage);
    }

    public boolean isRegisterLinkDisplayed() {
        return isDisplayed(registerLink);
    }

    public boolean isLoginLinkDisplayed() {
        return isDisplayed(loginLink);
    }
}
