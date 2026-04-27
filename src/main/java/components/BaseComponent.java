package components;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
public abstract class BaseComponent {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String mainWindow;

    public BaseComponent(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getPageHeader() {
        return driver.findElement(By.cssSelector(".page-title h1"))
                .getText()
                .trim();
    }

    protected WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForClickability(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickability(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected boolean click(WebElement element) {

        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView(true);", element);
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
            return true;

        } catch (Exception e) {
            try {
                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", element);
                return true;

            } catch (Exception ex) {
                log.error("Fail to click on button{}", element.getText());
                return false;
            }
        }
    }

    protected boolean fillText(WebElement element, String text) {
        try {
            waitForVisibility(element);
            element.clear();
            element.sendKeys(text);
            return true;
        } catch (Exception e) {
            log.error("Fail to fill text in element: {} | Error: {}", element.getText(), e.getMessage());
            return false;
        }
    }

    protected String getText(WebElement element) {
        try {
            return waitForVisibility(element).getText();
        } catch (Exception e) {
            log.error("Fail to get text from element: {} | Error: {}", element.getText(), e.getMessage());
            return "";
        }
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return waitForVisibility(element).isDisplayed();
        } catch (Exception e) {
            log.error("Fail to check visibility of element: Error: {}", e.getMessage());
            return false;
        }
    }

    protected boolean isEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (Exception e) {
            log.error("Element is not enabled");
            return false;
        }
    }

    public void hover(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementToBeClickable(By element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void moveToNewWindow() {

        mainWindow = driver.getWindowHandle();

        Set<String> windows = driver.getWindowHandles();

        for (String win : windows) {
            if (!win.equals(mainWindow)) {
                driver.switchTo().window(win);
            }
        }
    }

    public void backToMainWindow() {
        driver.close();
        driver.switchTo().window(mainWindow);
    }

    public boolean clickAndMoveToSelectedSocialMedia(WebElement element) {
        try {
            click(element);
            moveToNewWindow();
            return true;
        } catch (Exception e) {
            log.error("Fail to click and move to selected social media: {} | Error: {}", element.getText(), e.getMessage());
            return false;
        }
    }

    public String getAlertText() {
        return driver.switchTo().alert().getText();
    }

    public boolean clickOnAlertOKButton() {
        try {
            driver.switchTo().alert().accept();
            return true;
        } catch (Exception e) {
            log.error("Fail to click on alert OK button | Error: {}", e.getMessage());
            return false;
        }
    }

    public boolean selectOptionFromDropdownByVisibleText(WebElement element, String value) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            Select select = new Select(element);
            select.selectByVisibleText(value);
            return true;
        } catch (StaleElementReferenceException e) {
            log.error("Stale element reference: {} | Error: {}", element.getText(), e.getMessage());
            return false;
        }
    }

    public boolean clickOnFooterLinkByIndex(By footerLinksLocator, int index) {
        try {
            List<WebElement> freshLinks = driver.findElements(footerLinksLocator);

            if (index < freshLinks.size()) {
                return click(freshLinks.get(index));
            }
            System.out.println("Index " + index + " not found in footer links.");
            return false;
        } catch (StaleElementReferenceException e) {
            return click(driver.findElements(footerLinksLocator).get(index));
        }
    }

    public List<String> getAttributesSafe(By locator, String attribute) {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .ignoring(StaleElementReferenceException.class)
                .until(d -> d.findElements(locator).stream()
                        .map(el -> el.getAttribute(attribute))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
    }
}
