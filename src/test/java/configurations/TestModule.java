package configurations;

import com.google.inject.AbstractModule;
import io.cucumber.guice.CucumberModules;

public class TestModule extends AbstractModule {

    @Override
    protected void configure() {
        install(CucumberModules.createScenarioModule());
        install(new DriverModule());
    }
}