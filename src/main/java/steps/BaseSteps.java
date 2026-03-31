package steps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import webDriverManager.WebDriverFunctional;

/**
 * BaseSteps — shared driver reference and navigation helpers for all Step classes.
 * Driver is obtained from WebDriverFunctional (ThreadLocal), never created here.
 */
public class BaseSteps {

    protected static final Logger log = LogManager.getLogger(BaseSteps.class);
    protected final WebDriver driver;

    public BaseSteps() {
        this.driver = WebDriverFunctional.getDriver();
    }

}
