package components;

import com.google.inject.Inject;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
public class CategoryItemsComponent extends BaseComponent {

    @FindBy(css = "#products-orderby")
    private WebElement sortByDropdown;

    @FindBy(css = "#products-pagesize")
    private WebElement displayDropdown;

    @FindBy(css = "#products-viewmode")
    private WebElement viewModeDropdown;

    @FindBy(css = ".price-range-selector li:nth-child(1) a")
    private WebElement priceFilterUnder25;

    @FindBy(css = ".price-range-selector li:nth-child(2) a")
    private WebElement priceFilter25To50;

    @FindBy(css = ".price-range-selector li:nth-child(3) a")
    private WebElement priceFilterOver50;

    @FindBy(css = ".product-title a")
    private List<WebElement> productTitle;

    @FindBy(css = ".description")
    private List<WebElement> productDescription;

    @FindBy(css = ".price.actual-price")
    private List<WebElement> productActualPrice;

    @FindBy(css = ".price.old-price")
    private List<WebElement> productOldPrice;

    @FindBy(css = ".rating")
    private List<WebElement> productRating;

    @FindBy(css = ".product-grid .product-item")
    private WebElement gridElement;

    @FindBy(css = ".product-list .product-item")
    private WebElement listElement;

    @FindBy(css = ".price-range-selector li")
    private List<WebElement> priceRangeOptions;

    @FindBy(css = ".filter-content div")
    private List<WebElement> filterContentOptions;

    @FindBy(css = ".remove-price-range-filter")
    private WebElement removePriceRangeFilterButton;

    @FindBy(css = ".product-item")
    private List<WebElement> items;

    @FindBy(css = ".product-title>a")
    private List<WebElement> itemsTitlesLinks;

    @FindBy(css = ".next-page")
    private WebElement nextPageButton;

    @Inject
    public CategoryItemsComponent(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public List<String> searchItemsByNameUnderPrice(String query, double maxPrice, int limit) {
        List<String> results = new ArrayList<>();

        By itemsLocator = By.cssSelector(".product-title>a");

        while (results.size() < limit) {
            List<String> currentPathLinks = getAttributesSafe(itemsLocator, "href");

            for (String url : currentPathLinks) {
                if (results.size() < limit && !results.contains(url)) {
                    results.add(url);
                }
            }

            if (results.size() < limit) {
                try {
                    if (isDisplayed(nextPageButton) && isEnabled(nextPageButton)) {
                        WebElement firstItemBeforeClick = driver.findElement(itemsLocator);
                        click(nextPageButton);

                        wait.until(ExpectedConditions.stalenessOf(firstItemBeforeClick));
                    } else {
                        break;
                    }
                } catch (NoSuchElementException | IndexOutOfBoundsException e) {
                    break;
                }
            }
        }

        return results;
    }
}