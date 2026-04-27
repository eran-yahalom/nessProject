package components;

import com.google.inject.Inject;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class BreadcrumbComponent extends BaseComponent {

    private By breadcrumbElements = By.cssSelector(".breadcrumb ul li");
    private By breadcrumbContainer = By.cssSelector(".breadcrumb");

    @Inject
    public BreadcrumbComponent(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public boolean isDisplayedCorrectly(String... expectedBreadcrumbs) {
        try {
            WebElement container = driver.findElement(breadcrumbContainer);
            String[] actual = container.getText().split("/");

            if (actual.length != expectedBreadcrumbs.length) {
                return false;
            }

            for (int i = 0; i < expectedBreadcrumbs.length; i++) {
                if (!actual[i].trim().equalsIgnoreCase(expectedBreadcrumbs[i].trim())) {
                    return false;
                }
            }

            return true;

        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public List<String> getBreadcrumbSteps() {
        return driver.findElements(breadcrumbElements)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public String getLastBreadcrumbStep() {
        List<WebElement> breadCrumbs = driver.findElements(breadcrumbElements);
        if (breadCrumbs.isEmpty()) {
            return null;
        }
        return breadCrumbs.get(breadCrumbs.size() - 1).getText();
    }

    public boolean selectAndClickOnSpecificBreadCrumb(String breadCrumbName) {
        try {
            List<WebElement> breadCrumbs = driver.findElements(breadcrumbElements);
            for (WebElement breadCrumb : breadCrumbs) {
                String breadCrumbText = breadCrumb.getText().replace("/", "").trim();
                if (breadCrumbText.equalsIgnoreCase(breadCrumbName)) {
                    breadCrumb.click();
                    return true;
                }
            }
        } catch (NoSuchElementException ignored) {
            log.error("Breadcrumb element not found: " + breadCrumbName);
        }
        return false;
    }

    public int countNumberOfBreadcrumbs() {
        try {
            return driver.findElements(breadcrumbElements).size();
        } catch (NoSuchElementException e) {
            log.info("No breadcrumbs found on the page.");
            return 0;
        }
    }
}
