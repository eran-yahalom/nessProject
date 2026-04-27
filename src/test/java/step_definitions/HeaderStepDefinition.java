package step_definitions;

import com.google.inject.Inject;
import com.google.inject.Provider;
import components.CategoryItemsComponent;
import components.HeaderComponent;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.SearchPage;

@ScenarioScoped
public class HeaderStepDefinition {

    private final Provider<HeaderComponent> headerComponentProvider;
    private final Provider<CategoryItemsComponent> categoryItemsComponentProvider;
    private final Provider<SearchPage> searchPageProvider;

    //    HeaderComponent headerComponent;
    // CategoryItemsComponent categoryItemsComponent;
    //  SearchPage searchPage;

    @Inject
    public HeaderStepDefinition(Provider<HeaderComponent> headerComponentProvider,
                                Provider<CategoryItemsComponent> categoryItemsComponentProvider,
                                Provider<SearchPage> searchPageProvider) {
        this.headerComponentProvider = headerComponentProvider;
        this.categoryItemsComponentProvider = categoryItemsComponentProvider;
        this.searchPageProvider = searchPageProvider;
    }

//    public HeaderStepDefinition() {
//        WebDriver driver = DriverManager.getDriver();
//        this.headerComponent = new HeaderComponent(driver);
//        this.categoryItemsComponent = new CategoryItemsComponent(driver);
//        this.searchPage = new SearchPage(driver);
//    }

    @When("user clicks on the search button")
    public void userClicksOnTheSearchButton() {
        Assert.assertTrue(headerComponentProvider.get().clickOnSearchButton());
    }

    @Then("a pop up window will be displayed with text {string}")
    public void aPopUpWindowWillBeDisplayedWithText(String textFromPopUpWindow) {
        String popUpText = headerComponentProvider.get().getAlertPopUpText();
        Assert.assertEquals(popUpText, textFromPopUpWindow);
    }

    @And("user closes the alert window")
    public void userClosesTheAlertWindow() {
        Assert.assertTrue(headerComponentProvider.get().closeAlertPopUp());
    }

    @And("user enters {string} in the search field")
    public void userEntersInTheSearchField(String searchTerm) {
        Assert.assertTrue(headerComponentProvider.get().fillSearchInput(searchTerm));
    }

    @And("search results should contain {string}")
    public void searchResultsShouldContain(String expectedSearchTerm) {
        Assert.assertTrue(searchPageProvider.get().doesAllSearchResultsContainSearchText(expectedSearchTerm));
    }

    @And("search results should be displayed")
    public void searchResultsShouldBeDisplayed() {
        Assert.assertTrue(searchPageProvider.get().getNumberOfSearchResults() > 0);
    }
}
