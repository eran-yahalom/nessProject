package pages;

import com.google.inject.Inject;
import components.*;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Utils;

import java.time.Duration;
import java.util.List;
import java.util.Random;

// page that opens after we click on a product in the page (click on book x from bookPage and see book x details in this page)
@Log4j2
public class ItemDetailsPage extends BasePage {
    private CategoryItemsComponent categoryItems;

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

    @FindBy(css = ".product-name h1")
    private WebElement productName;

    @FindBy(css = "[value='Add to compare list']")
    private WebElement addToCompareButton;

    @FindBy(css = "[id^='product_attribute']")
    private List<WebElement> productAttributes;

    @Inject
    public ItemDetailsPage(WebDriver driver) {
        super(driver);
        categoryItems = new CategoryItemsComponent(driver);
    }

    public String getProductName() {
        return productName.getText();
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

    public boolean addProductToCart(String productName) {

        ItemComponent item = categoryItems.getItemByName(productName);
        item.clickAddToCart();
        return true;
    }

    public boolean clickOnRandomProductAttributeIfExist() {
        if (productAttributes == null || productAttributes.isEmpty()) {
            log.info("No product attributes found on this page - skipping selection (Normal behavior).");
            return true;
        }
        try {

            Random random = new Random();
            int randomIndex = random.nextInt(productAttributes.size());

            WebElement randomElement = productAttributes.get(randomIndex);
            log.info("Product attributes found. Clicking on random element at index: {}", randomIndex);

            return click(randomElement);
        } catch (Exception e) {
            log.error("Failed to click on product attribute even though it was found: {}", e.getMessage());
            return false;
        }
    }

    public boolean addItemsToCart(List<String> urls) {
        if (urls == null) {
            return false;
        }

        boolean allAdded = true;

        for (String url : urls) {
            try {
                driver.get(url);
                clickOnRandomProductAttributeIfExist();
                wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
                click(addToCartButton);

                Utils.takeScreenshot(driver, "Added_to_cart_" + System.currentTimeMillis());
            } catch (Exception e) {
                log.error("Error processing URL: " + url, e);
                allAdded = false;
            }
        }

        return allAdded;
    }
}