package launchers;

import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import utils.RetryListener;

@Listeners(RetryListener.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"step_definitions", "hooks", "di", "configurations"},
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "json:target/cucumber.json"
        },
        tags = "@logIn"
)
public class LoginLauncher extends BaseLauncher {

    @Override
    public void runScenario(io.cucumber.testng.PickleWrapper pickle, io.cucumber.testng.FeatureWrapper cucumberFeature) {
        super.runScenario(pickle, cucumberFeature);
    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
