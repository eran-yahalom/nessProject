package step_definitions;

import com.google.inject.Inject;
import com.google.inject.Provider;
import components.HeaderComponent;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.And;
import org.testng.Assert;
import pages.ShoppingCartPage;

@ScenarioScoped
public class RegisterStepDefinition {
    private final Provider<ShoppingCartPage> shoppingCartPageProvider;
    private final Provider<HeaderComponent> headerComponentProvider;

    @Inject
    public RegisterStepDefinition(

            Provider<ShoppingCartPage> shoppingCartPageProvider,
            Provider<HeaderComponent> headerComponentProvider)
    {
        this.shoppingCartPageProvider = shoppingCartPageProvider;
        this.headerComponentProvider = headerComponentProvider;
    }

    @And("the shopping cart is empty")
    public void theShoppingCartIsEmpty() {
        Assert.assertTrue(headerComponentProvider.get().clickOnShoppingCartLink());
        Assert.assertTrue(shoppingCartPageProvider.get().deleteItemFromCart());
    }
}
