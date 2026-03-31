package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import webDriverManager.WebDriverFunctional;

import java.time.Duration;

public class BasePage {

    protected static final Logger log = LogManager.getLogger(BasePage.class);

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        long timeout = Long.parseLong(
                WebDriverFunctional.prop.getProperty("explicitWait", "15"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    protected WebElement waitForVisible(WebElement element) {
        log.debug("Waiting for element to be visible: {}", element);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForClickable(WebElement element) {
        log.debug("Waiting for element to be clickable: {}", element);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void click(WebElement element) {
        log.debug("Clicking element: {}", element);
        waitForClickable(element).click();
    }

    protected void type(WebElement element, String text) {
        log.debug("Typing '{}' into element: {}", text, element);
        waitForVisible(element).clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        String text = waitForVisible(element).getText().trim();
        log.debug("Got text '{}' from element: {}", text, element);
        return text;
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return waitForVisible(element).isDisplayed();
        } catch (Exception e) {
            log.debug("Element not visible: {}", element);
            return false;
        }
    }
}
