package context;

import io.cucumber.guice.ScenarioScoped;

import java.util.HashMap;
import java.util.Map;

public class ScenarioState {

    private static final ThreadLocal<Map<String, Object>> STATE =
            ThreadLocal.withInitial(HashMap::new);

    public static void save(String key, Object value) {
        STATE.get().put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Class<T> clazz) {
        return (T) STATE.get().get(key);
    }

    public static void clear() {
        STATE.remove();
    }
}