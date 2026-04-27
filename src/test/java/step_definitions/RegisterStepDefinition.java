package step_definitions;

import com.google.inject.Inject;
import com.google.inject.Provider;
import components.HeaderComponent;
import components.TopMenuComponent;
import configurations.EnvConfig;
import configurations.EnvManager;
import configurations.db.QueryExecutor;
import context.ScenarioState;
import context.StateKeys;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.*;
import services.RegistrationService;
import utils.GeneratorUtils;
import utils.ScenarioContext;
import utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@ScenarioScoped
public class RegisterStepDefinition {
    private final Provider<WelcomePage> welcomePageProvider;
    private final Provider<RegisterPage> registerPageProvider;
    private final Provider<LoggedInPage> loggedInPageProvider;
    private final Provider<ShoppingCartPage> shoppingCartPageProvider;
    private final Provider<HeaderComponent> headerComponentProvider;
    private final Provider<ItemDetailsPage> itemDetailsPageProvider;
    private final Provider<TopMenuComponent> topMenuComponentProvider;
    private final Provider<ElectronicsPage> electronicsPageProvider;
    private final Provider<CellPhonesPage> cellPhonesPageProvider;
    private final Provider<RegistrationService> registrationServiceProvider;

    private String email = GeneratorUtils.generateEmail();
    private String password = GeneratorUtils.generatePassword();

    @Inject
    public RegisterStepDefinition(
            Provider<WelcomePage> welcomePageProvider,
            Provider<RegisterPage> registerPageProvider,
            Provider<LoggedInPage> loggedInPageProvider,
            Provider<ShoppingCartPage> shoppingCartPageProvider,
            Provider<HeaderComponent> headerComponentProvider,
            Provider<ItemDetailsPage> itemDetailsPageProvider,
            Provider<TopMenuComponent> topMenuComponentProvider,
            Provider<ElectronicsPage> electronicsPageProvider,
            Provider<CellPhonesPage> cellPhonesPageProvider,
            RegistrationService registrationService, Provider<RegistrationService> registrationServiceProvider
    ) {
        this.welcomePageProvider = welcomePageProvider;
        this.registerPageProvider = registerPageProvider;
        this.loggedInPageProvider = loggedInPageProvider;
        this.shoppingCartPageProvider = shoppingCartPageProvider;
        this.headerComponentProvider = headerComponentProvider;
        this.itemDetailsPageProvider = itemDetailsPageProvider;
        this.topMenuComponentProvider = topMenuComponentProvider;
        this.electronicsPageProvider = electronicsPageProvider;
        this.cellPhonesPageProvider = cellPhonesPageProvider;
        this.registrationServiceProvider = registrationServiceProvider;
    }


    @When("user clicks on register link")
    public void clickOnRegisterLink() {
        Assert.assertTrue(headerComponentProvider.get().clickOnRegisterLink());
    }

    @When("user clicks on log in link")
    public void clickOnLogInLink() {
        Assert.assertTrue(headerComponentProvider.get().clickOnLoginLink());
    }

    @And("user fills in email and password")
    public void userFillsEmailAndPassword() {
        List<Map<String, Object>> userDetails = QueryExecutor.executeQueryAsTable("get_random_customer_user_and_password", 1);

        Assert.assertNotNull(userDetails, "DB query is empty");

        String userEmail = userDetails.getFirst().get("email").toString();
        String userPassword = userDetails.getFirst().get("password").toString();

        ScenarioState.save(StateKeys.USER_EMAIL, userEmail);
        ScenarioState.save(StateKeys.USER_PASSWORD, userPassword);

        WelcomePage welcome = welcomePageProvider.get();

        Assert.assertTrue(welcome.fillEmail(userEmail));
        Assert.assertTrue(welcome.fillPassword(userPassword));
    }

    @And("user fills in email and password with {string} and {string}")
    public void userFillsEmailAndPasswordWith(String email, String password) {
        WelcomePage welcome = welcomePageProvider.get();
        Assert.assertTrue(welcome.fillEmail(email));
        Assert.assertTrue(welcome.fillPassword(password));
    }

