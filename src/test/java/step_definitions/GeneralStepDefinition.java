package step_definitions;

import com.google.inject.Inject;
import com.google.inject.Provider;
import components.BreadcrumbComponent;
import components.CategoryItemsComponent;
import components.HeaderComponent;
import configurations.EnvManager;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.testdata.SearchTestData;
import org.testng.Assert;
import pages.CartItemPage;
import pages.ItemDetailsPage;
import pages.ShoppingCartPage;
import pages.WelcomePage;
import utils.JsonDataLoaderUtils;
import utils.ScenarioContext;

import java.util.List;

@ScenarioScoped
public class GeneralStepDefinition {

    private final Provider<WelcomePage> welcomePageProvider;
    private final Provider<ItemDetailsPage> itemDetailsPageProvider;
    private final Provider<ShoppingCartPage> shoppingCartPageProvider;
    private final Provider<HeaderComponent> headerComponentProvider;
    private final Provider<CategoryItemsComponent> cartItemPageProvider;


    @Inject
    public GeneralStepDefinition(Provider<WelcomePage> welcomePageProvider, Provider<HeaderComponent> headerComponentProvider, Provider<CartItemPage> cartItemPageProvider, Provider<BreadcrumbComponent> breadcrumbComponentProvider, Provider<CategoryItemsComponent> cartItemPageProvider1, Provider<ItemDetailsPage> itemDetailsPageProvider, Provider<ShoppingCartPage> shoppingCartPageProvider) {
        this.welcomePageProvider = welcomePageProvider;
        this.headerComponentProvider = headerComponentProvider;
        this.cartItemPageProvider = cartItemPageProvider1;
        this.itemDetailsPageProvider = itemDetailsPageProvider;
        this.shoppingCartPageProvider = shoppingCartPageProvider;
    }

    @And("user is logged in successfully")
    public void userIsLoggedInSuccessfully() {
        WelcomePage welcomePage = welcomePageProvider.get();
        Assert.assertTrue(headerComponentProvider.get().clickOnLoginLink());
        Assert.assertTrue(welcomePage.fillEmail(EnvManager.get().getUsername()));
        Assert.assertTrue(welcomePage.fillPassword(EnvManager.get().getPassword()));
        Assert.assertTrue(welcomePage.clickOnLoginButton());
    }

    @And("user search item {string} with max price {double} and limit {int}")
    public void userSearchesItemWithMaxPriceAndLimitAndAddsItToCart(String itemName, double maxPrice, int limit) {
        List<String> items = cartItemPageProvider.get().searchItemsByNameUnderPrice(itemName, maxPrice, limit);

    }

    @When("user searches for items using data from {string} at index {int}")
    public void userSearchesWithModel(String jsonFile, int index) {

        List<SearchTestData> testScenarios = JsonDataLoaderUtils.getSearchData(jsonFile);
        SearchTestData scenario = testScenarios.get(index);
        Assert.assertNotNull(testScenarios, "Test scenarios should not be null");

        List<String> links = cartItemPageProvider.get().searchItemsByNameUnderPrice(
                scenario.getQuery(),
                scenario.getMaxPrice(),
                scenario.getLimit()
        );
        Assert.assertNotNull(links, "The search result list is null");
        Assert.assertTrue(links.size() <= scenario.getLimit(),
                "Search returned " + links.size() + " items, which exceeds the limit of "
                        + scenario.getLimit());

        ScenarioContext.save("links", links);
        ScenarioContext.save("linksSize", links.size());
        ScenarioContext.save("maxPrice", scenario.getMaxPrice());
        ScenarioContext.save("limit", scenario.getLimit());
    }

    @And("user adds all found items to the cart")
    public void userAddsAllFoundItemsToTheCart() {
        List<String> productsLinks = ScenarioContext.get("links", List.class);
        Assert.assertNotNull(productsLinks, "Test scenarios should not be null");

        Assert.assertTrue(itemDetailsPageProvider.get().addItemsToCart(productsLinks),
                "Failed to add items to cart");
    }

    @Then("user verifies that the cart total does not exceed the max price")
    public void userVerifiesThatTheCartTotalDoesNotExceedTheMaxPrice() {

        double maxPrice = ScenarioContext.get("maxPrice", Double.class);
        int limit = ScenarioContext.get("limit", Integer.class);
        boolean cartTotalValid = shoppingCartPageProvider.get().assertCartTotalNotExceeds(maxPrice, limit);
        int linksSize = ScenarioContext.get("linksSize", Integer.class);
        int numberOfItemsAfterAdd = shoppingCartPageProvider.get().getQuantityOfItemInCart();

        Assert.assertEquals(numberOfItemsAfterAdd, linksSize, "Number of items in cart does not match the number of added items");
        Assert.assertTrue(cartTotalValid, "Cart total exceeds the maximum price limit");


//        Assert.assertTrue(shoppingCartPageProvider.get().assertCartTotalNotExceeds(maxPrice, limit));
    }
}
