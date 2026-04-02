package block;

import io.qameta.atlas.webdriver.AtlasWebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Button {

    private static final Logger log = LogManager.getLogger(Button.class);
    private static final int DEFAULT_WAIT_TIMEOUT = 10;
    private final WebDriverWait wait;

    public Button(WebDriver driver)
    {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT));
    }

    public Button waitAndClick(AtlasWebElement<?> element) {
        return click(element, DEFAULT_WAIT_TIMEOUT);
    }

    public Button click(AtlasWebElement<?> element, int timeoutSeconds) {
        log.debug("Clicking button with wait: {} seconds", timeoutSeconds);

        try {
            wait.withTimeout(Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.elementToBeClickable(element.getWrappedElement()));
            log.debug("Element is clickable");

            element.click();
            log.debug("Button clicked successfully");
        } catch (Exception e) {
            log.error("Element not clickable within {} seconds: {}", timeoutSeconds, e.getMessage());
            throw e;
        }

        return this;
    }

    public boolean isClickable(AtlasWebElement<?> element) {
        try {
            return element.isDisplayed() && element.isEnabled();
        } catch (Exception e) {
            log.debug("Element is not clickable: {}", e.getMessage());
            return false;
        }
    }

    public boolean isDisplayed(AtlasWebElement<?> element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            log.debug("Element is not displayed: {}", e.getMessage());
            return false;
        }
    }

}