    @And("user clicks on log in button")
    public void userClicksOnLogInButton() {
        Assert.assertTrue(welcomePageProvider.get().clickOnLoginButton());
    }

    @When("user enters gender {string}")
    public void userEntersGender(String gender) {
        Assert.assertTrue(registerPageProvider.get().selectGender(gender));
    }

    @When("user enters first and last name")
    public void userEntersFirstNameAndLastName() {
        RegisterPage register = registerPageProvider.get();
        Assert.assertTrue(register.fillFirstName(GeneratorUtils.generateFirstName()));
        Assert.assertTrue(register.fillLastName(GeneratorUtils.generateLastName()));
    }

    @When("user enters email")
    public void userEntersEmail() {
        Assert.assertTrue(registerPageProvider.get().fillEmail(ScenarioState.get(StateKeys.USER_EMAIL, String.class)));
    }

    @And("user enters existing email")
    public void userEntersExistingEmail() {
        Assert.assertTrue(registerPageProvider.get().fillEmail(Utils.readProperty("userEmail")));
    }

    @And("user enters password and confirm password")
    public void userEntersPasswordAndConfirmPassword() {
        String password = ScenarioState.get(StateKeys.USER_PASSWORD, String.class);
        RegisterPage register = registerPageProvider.get();
        Assert.assertTrue(register.fillPassword(password));
        Assert.assertTrue(register.fillConfirmPassword(password));
    }

    @And("user enters non matching password and confirm password")
    public void userEntersNonMatchingPasswordAndConfirmPassword() {
        RegisterPage register = registerPageProvider.get();
        Assert.assertTrue(register.fillPassword(password));
        Assert.assertTrue(register.fillConfirmPassword(password + "A"));
    }

    @And("user clicks on register button")
    public void userClicksOnRegisterButton() {
        Assert.assertTrue(registerPageProvider.get().clickOnRegisterButton());
    }

    @And("a success message is displayed")
    public void userShouldSeeRegistrationSuccessMessage() {
        String actualMessage = registerPageProvider.get().getSuccessMessage();
        Assert.assertEquals(actualMessage, Utils.readProperty("registerSuccessMessage"), "Registration success message does not match expected.");
    }

    @And("user clicks on continue button")
    public void userClicksOnContinueButton() {
        Assert.assertTrue(registerPageProvider.get().clickOnContinueButton());
    }

    @And("user is logged in with email {string}")
    public void userIsLoggedIn(String email) {
        Assert.assertTrue(loggedInPageProvider.get().isUserLoggedIn(email));
    }

    @And("the user should be logged in")
    public void newUserIsLoggedInSuccessfully() {
        Assert.assertTrue(loggedInPageProvider.get().isUserLoggedIn(ScenarioState.get(StateKeys.USER_EMAIL, String.class)));
    }

    @And("the user is logged in successfully")
    public void existingUserIsLoggedInSuccessfully() {
        Assert.assertTrue(loggedInPageProvider.get().isUserLoggedIn(ScenarioState.get(StateKeys.USER_EMAIL, String.class)));
    }

    @Then("a error message should be displayed for existing email")
    public void userShouldSeeErrorMessageForExistingEmail() {
        String actualMessage = registerPageProvider.get().getEmailRegistrationErrorMessage();
        Assert.assertEquals(actualMessage, Utils.readProperty("emailAlreadyExistsText"));
    }

    @Then("a error message should be displayed for non matching passwords")
    public void userShouldSeeErrorMessageForNonMatchingPasswords() {
        String actualMessage = registerPageProvider.get().getPasswordValidationErrorMessage();
        Assert.assertEquals(actualMessage, Utils.readProperty("passwordsDontMatchErrorMessage"));
    }

