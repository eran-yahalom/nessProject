package launchers;

import di.TestInjectorSource;
import io.cucumber.guice.InjectorSource;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"step_definitions", "hooks", "di", "configurations"},
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "json:target/cucumber.json"
        },
        tags = "@register"
)
public class RegisterLauncher extends BaseLauncher {

    static {
        System.setProperty(
                InjectorSource.class.getName(),
                TestInjectorSource.class.getName()
        );
    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}