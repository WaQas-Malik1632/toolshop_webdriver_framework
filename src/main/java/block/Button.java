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
    private static final int DEFAULT_WAIT_TIMEOUT = 20;
    private final WebDriverWait wait;
    private final WebDriver driver;

    public Button(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT));
    }

    public Button waitAndClick(AtlasWebElement<?> element) {
        return click(element);
    }

    public Button click(AtlasWebElement<?> element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            log.debug("Waiting for element to be clickable");
            element.isEnabled();
            element.click();
            log.debug("Button clicked successfully");
        } catch (Exception e) {
            log.error("Element not clickable within {} seconds: {}", DEFAULT_WAIT_TIMEOUT, e.getMessage());
            throw e;
        }

        return this;
    }

    public boolean isClickable(AtlasWebElement<?> element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            log.debug("Element is clickable");
            return true;
        } catch (Exception e) {
            log.debug("Element is not clickable: {}", e.getMessage());
            return false;
        }
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

}