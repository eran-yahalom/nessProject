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
            // 1. קודם כל מטפלים בתיעוד הכישלון (Screenshot) כל עוד הדרייבר חי
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
            // 2. ניקוי נתונים - תמיד יתבצע גם אם הצילום נכשל
            ScenarioContext.clear();

            // 3. סגירת ה-DB בנפרד (כדי שלא יפיל את ה-Driver Quit)
            try {
                DBSetupService.close();
            } catch (Exception e) {
                System.err.println("DB connection close failed: " + e.getMessage());
            }

            // 4. שחרור הדרייבר
            if (driver != null) {
                driver.quit();
            }
            DriverManager.unload();
        }
    }
}