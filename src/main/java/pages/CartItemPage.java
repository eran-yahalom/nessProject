package pages;

import com.google.inject.Inject;
import components.BreadcrumbComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Utils;

public class CartItemPage extends BasePage {
    private BreadcrumbComponent breadcrumbs;

    @FindBy(css = ".add-to-cart-panel [value='Add to cart']")
    private WebElement addToCartButton;

    @FindBy(css = "#bar-notification p:nth-child(2)")
    private WebElement validRecipientNameErrorMessage;

    @FindBy(css = "#bar-notification p:nth-child(3)")
    private WebElement validRecipientEmailErrorMessage;

    @FindBy(css = ".recipient-name")
    private WebElement recipientNameInput;

    @FindBy(css = ".recipient-email")
    private WebElement recipientEmailInput;

    @FindBy(css = ".sender-name")
    private WebElement senderNameInput;

    @FindBy(css = ".sender-email")
    private WebElement senderEmailInput;

    @FindBy(css = "#bar-notification p a")
    private WebElement shoppingCartLinkInNotification;

    @FindBy(css = "#bar-notification p")
    private WebElement addToCartSuccessNotificationMessage;

    @FindBy(css = ".product-name h1")
    private WebElement productNameHeader;

    @FindBy(css = "[value='Add to wishlist']")
    private WebElement addToWishlistButton;

    @FindBy(css = "[value='Add to compare list']")
    private WebElement addToCompareListButton;

    @Inject
    public CartItemPage(WebDriver driver) {
        super(driver);
        breadcrumbs = new BreadcrumbComponent(driver);
    }

    public boolean clickAddToCartButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        return click(addToCartButton);
    }

    public boolean isValidTRecipientNameMessagePresented() {
        return Utils.isMessageDisplayedCorrectly(driver, validRecipientNameErrorMessage, "cartEnterValidRecipientName");
    }

    public boolean isValidTRecipientEmailMessagePresented() {
        return Utils.isMessageDisplayedCorrectly(driver, validRecipientEmailErrorMessage, "cartEnterValidRecipientEmail");
    }

    public boolean fillRecipientNameFromSender() {
        wait.until(ExpectedConditions.elementToBeClickable(senderNameInput));
        String senderName = senderNameInput.getAttribute("value");
        return fillText(recipientNameInput, senderName);
    }

    public boolean fillRecipientEmailFromSender() {
        String senderEmail = senderEmailInput.getAttribute("value");
        return fillText(recipientEmailInput, senderEmail);
    }

    public boolean addToCartSuccessMessagePresented() {
        return Utils.isMessageDisplayedCorrectly(driver, addToCartSuccessNotificationMessage, "addToCartSuccessNotificationMessage");
    }

    public boolean clickOnCartLinkInNotification() {
        wait.until(ExpectedConditions.elementToBeClickable(shoppingCartLinkInNotification));
        return click(shoppingCartLinkInNotification);
    }

    public String getProductNameHeader() {
        return getText(productNameHeader);
    }

    public boolean clickAddToWishlistButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addToWishlistButton));
        return click(addToWishlistButton);
    }

    public boolean addToCompareListButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCompareListButton));
        return click(addToCompareListButton);
    }

    public boolean doesBreadcrumbContainProductName(String expectedBreadcrumb) {
        String lastBreadCrumbText = breadcrumbs.getLastBreadcrumbStep();
        return expectedBreadcrumb.equalsIgnoreCase(lastBreadCrumbText);
    }
}
