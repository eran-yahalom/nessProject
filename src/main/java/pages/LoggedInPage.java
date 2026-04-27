package pages;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoggedInPage extends BasePage {

    @FindBy(css = ".header-links .account")
    private WebElement myAccountLink;

    @Inject
    public LoggedInPage(WebDriver driver) {
        super(driver);

    }

    public boolean isMyAccountLinkDisplayed() {
        return myAccountLink.isDisplayed();
    }

    public String getMyAccountLinkText() {
        return myAccountLink.getText();
    }

    public boolean isUserLoggedIn(String email) {
        return email.equalsIgnoreCase(getMyAccountLinkText()) && !getMyAccountLinkText().isEmpty();
    }
}
