package di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import configurations.TestModule;
import io.cucumber.guice.InjectorSource;

public class TestInjectorSource implements InjectorSource {

    @Override
    public Injector getInjector() {
        return Guice.createInjector(new TestModule());
    }
}