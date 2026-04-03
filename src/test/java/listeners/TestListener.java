package listeners;

import basePage.BasePage;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import webDriverManager.DriverManager;

public class TestListener implements ITestListener {
    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        log.info("=== STARTING TEST SUITE: {} ===", context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("=== FINISHED TEST SUITE: {} ===", context.getName());
        log.info("Passed tests: {}", context.getPassedTests().size());
        log.info("Failed tests: {}", context.getFailedTests().size());
        log.info("Skipped tests: {}", context.getSkippedTests().size());
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info("STARTING TEST: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("TEST PASSED: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error("TEST FAILED: {}", result.getMethod().getMethodName());

        WebDriver driver = DriverManager.getDriver();

        if (driver != null) {
            String testName = result.getMethod().getMethodName();
            BasePage.captureScreenshot(driver, testName);

            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.getLifecycle().addAttachment(
                        testName,
                        "image/png",
                        "png",
                        screenshot
                );
                log.info("Screenshot attached to Allure report");
            } catch (Exception e) {
                log.error("Failed to capture/attach screenshot: {}", e.getMessage());
            }
        } else {
            log.warn("WebDriver was null - skipping screenshot capture");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("TEST SKIPPED: {}", result.getMethod().getMethodName());
    }
}
