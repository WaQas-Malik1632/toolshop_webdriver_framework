package block;

import io.qameta.atlas.webdriver.AtlasWebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Input {
    private static final Logger log = LogManager.getLogger(Input.class);
    private static final int DEFAULT_WAIT_TIMEOUT = 10;
    private final WebDriverWait wait;

    public Input(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT));
    }

    public Input enterText(AtlasWebElement<?> element, String text) {
        log.debug("Entering text into input field: {}", text);

        // Handle null or empty text gracefully
        if (text == null || text.isEmpty()) {
            log.warn("Attempted to enter null or empty text. Skipping input.");
            return this;
        }

        waitForVisible(element);
        element.clear();
        element.sendKeys(text);

        log.debug("Text entered successfully");
        return this;
    }

    public Input clearAndEnterText(AtlasWebElement<?> element, String text) {
        // Handle null or empty text gracefully
        if (text == null || text.isEmpty()) {
            log.warn("Attempted to enter null or empty text. Skipping input.");
            return this;
        }

        waitForVisible(element);
        element.clear();
        element.sendKeys(text);

        log.debug("Field cleared and text entered successfully");
        return this;
    }

    private void waitForVisible(AtlasWebElement<?> element) {
        waitToBeVisible(element);
    }

    private void waitToBeVisible(AtlasWebElement<?> element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            log.debug("Element is visible");
        } catch (Exception e) {
            log.error("Element not visible within {} seconds: {}", DEFAULT_WAIT_TIMEOUT, e.getMessage());
            throw e;
        }
    }

    public String getText(AtlasWebElement<?> element) {
        log.debug("Getting text from input field");

        String text = element.getAttribute("value");

        log.debug("Retrieved text: {}", text);
        return text;
    }

    public boolean isDisplayed(AtlasWebElement<?> element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (Exception e) {
            log.debug("Element is not displayed: {}", e.getMessage());
            return false;
        }
    }

    public boolean isEnabled(AtlasWebElement<?> element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return element.isEnabled();
        } catch (Exception e) {
            log.debug("Element is not enabled: {}", e.getMessage());
            return false;
        }
    }

}