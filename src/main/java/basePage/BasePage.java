package basePage;

import block.Button;
import block.DropDown;
import block.Input;
import helper.ReworkedRetryer;
import io.qameta.atlas.core.Atlas;
import io.qameta.atlas.core.context.RetryerContext;
import io.qameta.atlas.webdriver.AtlasWebElement;
import io.qameta.atlas.webdriver.WebDriverConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
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
    protected Checkbox checkbox;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.atlas = new Atlas(new WebDriverConfiguration(driver)).context(
                new RetryerContext(new ReworkedRetryer(5000L, 100L, singletonList(NoSuchElementException.class))));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));

        // Initialize blocks with WebDriver
        this.input = new Input(driver);
        this.button = new Button(driver);
        this.dropdown = new DropDown(driver);
       // this.label = new Label(driver);
        this.label= new Label(driver);
        this.checkbox = new Checkbox(driver);
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
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element.getWrappedElement());
    }

    protected String waitForUrl(String urlFraction) {
        log.debug("Waiting for URL to contain: {}", urlFraction);

        wait.until(ExpectedConditions.urlContains(urlFraction));
        String currentUrl = driver.getCurrentUrl();

        log.debug("URL now contains: {}", urlFraction);
        return currentUrl;
    }

    protected String handleResult(AtlasWebElement<?> errorElement) {
        log.debug("Waiting for result: success URL or error message");
        try {
            // Wait for URL to contain /account (success case)
            wait.until(ExpectedConditions.urlContains("/account"));
            log.info("Action successful. Redirected to: {}", driver.getCurrentUrl());
            return driver.getCurrentUrl();

        } catch (Exception e) {
            // If URL doesn't change, check for error message
            try {
                wait.until(ExpectedConditions.visibilityOf(errorElement));
                String errorText = label.getText(errorElement);
                log.warn("Action failed. Error: {}", errorText);
                return errorText;
            } catch (Exception ex) {
                throw new RuntimeException("Neither success nor error condition met", ex);
            }
        }
    }

    protected <T> T on(Class<T> pageClass) {
        return atlas.create(driver, pageClass);
    }

    protected WebDriverWait getWait() {
        return wait;
    }

}