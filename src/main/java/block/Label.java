package block;

import io.qameta.atlas.webdriver.AtlasWebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Label {
    private static final Logger log = LogManager.getLogger(Label.class);
    private static final int DEFAULT_WAIT_TIMEOUT = 20;
    private final WebDriver driver;
    private final WebDriverWait wait;

    public Label(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT));
    }

    public String getText(AtlasWebElement<?> element) {
        waitForVisible(element);
        String text = element.getText().trim();
        log.debug("Retrieved text: {}", text);
        return text;
    }

    public boolean isDisplayed(AtlasWebElement<?> element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            log.debug("Element is displayed");
            return true;
        } catch (Exception e) {
            log.debug("Element is not displayed: {}", e.getMessage());
            return false;
        }
    }

    public String waitForToast(AtlasWebElement<?> element) {
        log.debug("Waiting for toast/alert message");
        try {
            wait.until(ExpectedConditions.visibilityOf(element));

            String text = element.getText().trim();
            log.debug("Toast message captured: {}", text);
            return text;
        } catch (Exception e) {
            log.error("Toast message not visible within timeout: {}", e.getMessage());
            throw new RuntimeException("Failed to capture toast message", e);
        }
    }

    private void waitForVisible(AtlasWebElement<?> element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            log.debug("Element is visible");
        } catch (Exception e) {
            log.error("Element not visible within {} seconds: {}", DEFAULT_WAIT_TIMEOUT, e.getMessage());
            throw e;
        }

    }
}