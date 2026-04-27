package pages;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Utils;

import java.util.List;

public class GiftCardsPage extends BasePage {

    @FindBy(css = ".product-item")
    private List<WebElement> giftCardItems;

    @FindBy(css = ".product-item .product-title a")
    private List<WebElement> giftCardTitles;

    @FindBy(css = ".product-item .buttons input")
    private List<WebElement> giftCardAddToCartButtons;

    @FindBy(css = ".product-item .price.actual-price")
    private List<WebElement> giftCardPrices;

    @Inject
    public GiftCardsPage(WebDriver driver) {
        super(driver);
    }

    public boolean addGiftCardToCart(String giftCardName) {
        wait.until(ExpectedConditions.elementToBeClickable(giftCardTitles.get(giftCardTitles.size() - 1)));
        return Utils.addProductToCart(driver, giftCardTitles, giftCardAddToCartButtons, giftCardName);
    }
}