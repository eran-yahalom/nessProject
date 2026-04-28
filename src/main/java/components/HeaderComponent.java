package components;

import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Log4j2
public class HeaderComponent extends BaseComponent {

  //  private WebDriver driver;

    @FindBy(css = ".ico-register")
    private WebElement registerLink;

    @FindBy(css = ".ico-login")
    private WebElement loginLink;

    @FindBy(css = ".ico-logout")
    private WebElement logoutLink;

    @FindBy(css = "#topcartlink .ico-cart")
    private WebElement shoppingCartLink;

    @FindBy(css = "#topcartlink .ico-cart span:nth-child(2)")
    private WebElement shoppingCartItemCount;

    @FindBy(css = ".header-links li .ico-wishlist")
    private WebElement wishlistLink;

    @FindBy(css = ".header-links li .ico-wishlist span:nth-child(2)")
    private WebElement wishlistItemCount;

    @FindBy(css = "[value='Search store']")
    private WebElement searchInput;

    @FindBy(css = "[value='Search']")
    private WebElement searchButton;

    @FindBy(css = ".header-logo")
    private WebElement homeLogo;

    @FindBy(css = ".search-results .result")
    private WebElement noItemsInResultsMessage;

    @Inject
    public HeaderComponent(WebDriver driver) {
        super(driver);
     //   this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean clickOnLoginLink() {
        return click(loginLink);
    }

    public boolean clickOnShoppingCartLink() {
        return click(shoppingCartLink);
    }

    public boolean fillSearchInput(String searchTerm) {
        return fillText(searchInput, searchTerm);
    }

    public boolean clickOnSearchButton() {
        return click(searchButton);
    }
}
