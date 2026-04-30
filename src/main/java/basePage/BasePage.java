package basePage;

import block.*;
import helper.ReworkedRetryer;
import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.core.context.RetryerContext;
import io.qameta.atlas.webdriver.AtlasWebElement;
import io.qameta.atlas.webdriver.WebDriverConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;

import static java.util.Collections.singletonList;

public class BasePage {
    protected WebDriver driver;
    protected Atlas atlas;
    protected WebDriverWait wait;
    public static final Logger log = LogManager.getLogger(BasePage.class);

    private static final int DEFAULT_TIMEOUT = 20;
    private static final int MAXIMUM_TIMEOUT = 30;

    // Blocks
    protected Input input;
    protected Button button;
    protected DropDown dropdown;
    protected Label label;
    protected CheckBox checkbox;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.atlas = new Atlas(new WebDriverConfiguration(driver)).context(
                new RetryerContext(new ReworkedRetryer(2000L, 100L, singletonList(NoSuchElementException.class))));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));

        // Initialize blocks with WebDriver
        this.input = new Input(driver);
        this.button = new Button(driver);
        this.dropdown = new DropDown(driver);
        this.label = new Label(driver);
        this.checkbox = new CheckBox(driver);
    }

    protected String getPageTitle() {
        String title = driver.getTitle();
        log.debug("Page title: {}", title);
        return title;
    }

    protected String getCurrentPageUrl() {
        String url = driver.getCurrentUrl();
        log.info("Current URL: {}", url);
        return url;
    }

    protected void scrollIntoView(AtlasWebElement<?> element) {
        log.debug("Scrolling element into view");
        wait.until(ExpectedConditions.visibilityOf(element));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView", element);
    }

    protected String waitForUrl(String urlFraction) {
        log.debug("Waiting for URL to contain: {}", urlFraction);

        wait.until(ExpectedConditions.urlContains(urlFraction));
        String currentUrl = driver.getCurrentUrl();

        log.debug("URL now contains: {}", urlFraction);
        return currentUrl;
    }

    protected String handleLoginResult(AtlasWebElement<?> errorElement) {
        log.debug("Waiting for result: success URL or error toast");

        // Poll every 500ms for up to 20s — whichever condition appears first wins
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(Exception.class);

        String result = fluentWait.until(d -> {
            // Success: URL changed to /account
            if (d.getCurrentUrl().contains("/account")) {
                log.info("Action successful->Redirected to: {}", d.getCurrentUrl());
                return d.getCurrentUrl();
            }
            // Failure: toast/error message is visible
            try {
                if (errorElement.isDisplayed()) {
                    String errorText = errorElement.getText().trim();
                    if (!errorText.isEmpty()) {
                        log.warn("Action failed. Error: {}", errorText);
                        return errorText;
                    }
                }
            } catch (Exception ignored) {
                // element not yet in DOM — keep polling
            }
            return null; // neither condition met yet — keep polling
        });

        return result;
    }

    protected String handleRegistrationResult(AtlasWebElement<?> errorElement) {
        log.debug("Waiting for result: success (/login) or error message on register page");

        // Poll every 500ms for up to 20s — whichever condition appears first wins
        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(Exception.class);

        String result = fluentWait.until(d -> {
            // Success: URL changed to /account
            if (d.getCurrentUrl().contains("/login")) {
                log.info("Action successful. Redirected to: {}", d.getCurrentUrl());
                return d.getCurrentUrl();
            }
            // Failure: toast/error message is visible
            try {
                if (errorElement.isDisplayed()) {
                    String errorText = errorElement.getText().trim();
                    if (!errorText.isEmpty()) {
                        log.warn("Action failed. Error: {}", errorText);
                        return errorText;
                    }
                }
            } catch (Exception ignored) {
                // element not yet in DOM — keep polling
            }
            return null; // neither condition met yet — keep polling
        });

        return result;
    }

    protected <T> T on(Class<T> pageClass) {
        return atlas.create(driver, pageClass);
    }

    protected WebDriverWait getWait() {
        return wait;
    }

    public static String captureScreenshot(WebDriver driver, String testName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
        File destination = new File(path);
        try {
            FileHandler.copy(srcFile, destination);
        } catch (IOException e) {
            //Error case
        }
        return path;
    }

}