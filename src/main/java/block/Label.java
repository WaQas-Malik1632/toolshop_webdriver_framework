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

    public Label(WebDriver driver) {
        this.driver = driver;
    }

    public String getText(AtlasWebElement<?> element) {
        log.debug("Getting text from label");
        waitForVisible(element, DEFAULT_WAIT_TIMEOUT);
        String text = element.getText().trim();
        log.debug("Retrieved text: {}", text);
        return text;
    }

    public boolean isDisplayed(AtlasWebElement<?> element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            log.debug("Element is not displayed: {}", e.getMessage());
            return false;
        }
    }

    public String waitForToast(AtlasWebElement<?> element) {
        log.debug("Waiting for toast/alert message");
        waitForVisible(element, DEFAULT_WAIT_TIMEOUT);
        String text = element.getText().trim();
        log.debug("Toast message: {}", text);
        return text;
    }

    private void waitForVisible(AtlasWebElement<?> element, int timeoutSeconds) {
        // Create a fresh WebDriverWait each time — never mutate a shared instance
        WebDriverWait freshWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        try {
            freshWait.until(ExpectedConditions.visibilityOf(element.getWrappedElement()));
            log.debug("Element is visible");
        } catch (Exception e) {
            log.error("Element not visible within {} seconds: {}", timeoutSeconds, e.getMessage());
            throw e;
        }
    }
}
