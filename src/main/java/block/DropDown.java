package block;

import io.qameta.atlas.webdriver.AtlasWebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DropDown
{
    private static final Logger log = LogManager.getLogger(DropDown.class);
    private static final int DEFAULT_WAIT_TIMEOUT = 10;
    private final WebDriverWait wait;

    public DropDown (WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIMEOUT));
    }

    public DropDown selectByVisibleText(AtlasWebElement<?> element, String text) {
        log.debug("Selecting dropdown option by visible text: {}", text);

        waitForClickable(element);

        try {
            Select select = new Select(element);
            select.selectByVisibleText(text);
        } catch (Exception e) {
            element.click();
            element.findElement(org.openqa.selenium.By.xpath(".//option[normalize-space()='" + text + "']"))
                    .click();
        }

        log.debug("Option selected successfully");
        return this;
    }

    public DropDown selectByValue(AtlasWebElement<?> element, String value) {
        log.debug("Selecting dropdown option by value: {}", value);

        waitForClickable(element);

        try {
            Select select = new Select(element);
            select.selectByValue(value);
        } catch (Exception e) {
            element.click();
            element.findElement(org.openqa.selenium.By.xpath(".//option[@value='" + value + "']"))
                    .click();
        }

        log.debug("Option selected successfully");
        return this;
    }

    public DropDown selectByIndex(AtlasWebElement<?> element, int index) {
        log.debug("Selecting dropdown option by index: {}", index);

        waitForClickable(element);

        Select select = new Select(element);
        select.selectByIndex(index);

        log.debug("Option selected successfully");
        return this;
    }

    private void waitForClickable(AtlasWebElement<?> element) {
        waitForClickable(element, DEFAULT_WAIT_TIMEOUT);
    }

    private void waitForClickable(AtlasWebElement<?> element, int timeoutSeconds) {
        try {
            wait.withTimeout(Duration.ofSeconds(timeoutSeconds))
                    .until(ExpectedConditions.elementToBeClickable(element));
            log.debug("Element is clickable");
        } catch (Exception e) {
            log.error("Element not clickable within {} seconds: {}", timeoutSeconds, e.getMessage());
            throw e;
        }
    }

    public String getSelectedOption(AtlasWebElement<?> element) {
        log.debug("Getting selected dropdown option");

        try {
            Select select = new Select(element);
            return select.getFirstSelectedOption().getText();
        } catch (Exception e) {
            // fallback
            return element.getText();
        }
    }

}