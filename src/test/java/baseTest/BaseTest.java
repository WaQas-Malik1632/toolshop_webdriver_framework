package baseTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import webDriverManager.WebDriverFunctional;

public class BaseTest {

    protected static final Logger log = LogManager.getLogger(BaseTest.class);
    protected LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        log.info("===== Test setup: launching browser =====");
        WebDriverFunctional.setupBrowser();
        loginPage = new LoginPage(WebDriverFunctional.getDriver());

        log.info("Browser ready. Base URL opened.");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        log.info("===== Test teardown: closing browser =====");
        WebDriverFunctional.quitDriver();
    }
}
