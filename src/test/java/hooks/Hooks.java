package hooks;

import com.google.inject.Inject;
import configurations.EnvManager;
import configurations.db.DBSetupService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.AllureUtils;
import utils.DriverManager;
import utils.ScenarioContext;

public class Hooks {

    private final WebDriver driver;

    @Inject
    public Hooks(WebDriver driver) {
        this.driver = driver;
    }

    @Before(order = 0)
    public void setup() {

        DBSetupService.init(
                EnvManager.get().getSchema(),
                EnvManager.get().getDbName(),
                null,
                null
        );

        if (driver != null) {
            driver.manage().window().maximize();
            driver.get(EnvManager.get().getUrl());

            try {
                driver.findElement(By.cssSelector("#details-button")).click();
                driver.findElement(By.cssSelector("#proceed-link")).click();
            } catch (Exception ignored) {
            }
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed() && driver != null) {
                try {
                    AllureUtils.attachScreenshot(driver, "Failure Screenshot - " + scenario.getName());
                    AllureUtils.attachBrowserLogs(driver);
                    AllureUtils.attachPageSource(driver);
                    AllureUtils.attachCurrentUrl(driver);
                } catch (Exception e) {
                    System.err.println("Failed to attach artifacts to Allure: " + e.getMessage());
                }
            }
        } finally {
            ScenarioContext.clear();

            try {
                DBSetupService.close();
            } catch (Exception e) {
                System.err.println("DB connection close failed: " + e.getMessage());
            }

            if (driver != null) {
                driver.quit();
            }
            DriverManager.unload();
        }
    }
}