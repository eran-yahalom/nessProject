package configurations;

import com.google.inject.AbstractModule;
import di.WebDriverProvider;
import io.cucumber.guice.ScenarioScoped;
import org.openqa.selenium.WebDriver;

public class DriverModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(WebDriver.class)
                .toProvider(WebDriverProvider.class)
                .in(ScenarioScoped.class);
    }
}