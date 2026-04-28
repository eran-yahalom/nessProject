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

        boolean isDocker = new File("/usr/bin/chromium").exists();

        if (isDocker) {
            System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");

            options.setBinary("/usr/bin/chromium");
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
        } else {
            WebDriverManager.chromedriver().setup();
        }

        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        DriverManager.setDriver(driver);

        return driver;
    }
}