    @Then("a error message should be displayed for all empty fields")
    public void userShouldSeeErrorMessageForAllEmptyFields() {
        RegisterPage registerPage = registerPageProvider.get();
        String emptyFirstname = registerPage.getFirstNameValidationErrorMessage();
        String emptyLastname = registerPage.getLastNameValidationErrorMessage();
        String emptyEmail = registerPage.getEmailValidationErrorMessage();
        String emptyPassword = registerPage.getEmptyPasswordValidationErrorMessage();
        String emptyConfirmPassword = registerPage.getEmptyConfirmPasswordValidationErrorMessage();
        Assert.assertEquals(
                emptyFirstname,
                Utils.readProperty("firstNameRequired"),
                "First name required message is incorrect!"
        );

        Assert.assertEquals(
                emptyLastname,
                Utils.readProperty("lastNameRequired"),
                "Last name required message is incorrect!"
        );

        Assert.assertEquals(
                emptyEmail,
                Utils.readProperty("emailRequired"),
                "Email required message is incorrect!"
        );

        Assert.assertEquals(
                emptyPassword,
                Utils.readProperty("passwordRequired"),
                "Password required message is incorrect!"
        );

        Assert.assertEquals(
                emptyConfirmPassword,
                Utils.readProperty("confirmPasswordRequired"),
                "Confirm password required message is incorrect!"
        );
    }

    @Then("error message {string} is displayed for invalid credentials")
    public void userShouldSeeErrorMessageForInvalidCredentials(String errorMessageFormat) {
        String actualMessage = welcomePageProvider.get().getInvalidLoginErrorMessage();
        Assert.assertEquals(actualMessage, Utils.readProperty(errorMessageFormat));
    }

    @And("the user navigates to the {string} page")
    public void userClicksOnFromTheTopMenu(String menuOption) {
        Assert.assertTrue(topMenuComponentProvider.get().clickOnTopMenuLink(menuOption));
    }

    @And("user adds {string} to the cart")
    public void userAddsToTheCart(String productName) {
        Assert.assertTrue(itemDetailsPageProvider.get().addProductToCart(productName));
    }

    @And("user clicks on add to cart button")
    public void clickOnAddToCartButton() {
        Assert.assertTrue(itemDetailsPageProvider.get().clickAddToCartButton());
    }

    @And("an error message should be displayed")
    public void userShouldSeeMessageProductNotAddedToCart() {
        ItemDetailsPage itemDetailsPage = itemDetailsPageProvider.get();
        Assert.assertTrue(itemDetailsPage.isValidTRecipientEmailMessagePresented());
        Assert.assertTrue(itemDetailsPage.isValidTRecipientNameMessagePresented());
    }

    @And("user fills in recipient name")
    public void userFillsInRecipientName() {
        Assert.assertTrue(itemDetailsPageProvider.get().fillRecipientNameFromSender());
    }

    @And("user fills in recipient email")
    public void userFillsInRecipientEmail() {
        Assert.assertTrue(itemDetailsPageProvider.get().fillRecipientEmailFromSender());
    }

    @And("a success message should be displayed")
    public void userShouldSeeMessageProductAddedToCart() {
        Assert.assertTrue(itemDetailsPageProvider.get().addToCartSuccessMessagePresented());
    }

    @And("the user clicks on cart link in the notification")
    public void userClicksOnCartLinkInTheNotification() {
        Assert.assertTrue(itemDetailsPageProvider.get().clickOnCartLinkInNotification());
    }

    @Then("the user should be redirected to the shopping cart page")
    public void userShouldBeOnShoppingCartPage() {
        Assert.assertTrue(shoppingCartPageProvider.get().isHeaderDisplayedCorrectly());
    }

    @Then("the item was added to the cart successfully")
    public void itemWasAddedToTheCartSuccessfully() {
        int numberOfItemsBeforeAdd = ScenarioContext.get("quantityOfItemsInCart", Integer.class);
        int numberOfItemsAfterAdd = shoppingCartPageProvider.get().getQuantityOfItemInCart();
        Assert.assertEquals(numberOfItemsAfterAdd, numberOfItemsBeforeAdd + 1, "Expected number of items in cart to be increased by 1 after adding a product, but it was not.");
    }

    @And("user clicks on shopping cart link in the top menu")
    public void userClicksOnShoppingCartLinkInTheTopMenu() {
        Assert.assertTrue(headerComponentProvider.get().clickOnShoppingCartLink());
    }

