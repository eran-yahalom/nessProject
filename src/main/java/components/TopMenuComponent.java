package components;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TopMenuComponent extends BaseComponent {

    private List<WebElement> subMenuLink;

    @FindBy(css = ".header-menu .top-menu>li>a")
    private List<WebElement> topMenuLinks;

    @FindBy(css = ".header-menu .top-menu li:nth-child(3) ul li a")
    private List<WebElement> electronicsSubMenuLink;

    @FindBy(css = ".header-menu .top-menu li:nth-child(2) ul li a")
    private List<WebElement> computersSubMenuLink;

    @FindBy(css = ".header-logo a")
    private WebElement logoLink;

    @Inject
    public TopMenuComponent(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public boolean clickOnTopMenuLink(String linkText) {
        wait.until(ExpectedConditions.visibilityOfAllElements(topMenuLinks));
        for (WebElement link : topMenuLinks) {
            if (getText(link).equalsIgnoreCase(linkText)) {

                waitForElementToBeClickable(link);
                return click(link);
            }
        }
        return false;
    }

    public boolean clickOnSubMenuLink(String linkText, String subLinkText) {
        for (WebElement link : topMenuLinks) {
            if (getText(link).equalsIgnoreCase(linkText)) {
                hover(link);

                subMenuLink = getSubMenuByText(linkText);
                wait.until(ExpectedConditions.visibilityOfAllElements(subMenuLink));

                for (WebElement subLink : subMenuLink) {
                    if (getText(subLink).equalsIgnoreCase(subLinkText)) {

                        wait.until(ExpectedConditions.elementToBeClickable(subLink));
                        return click(subLink);
                    }
                }
            }
        }
        return false;
    }

    public boolean clickOnLogoLink() {
        return click(logoLink);
    }

    public List<WebElement> getSubMenuByText(String linkText) {

        Map<String, List<WebElement>> menuMap = Map.of(
                "electronics", electronicsSubMenuLink,
                "computers", computersSubMenuLink
        );

        return Optional.ofNullable(menuMap.get(linkText.toLowerCase()))
                .orElse(Collections.emptyList());
    }
}
