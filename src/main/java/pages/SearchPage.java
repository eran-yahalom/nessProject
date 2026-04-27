package pages;

import com.google.inject.Inject;
import components.CategoryItemsComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.Utils;

public class SearchPage extends BasePage {

    CategoryItemsComponent categoryItems = new CategoryItemsComponent(driver);
    @FindBy(css = ".basic-search div label:nth-child(1)")
    public WebElement searchKeyWordHeader;

    @FindBy(css = "sic-search div label:nth-child(2)")
    public WebElement advanceSearchHeader;

    @FindBy(css = "[id='Q']")
    public WebElement advanceSearchField;

    @FindBy(css = "[id='As']")
    public WebElement advanceSearchCheckBox;

    @FindBy(css = ".button-1.search-button")
    public WebElement searchButton;

    @FindBy(css = ".search-results .result")
    public WebElement noSearchResultsText;

    @FindBy(css = "#Pt")
    public WebElement priceRangeToInput;

    @FindBy(css = "[value='Search store']")
    public WebElement searchInputField;

    @FindBy(css = "[class$='search-box-button']")
    public WebElement searchStoreSearchButton;

    @Inject
    public SearchPage(WebDriver driver) {
        super(driver);
    }


    public boolean doesAllSearchResultsContainSearchText(String textToSearch) {
        return Utils.isSearchedItemInCategoryItems(driver, categoryItems.getListOfWebElementTitles(), textToSearch);
    }

    public int getNumberOfSearchResults() {
        return categoryItems.getNumberOfProducts();
    }

    public boolean clickOnAdvanceSearchCheckBox() {
        try {
            waitForElementToBeVisible(advanceSearchCheckBox);
            advanceSearchCheckBox.click();
            return true;
        } catch (Exception e) {
            System.err.println("Failed to click on advance search checkbox: " + e.getMessage());
            return false;
        }
    }

    public boolean clickOnSearchButton() {
        try {
            waitForElementToBeClickable(searchButton);
            searchButton.click();
            return true;
        } catch (Exception e) {
            System.err.println("Failed to click on search button: " + e.getMessage());
            return false;
        }
    }

    public void enterPriceRangeTo(String price) {
        waitForElementToBeVisible(priceRangeToInput);
        priceRangeToInput.clear();
        priceRangeToInput.sendKeys(price);
    }

    public boolean fillSearchInputField(String searchText) {
        try {
            waitForElementToBeVisible(searchInputField);
            searchInputField.clear();
            searchInputField.sendKeys(searchText);
            click(searchStoreSearchButton);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to fill search input field: " + e.getMessage());
            return false;
        }
    }

    public boolean isNoSearchResultsTextDisplayed() {
        try {
            waitForElementToBeVisible(noSearchResultsText);
            return noSearchResultsText.isDisplayed();
        } catch (Exception e) {
            System.err.println("Failed to verify no search results text: " + e.getMessage());
            return false;
        }
    }
}
