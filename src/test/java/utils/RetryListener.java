package utils;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryListener implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        // זה מבטיח שכל מתודה ש-TestNG מריץ (כולל runScenario של Cucumber) תקבל את ה-Retry
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}