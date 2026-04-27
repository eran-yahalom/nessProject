package components;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

public class ItemComponent {

    private WebDriver driver;
    private WebElement root;

    @FindBy(css = ".product-title a")
    private WebElement title;

    @FindBy(css = ".price.actual-price")
    private WebElement actualPrice;

    @FindBy(css = ".price.old-price")
    private WebElement oldPrice;

    @FindBy(css = ".rating")
    private WebElement rating;

    @FindBy(css = "input[value='Add to cart']")
    private WebElement addToCartButton;

    @FindBy(css = "input[value='Add to wishlist']")
    private WebElement addToWishlistButton;

    @FindBy(css = "input[value='Add to compare list']")
    private WebElement addToCompareButton;

    @Inject
    public ItemComponent(WebDriver driver, WebElement root) {
        this.driver = driver;
        this.root = root;

        PageFactory.initElements(
                new DefaultElementLocatorFactory(root),
                this
        );
    }

    public String getTitle() {
        return title.getText();
    }

    public String getPrice() {
        return actualPrice.getText();
    }

    public boolean hasDiscount() {
        return oldPrice.isDisplayed();
    }

    public void clickAddToCart() {
        addToCartButton.click();
    }

    public void clickAddToWishlist() {
        if (addToWishlistButton != null) {
            addToWishlistButton.click();
        }
    }

    public void clickAddToCompare() {
        if (addToCompareButton != null) {
            addToCompareButton.click();
        }
    }

    public void openDetails() {
        title.click();
    }
}