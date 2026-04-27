package configurations;

import com.google.inject.AbstractModule;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new DriverModule());
        install(new TestModule());
    }
}