    @And("user counts the number of items in the cart")
    public void userCountsTheNumberOfItemsInTheCart() {
        int numberOfItemsInCart = shoppingCartPageProvider.get().countNumberOfItemsInCart();
        ScenarioContext.save("numberOfItemsInCart", numberOfItemsInCart);
        Assert.assertTrue(numberOfItemsInCart > 0, "Expected at least one item in the cart, but found: " + numberOfItemsInCart);
    }

    @And("user clicks on demo web shop link in the top menu")
    public void userClicksOnDemoWebShopLinkInTheTopMenu() {
        Assert.assertTrue(topMenuComponentProvider.get().clickOnLogoLink());
    }

    @And("user counts the quantity of items in the cart")
    public void userCountsTheQuantityOfItemsInTheCart() {
        int quantityOfItemsInCart = shoppingCartPageProvider.get().getQuantityOfItemInCart();
        ScenarioContext.save("quantityOfItemsInCart", quantityOfItemsInCart);
        Assert.assertTrue(quantityOfItemsInCart >= 0, "Expected at least one item in the cart, but found quantity: " + quantityOfItemsInCart);
    }

    @And("user deletes all the items from the cart")
    public void userDeletesItemFromTheCart() {
        Assert.assertTrue(shoppingCartPageProvider.get().deleteItemFromCart());
    }

    @And("user clicks on {string} from top menu and selects {string} from the submenu")
    public void userSelectsFromTheTopMenu(String menuOption, String subMenuOption) {
        Assert.assertTrue(topMenuComponentProvider.get().clickOnSubMenuLink(menuOption, subMenuOption));
    }

    @And("user selects {string} from categories page")
    public void userSelectsItemFromPage(String categoryOption) {
        Assert.assertTrue(electronicsPageProvider.get().selectItem(categoryOption));
    }

    @And("user selects {string}")
    public void userSelectsItemFromDropdownPage(String itemName) {
        Assert.assertTrue(cellPhonesPageProvider.get().selectItem(itemName));
    }

    @And("the cart total price should be calculated correctly")
    public void userShouldSeeCorrectTotalPriceForItemsInCart() {
        Assert.assertTrue(shoppingCartPageProvider.get().isTotalPriceCalculatedCorrectly());
    }

    @And("the cart header number of items match the cart page number of items")
    public void numberOfItemsInCartHeaderShouldMatchNumberOfItemsInCartPage() {
        int numberOfItemsInCart = shoppingCartPageProvider.get().countNumberOfItemsInCart();
        int numberOfItemsInHeader = headerComponentProvider.get().getShoppingCartItemCount();
        Assert.assertEquals(numberOfItemsInHeader, numberOfItemsInCart, "Expected number of items in cart header to match the actual number of items in the cart, but it did not.");
    }

    @And("random user is logged in successfully")
    public void theUserIsLoggedIn() {
        List<String> userCreds = registrationServiceProvider.get().getRandomUserLoginCredentials();

        ScenarioState.save(StateKeys.DB_EMAIL, userCreds.getFirst());
        ScenarioState.save(StateKeys.DB_PASSWORD, userCreds.getLast());

        WelcomePage welcomePage = welcomePageProvider.get();
        Assert.assertTrue(headerComponentProvider.get().clickOnLoginLink());
        Assert.assertTrue(welcomePage.fillEmail(userCreds.getFirst()));
        Assert.assertTrue(welcomePage.fillPassword(userCreds.getLast()));
        Assert.assertTrue(welcomePage.clickOnLoginButton());
    }

    @And("the shopping cart is empty")
    public void theShoppingCartIsEmpty() {
        Assert.assertTrue(headerComponentProvider.get().clickOnShoppingCartLink());
        Assert.assertTrue(shoppingCartPageProvider.get().deleteItemFromCart());
    }

    @And("user clicks on log out link")
    public void userClicksOnLogOutLink() {
        Assert.assertTrue(headerComponentProvider.get().clickOnLogoutLink());
    }

    @Then("the user is logged out successfully")
    public void theUserIsLoggedOutSuccessfully() {
        WelcomePage welcomePage = welcomePageProvider.get();
        Assert.assertTrue(welcomePage.isRegisterLinkDisplayed());
        Assert.assertTrue(welcomePage.isLoginLinkDisplayed());
    }

