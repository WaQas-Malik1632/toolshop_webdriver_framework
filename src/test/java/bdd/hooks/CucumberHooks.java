package bdd.hooks;

import io.cucumber.java.*;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Instant;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import webDriverManager.DriverManager;

import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

/**
 * Cucumber Hooks for BDD Framework with Allure Integration
 * <p>
 * Available Cucumber Hook Annotations:
 * - @Before: Runs before each scenario (can have order parameter)
 * - @After: Runs after each scenario (can have order parameter)
 * - @BeforeStep: Runs before each step
 * - @AfterStep: Runs after each step
 * - @BeforeAll: Runs once before all scenarios (static method)
 * - @AfterAll: Runs once after all scenarios (static method)
 * <p>
 * Hook Execution Order:
 * - Lower order number = Higher priority (executes first)
 * - @Before(order = 0) executes before @Before(order = 1)
 * - @After(order = 0) executes after @After(order = 1)
 */

public class CucumberHooks {

    private static final Logger log = LogManager.getLogger(CucumberHooks.class);
    private Properties prop;
    private Instant scenarioStartTime;

    @BeforeAll
    public static void beforeAll() {
        log.info("╔════════════════════════════════════════════════════════════════╗");
        log.info("║           STARTING BDD TEST EXECUTION                         ║");
        log.info("╚════════════════════════════════════════════════════════════════╝");
    }

    @Before(order = 0)
    public void setUp(Scenario scenario) throws IOException {

        scenarioStartTime = Instant.now();

        log.info("══════════════════════════════════════════════════════════════");
        log.info("SCENARIO: {}", scenario.getName());
        log.info("TAGS: {}", scenario.getSourceTagNames());

        prop = DriverManager.loadProperties();
        String browserName = prop.getProperty("browser");
        String appBaseUrl = prop.getProperty("app.base.url");

        DriverManager.initializeDriver(browserName, appBaseUrl);

        log.info("✓ Browser: {} | URL: {}", browserName, appBaseUrl);

    }

    @Before(value = "@RequiresLogin", order = 1)
    public void setUpForLoginScenarios(Scenario scenario) {
        log.info("→ Pre-login setup for: {}", scenario.getName());
    }

    @After(order = 1)
    public void tearDown(Scenario scenario) {

        Duration duration = Duration.between(scenarioStartTime, Instant.now());

        try {
            WebDriver driver = DriverManager.getDriver();

            if (scenario.isFailed()) {
                log.error("✗ FAILED: {}", scenario.getName());

                if (driver != null) {
                    try {
                        byte[] screenshot = ((TakesScreenshot) driver)
                                .getScreenshotAs(OutputType.BYTES);

                        Allure.getLifecycle().addAttachment(
                                scenario.getName(),
                                "image/png",
                                "png",
                                screenshot
                        );

                        log.info("✓ Screenshot attached");

                    } catch (Exception e) {
                        log.error("Screenshot failed: {}", e.getMessage());
                    }
                }

                Allure.addAttachment("Failure Details", "text/plain",
                        "Scenario: " + scenario.getName() +
                                "\nStatus: FAILED" +
                                "\nDuration: " + duration.getSeconds() + " seconds");
            }

            log.info("Duration: {} sec", duration.getSeconds());

        } catch (Exception e) {
            log.error("Teardown error: {}", e.getMessage(), e);
        }
    }

    @After(order = 0)
    public void finalCleanup() {
        try {
            DriverManager.quitDriver();
            log.info("✓ Browser closed");
        } catch (Exception e) {
            log.error("Error closing browser: {}", e.getMessage());
        }

        log.info("══════════════════════════════════════════════════════════════");
    }

    @AfterAll
    public static void afterAll() {
        log.info("╔════════════════════════════════════════════════════════════════╗");
        log.info("║           FINISHED BDD TEST EXECUTION                         ║");
        log.info("╚════════════════════════════════════════════════════════════════╝");
    }
}