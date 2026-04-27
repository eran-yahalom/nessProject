package step_definitions;

import com.google.inject.Inject;
import com.google.inject.Provider;
import components.FooterComponent;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

@ScenarioScoped
public class FooterStepDefinition {

    private final Provider<FooterComponent> footerComponentProvider;

    @Inject
    public FooterStepDefinition(Provider<FooterComponent> footerComponentProvider) {
        this.footerComponentProvider = footerComponentProvider;
    }

    @When("user clicks on {string} link in the footer")
    public void userClicksOnLinkInTheFooter(String socialMedia) {
        Assert.assertTrue(footerComponentProvider.get().clickAndMoveToSelectedSocialMedia(socialMedia));
    }

    @Then("social media {string} page will be opened in a new tab")
    public void socialMediaPageWillBeOpenedInANewTab(String socialMedia) {
        Assert.assertTrue(footerComponentProvider.get().isCorrectSocialMediaPageOpened(socialMedia));
    }

    @When("user clicks on my account {string} link in the footer")
    public void userClicksOnMyAccountLinkInTheFooter(String linkText) {
        Assert.assertTrue(footerComponentProvider.get().clickOnMyAccountLinks(linkText));
    }

    @Then("my account {string} page will be opened")
    public void myAccountPageWillBeOpened(String expectedPage) {
        Assert.assertTrue(footerComponentProvider.get().isCorrectMyAccountPageOpenedForAnonymousUser(expectedPage));
    }

    @Then("my account {string} page will be opened for logged in user")
    public void myAccountPageWillBeOpenedForLoggedInUser(String expectedPage) {
        Assert.assertTrue(footerComponentProvider.get().isCorrectMyAccountPageOpenedForLoggedInUser(expectedPage));
    }

    @When("user clicks on customer service {string} link in the footer")
    public void userClicksOnCustomerServiceLinkInTheFooter(String customerServiceLink) {
        Assert.assertTrue(footerComponentProvider.get().clickOnCustomerServiceLinks(customerServiceLink));
    }

    @Then("customer service {string} page will be opened")
    public void customerServicePageWillBeOpened(String customerServiceLink) {
        Assert.assertTrue(footerComponentProvider.get().isCorrectCustomerServicePageOpenedF(customerServiceLink));
    }

    @When("user clicks on information {string} link in the footer")
    public void userClicksOnInformationLinkInTheFooter(String informationLink) {
        Assert.assertTrue(footerComponentProvider.get().clickOnInformationLinks(informationLink));
    }

    @Then("information {string} page will be opened")
    public void informationPageWillBeOpened(String informationLink) {
        Assert.assertTrue(footerComponentProvider.get().isCorrectInformationPageOpenedF(informationLink));
    }
}