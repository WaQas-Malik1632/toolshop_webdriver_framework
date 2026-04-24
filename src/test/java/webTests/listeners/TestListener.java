package webTests.listeners;

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
        log.info("╔══════════════════════════════════════════════════════╗");
        log.info("  SUITE STARTED: {}", context.getName());
        log.info("╚══════════════════════════════════════════════════════╝");
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info("╔══════════════════════════════════════════════════════╗");
        log.info("  SUITE FINISHED: {}", context.getName());
        log.info("  ✔ Passed  : {}", context.getPassedTests().size());
        log.info("  ✘ Failed  : {}", context.getFailedTests().size());
        log.info("  ⊘ Skipped : {}", context.getSkippedTests().size());
        log.info("╚══════════════════════════════════════════════════════╝");
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info("──────────────────────────────────────────────────────");
        log.info("▶ TEST STARTED : [{}] {}",
                result.getTestClass().getRealClass().getSimpleName(),
                result.getMethod().getMethodName());
        log.info("  Groups    : {}", String.join(", ", result.getMethod().getGroups()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        long duration = result.getEndMillis() - result.getStartMillis();
        log.info("✔ TEST PASSED  : [{}] {} ({}ms)",
                result.getTestClass().getRealClass().getSimpleName(),
                result.getMethod().getMethodName(),
                duration);
        log.info("──────────────────────────────────────────────────────");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        long duration = result.getEndMillis() - result.getStartMillis();
        log.error("✘ TEST FAILED  : [{}] {} ({}ms)",
                result.getTestClass().getRealClass().getSimpleName(),
                result.getMethod().getMethodName(),
                duration);
        log.error("  Reason    : {}", result.getThrowable().getMessage());

        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            String testName = result.getMethod().getMethodName();
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.getLifecycle().addAttachment(testName, "image/png", "png", screenshot);
                log.info("  Screenshot attached to Allure report");
                BasePage.captureScreenshot(driver, testName);
            } catch (Exception e) {
                log.error("  Failed to capture screenshot: {}", e.getMessage());
            }
        }
        log.error("──────────────────────────────────────────────────────");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn("⊘ TEST SKIPPED : [{}] {}",
                result.getTestClass().getRealClass().getSimpleName(),
                result.getMethod().getMethodName());
        log.warn("──────────────────────────────────────────────────────");
    }
}