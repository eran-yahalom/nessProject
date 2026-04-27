package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import utils.Utils;

import java.time.Duration;
import java.util.Set;

@Log4j2
public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WebDriverWait longWait;
    protected WebDriverWait socialNetworkWait;
    protected JavascriptExecutor js;
    protected static String mainWindow;

    public BasePage(WebDriver driver) {
        this.driver = driver;

        // Waits
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.longWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.socialNetworkWait = new WebDriverWait(driver, Duration.ofSeconds(60));

        // JS
        this.js = (JavascriptExecutor) driver;

        // PageFactory
        PageFactory.initElements(driver, this);
    }

    // ==============================
    // Text actions
    // ==============================

    public String getPageHeader() {
        return driver.findElement(By.cssSelector(".page-title h1"))
                .getText()
                .trim();
    }

    public boolean fillText(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(text);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public String getText(WebElement element) {
        final int maxRetries = 3;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                wait.until(ExpectedConditions.visibilityOf(element));
                return element.getText();

            } catch (StaleElementReferenceException e) {
                if (attempt == maxRetries) {
                    throw e;
                }
                sleep(500);
            }
        }

        throw new IllegalStateException(
                "Failed to retrieve text after retries"
        );
    }

    public String getTextFromTextField(WebElement element) {
        return element.getAttribute("value");
    }

    public String getTextIfVisible(WebElement element) {
        if (isDisplayed(element)) {
            return element.getText();
        }
        return null;
    }

    // ==============================
    // Click actions
    // ==============================
    public boolean click(WebElement element) {
        try {
            longWait.until(ExpectedConditions.elementToBeClickable(element));
            highlightElement(element, "red");
            element.click();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean clickWithJS(WebElement element) {
        try {
            longWait.until(ExpectedConditions.elementToBeClickable(element));
            highlightElement(element, "blue");
            js.executeScript("arguments[0].click();", element);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean clickOnStaleElement(WebElement element) {
        final int maxRetries = 3;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(element));
                highlightElement(element, "green");
                element.click();
                return true;

            } catch (StaleElementReferenceException e) {
                if (attempt == maxRetries) {
                    e.printStackTrace();
                    return false;
                }
                sleep(500);
            }
        }

        return false;
    }
    // ==============================
    // Wait helpers
    // ==============================
    public void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    // ==============================
    // Display check
    // ==============================
    public boolean isDisplayed(WebElement element) {
        try {
            return element != null && element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }
    // ==============================
    // Hover
    // ==============================
    public void hover(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public void hoverAndClick(WebElement dropdown, WebElement option) {
        Actions actions = new Actions(driver);
        actions.moveToElement(dropdown).perform();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(option));

        option.click();
    }
    // ==============================
    // Scroll
    // ==============================
    public void scrollToElement(WebElement element) {
        js.executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element
        );
    }
    // ==============================
    // Highlight
    // ==============================
    protected void highlightElement(WebElement element, String color) {

        String originalStyle = element.getAttribute("style");

        String newStyle =
                "border: 2px solid " + color +
                        "; background-color: yellow;" +
                        originalStyle;

        js.executeScript(
                "arguments[0].setAttribute('style', arguments[1]);",
                element, newStyle
        );

        sleep(300);

        js.executeScript(
                "arguments[0].setAttribute('style', arguments[1]);",
                element, originalStyle
        );
    }
    // ==============================
    // Windows handling
    // ==============================
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
    // ==============================
    // Dropdown
    // ==============================
    public boolean selectOptionFromDropdownByValue(WebElement element, String value) {
        try {
            longWait.until(ExpectedConditions.elementToBeClickable(element));
            Select select = new Select(element);
            select.selectByValue(value);
            return true;
        } catch (StaleElementReferenceException e) {
            log.error("Failed to select option from dropdown by value", e);
            return false;
        }
    }

    public boolean selectOptionFromDropdownByVisibleText(WebElement element, String value) {
        try {
            longWait.until(ExpectedConditions.elementToBeClickable(element));
            Select select = new Select(element);
            select.selectByVisibleText(value);
            return true;
        } catch (StaleElementReferenceException e) {
            log.error("Failed to select option from dropdown by visible text", e);
            return false;
        }
    }

    public boolean selectOptionFromDropdownByIndex(WebElement element, String value) {
        try {
            longWait.until(ExpectedConditions.elementToBeClickable(element));
            Select select = new Select(element);
            select.selectByIndex(2);
            return true;
        } catch (StaleElementReferenceException e) {
            log.error("Failed to select option from dropdown by index", e);
            return false;
        }
    }
    // ==============================
    // Popup handling
    // ==============================
    public void closeSystemPopupIfPresent() {
        try {
            WebDriverWait shortWait =
                    new WebDriverWait(driver, Duration.ofSeconds(5));

            WebElement closeButton = shortWait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector(".z-close"))
            );

            if (closeButton.isDisplayed()) {
                closeButton.click();
            }

        } catch (Exception ignored) {
            log.error("System popup not found or already closed");
        }
    }

    // ==============================
    // Toast message
    // ==============================
    public String getToastMessage(WebElement element) {
        try {
            return getText(element);
        } catch (Exception e) {
            log.info("Toast message not found");
            return null;
        }
    }

    // ==============================
    // Header validation
    // ==============================
    public boolean confirmPageHeader(
            WebElement element,
            String textHeaderKey) {

        String expected =
                Utils.readProperty(textHeaderKey);

        return element.getText()
                .equalsIgnoreCase(expected);
    }

    // ==============================
    // Sleep helper (avoid if possible)
    // ==============================
    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
//            log.error("Sleep interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    public void waiting(long milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickBreadcrumbStep(String stepName) {
        driver.findElement(By.linkText(stepName)).click();
    }
}
