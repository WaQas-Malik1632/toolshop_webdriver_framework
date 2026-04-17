package block;

import io.qameta.atlas.webdriver.AtlasWebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckBox {
    private static final Logger log = LogManager.getLogger(CheckBox.class);
    private static final int DEFAULT_WAIT_TIMEOUT = 10;
    private final WebDriverWait wait;

    public CheckBox (WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT));
    }

    public CheckBox check(AtlasWebElement<?> element) {
        log.debug("Checking checkbox");

        waitForClickable(element);

        if (!element.isSelected()) {
            element.click();
            log.debug("Checkbox checked");
        } else {
            log.debug("Checkbox already checked");
        }

        return this;
    }

    public CheckBox uncheck(AtlasWebElement<?> element) {
        log.debug("Unchecking checkbox");

        waitForClickable(element);

        if (element.isSelected()) {
            element.click();
            log.debug("Checkbox unchecked");
        } else {
            log.debug("Checkbox already unchecked");
        }

        return this;
    }

    public CheckBox setState(AtlasWebElement<?> element, boolean value) {
        log.debug("Setting checkbox state to: {}", value);

        waitForClickable(element);

        if (element.isSelected() != value) {
            element.click();
            log.debug("Checkbox state changed");
        }

        return this;
    }

    private void waitForClickable(AtlasWebElement<?> element) {
        waitForToBeClickable(element);
    }

    private void waitForToBeClickable(AtlasWebElement<?> element) {
        try {
            wait.withTimeout(Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT))
                    .until(ExpectedConditions.elementToBeClickable(element));
            log.debug("Element is clickable");
        } catch (Exception e) {
            log.error("Element not clickable within {} seconds: {}", DEFAULT_WAIT_TIMEOUT, e.getMessage());
            throw e;
        }
    }

    public boolean isChecked(AtlasWebElement<?> element) {
        try {
            return element.isSelected(); // ✅ safe with Atlas
        } catch (Exception e) {
            return false;
        }
    }

}