    @Then("error message {string} should be displayed")
    public void errorMessageShouldBeDisplayed(String messageKey) {
        String actualMessage = headerComponentProvider.get().getNoResultsTextMessage();
        String expectedMessage = Utils.readProperty(messageKey);
        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @When("new user is updated in DB")
    public void newUserIsUpdatedInDB() {
        List<String> userDetails = registrationServiceProvider.get().registerRandomUserInDB();
        ScenarioState.save(StateKeys.USER_EMAIL, userDetails.get(0));
        ScenarioState.save(StateKeys.USER_PASSWORD, userDetails.get(1));
    }

    @When("user is logged in with email {string} and password {string}")
    public void userIsLoggedInWithEmailAndPassword(String email, String password) {
        WelcomePage welcomePage = welcomePageProvider.get();
        Assert.assertTrue(headerComponentProvider.get().clickOnLoginLink());
        Assert.assertTrue(welcomePage.fillEmail(email));
        Assert.assertTrue(welcomePage.fillPassword(password));
        Assert.assertTrue(welcomePage.clickOnLoginButton());
    }

    @And("cart items for user {string} match the user cart in DB")
    public void cartItemsMatchTheUserCartInDB(String userEmail) {
        List<Map<String, Object>> queryResult = QueryExecutor.executeQueryAsTable(
                "get_cart_item_qty_and_total_price", userEmail);

        double totalPriceFromDB = (double) queryResult.getFirst().get("totalPrice");
        int qtyFromDB = (int) queryResult.getFirst().get("itemsQty");

        int itemQty = shoppingCartPageProvider.get().getQuantityOfItemInCart();
        double totalPriceFromUI = shoppingCartPageProvider.get().sumPriceOfProductsInCart();

        Assert.assertEquals(itemQty, qtyFromDB, "Expected item quantity in cart to match DB value, but it did not.");
        Assert.assertEquals(totalPriceFromUI, totalPriceFromDB, "Expected total price in cart to match DB value, but it did not.");
    }

    @And("cart items for user match the user cart in DB")
    public void cartItemsMatchTheRandomUserCartInDB() {
        record CartSummary(double totalPrice, int itemsQty) {
        }
        String email = ScenarioState.get(StateKeys.DB_EMAIL, String.class);

        List<Map<String, Object>> queryResult = QueryExecutor.executeQueryAsTable(
                "get_cart_item_qty_and_total_price", email);

        Assert.assertFalse(queryResult.get(0).values().stream().allMatch(Objects::isNull), "DB query returned null for cart summary");

        Map<String, Object> row = queryResult.getFirst();

        CartSummary dbCart = new CartSummary(
                ((Number) row.getOrDefault("totalPrice", 0.0)).doubleValue(),
                ((Number) row.getOrDefault("itemsQty", 0)).intValue()
        );

        int uiQty = shoppingCartPageProvider.get().getQuantityOfItemInCart();
        double uiPrice = shoppingCartPageProvider.get().sumPriceOfProductsInCart();

        Assert.assertEquals(uiQty, dbCart.itemsQty(), "Quantity mismatch!");
        Assert.assertEquals(uiPrice, dbCart.totalPrice(), 0.01, "Price mismatch!");
    }

    @And("user updated cart item quantity to {int}")
    public void userUpdatedCartItemQuantityTo(int quantity) {
        Assert.assertTrue(shoppingCartPageProvider.get().updateQuantityOfItemInCart(quantity));
    }

    @And("user registers {int} new users in the database")
    public void userRegistersNewUsersInTheDatabase(int numberOfUsersStr) {
        registerPageProvider.get().registerNewUser(numberOfUsersStr);
    }

    @And("user generates random user and password")
    public void generateRandomUserAndPassword() {
        List<String> userEmail = GeneratorUtils.generateRandomUserAndPassword();
        ScenarioState.save(StateKeys.USER_EMAIL, userEmail.getFirst());
        ScenarioState.save(StateKeys.USER_PASSWORD, userEmail.getLast());
    }
}
