package apiTest;

import baseTest.BaseTest;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseApiTest extends BaseTest {

    @Override
    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        this.testMethod = method;
        log.info("===== STARTING API TEST: {} =====", method.getName());
        // No browser needed for API tests
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        log.info("===== FINISHED API TEST: {} =====", result.getMethod().getMethodName());
        // No driver to quit for API tests
    }
}
