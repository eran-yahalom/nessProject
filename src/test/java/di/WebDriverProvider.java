package di;

import com.google.inject.Provider;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.DriverManager;
import java.io.File;

public class WebDriverProvider implements Provider<WebDriver> {
    @Override
    public WebDriver get() {
        if (DriverManager.getDriver() != null) {
            return DriverManager.getDriver();
        }

        ChromeOptions options = new ChromeOptions();

        // בדיקה אם אנחנו בתוך Docker
        boolean isDocker = new File("/usr/bin/chromium").exists();

        if (isDocker) {
            // 1. הגדרת הנתיב לדרייבר שראינו הרגע בטרמינל (חובה ל-M3 ב-Docker)
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");

            // 2. הגדרת הנתיב לדפדפן
            options.setBinary("/usr/bin/chromium");

            // 3. הגדרות הרצה לקונטיינר
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu"); // מונע קריסות גרפיות ב-Docker
        } else {
            // הרצה מקומית על ה-MacBook M3 שלך
            WebDriverManager.chromedriver().setup();
        }

        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");

        // יצירת הדרייבר
        WebDriver driver = new ChromeDriver(options);
        DriverManager.setDriver(driver);

        return driver;
    }
}