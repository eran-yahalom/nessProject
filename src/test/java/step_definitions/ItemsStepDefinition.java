package step_definitions;

import com.google.inject.Inject;
import com.google.inject.Provider;
import components.CategoryItemsComponent;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.*;
import utils.DriverManager;
import utils.GeneratorUtils;

@ScenarioScoped
public class ItemsStepDefinition {

    private String email = GeneratorUtils.generateEmail();
    private String password = GeneratorUtils.generatePassword();

    private final Provider<CategoryItemsComponent> categoryItemsComponentProvider;

    @Inject
    public ItemsStepDefinition(Provider<CategoryItemsComponent> categoryItemsComponentProvider) {
        this.categoryItemsComponentProvider = categoryItemsComponentProvider;
    }


//    public ItemsStepDefinition() {
//        WebDriver driver = DriverManager.getDriver();
//        this.categoryItemsComponent = new CategoryItemsComponent(driver);
//    }

    @When("user clicks on sort by dropdown and selects {string}")
    public void userClicksOnSortByDropdownAndSelects(String sortOption) {
        Assert.assertTrue(categoryItemsComponentProvider.get().selectSortByOption(sortOption));
    }

    @Then("the books should be sorted by {string}")
    public void booksShouldBeSortedCorrectly(String sortOption) {
        Assert.assertTrue(categoryItemsComponentProvider.get().areBooksSortedCorrectly(sortOption));
    }

    @When("user clicks on display dropdown and selects {string}")
    public void userClicksOnDisplayDropdownAndSelects(String sortOption) {
        Assert.assertTrue(categoryItemsComponentProvider.get().selectDisplayOption(sortOption));
    }

    @Then("{string} books should be displayed")
    public void booksShouldBeDisplayedCorrectly(String displayOption) {
        Assert.assertTrue(
                categoryItemsComponentProvider.get().doesNumberOfDisplayedBooksMatchDisplayOption(displayOption),
                "Displayed books count does NOT match selected display option: " + displayOption
        );
    }

    @When("user clicks on view as dropdown and selects {string}")
    public void userClicksOnViewAsDropdownAndSelects(String viewOption) {
        Assert.assertTrue(categoryItemsComponentProvider.get().selectViewModeOption(viewOption));
    }

    @Then("the books should be displayed in {string} view")
    public void booksShouldBeDisplayedInView(String viewOption) {
        Assert.assertTrue(categoryItemsComponentProvider.get().isViewDisplayed(viewOption));
    }

    @And("sort by dropdown should be set to {string}")
    public void sortByDropdownShouldBeSetTo(String expectedOption) {
        Assert.assertTrue(categoryItemsComponentProvider.get().areDropDownsSetToDefault(expectedOption));
    }

    @And("display dropdown should be set to {string}")
    public void displayDropdownShouldBeSetTo(String expectedOption) {
        Assert.assertTrue(categoryItemsComponentProvider.get().areDropDownsSetToDefault(expectedOption));
    }

    @And("view as dropdown should be set to {string}")
    public void viewAsDropdownShouldBeSetTo(String expectedOption) {
        Assert.assertTrue(categoryItemsComponentProvider.get().areDropDownsSetToDefault(expectedOption));
    }

    @And("user sets price range filter to {string}")
    public void userSetsPriceRangeFilterTo(String priceRangeFilter) {
        Assert.assertTrue(categoryItemsComponentProvider.get().selectPriceFilter(priceRangeFilter));
    }

    @Then("only books within the price range of {string} should be displayed")
    public void onlyBooksWithinPriceRangeShouldBeDisplayed(String priceRangeFilter) {
        Assert.assertTrue(categoryItemsComponentProvider.get().areDisplayedBooksWithinPriceRange(priceRangeFilter));
    }

    @And("number of displayed filter by price elements is correct")
    public void numberOfDisplayedFilterByPriceElementsIsCorrect() {
        Assert.assertTrue(categoryItemsComponentProvider.get().isNumberOfFilterByPriceElementsAfterFilterCorrect());
        Assert.assertTrue(categoryItemsComponentProvider.get().isRemovePriceRangeFilterButtonDisplayed());
    }

    @And("user clicks on remove price range filter button")
    public void userClicksOnRemovePriceRangeFilterButton() {
        Assert.assertTrue(categoryItemsComponentProvider.get().clickRemovePriceRangeFilterButton());
    }

    @Then("all price range filters should be displayed again")
    public void allPriceRangeFiltersShouldBeDisplayedAgain() {
        Assert.assertTrue(categoryItemsComponentProvider.get().isNumberOfFilterByPriceElementsCorrect());
    }
}
