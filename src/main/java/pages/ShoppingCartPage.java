package pages;

import com.google.inject.Inject;
import components.HeaderComponent;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.Utils;

import java.util.List;

@Log4j2
public class ShoppingCartPage extends BasePage {

    @FindBy(css = ".page-title h1")
    private WebElement header;

    @FindBy(css = ".cart-item-row")
    private List<WebElement> cartItemRow;

    @FindBy(css = ".qty-input")
    private List<WebElement> quantityInputFields;

    @FindBy(css = "[name='removefromcart']")
    private List<WebElement> removeFromCartCheckboxes;

    @FindBy(css = "[name='updatecart']")
    private WebElement updateCartButton;

    @FindBy(css = ".product-subtotal")
    private List<WebElement> productTotalPrice;

    @FindBy(css = ".cart-total tr:nth-child(1) .cart-total-right")
    private WebElement subtotalPrice;

    @FindBy(css = ".cart-total tr:nth-child(2) .cart-total-right")
    private WebElement shippingCost;

    @FindBy(css = ".cart-total tr:nth-child(3) .cart-total-right")
    private WebElement taxCost;

    @FindBy(css = ".cart-total tr:nth-child(4) .cart-total-right")
    private WebElement totalPrice;

    @Inject
    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public int getQuantityOfItemInCart() {
        int qty = 0;
        try {
            for (int i = 0; i < cartItemRow.size(); i++) {
                int quantity = Integer.parseInt(quantityInputFields.get(i).getAttribute("value"));
                qty += quantity;
            }
            return qty;
        } catch (Exception e) {
            log.info("Quantity of items in cart: 0");
            return 0;
        }
    }

    public boolean deleteItemFromCart() {
        if (cartItemRow == null || cartItemRow.isEmpty()) {
            return true;
        }
        for (WebElement checkbox : removeFromCartCheckboxes) {
            if (!checkbox.isSelected()) {
                click(checkbox);
            }
        }
        return click(updateCartButton);
    }

    public boolean assertCartTotalNotExceeds(double budgetPerItem, int itemsCount) {
        double actualTotal = 0.0;
        HeaderComponent header = new HeaderComponent(driver);
        header.clickOnShoppingCartLink();
        log.info("Opened Shopping Cart to verify total price.");

        try {
            String totalPriceText = getText(totalPrice).replace(".00", "").trim();
            actualTotal = Double.parseDouble(totalPriceText);
        } catch (Exception e) {
            actualTotal = 0.0;
            log.info("Failed to retrieve total price from cart, defaulting to 0. Error: {}", e.getMessage());
        }
        double maxAllowedBudget = budgetPerItem * itemsCount;
        log.info("Actual Total: {}, Max Allowed Budget: {} ({} per item * {})",
                actualTotal, maxAllowedBudget, budgetPerItem, itemsCount);

        Utils.takeScreenshot(driver, "Cart_Validation_" + System.currentTimeMillis());

        if (actualTotal <= maxAllowedBudget) {
            log.info("Total price is within the expected budget.");
            return true;
        } else {
            log.warn("Total price exceeds the expected budget!");
            return false;
        }
    }
}