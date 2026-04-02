package baseTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import steps.*;
import webDriverManager.DriverManager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

public class BaseTest {
    protected static final Logger log = LogManager.getLogger(BaseTest.class);
    protected Properties prop;
    protected Method testMethod;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) throws IOException {
        this.testMethod = method;
        log.info("===== STARTING TEST: {} =====", method.getName());

        prop = DriverManager.loadProperties();

        String browserName = prop.getProperty("browser");
        String baseUrl = prop.getProperty("app.base.url");
        DriverManager.initializeDriver(browserName, baseUrl);

        log.info("Browser initialized successfully");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (testMethod != null) {
            log.info("===== FINISHED TEST: {} =====", testMethod.getName());
        }
        //   DriverManager.quitDriver();
    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    protected LoginPageSteps onLoginPage() {
        return new LoginPageSteps(getDriver());
    }

    protected HomePageSteps onHomePage() {
        return new HomePageSteps(getDriver());
    }

    protected CustomerRegisterPageSteps onRegisterPage() {
        return new CustomerRegisterPageSteps(getDriver());
    }

    protected ContactPageSteps onContactPage() {
        return new ContactPageSteps(getDriver());
    }

    protected CustomerAccountPageSteps onAccountPage() {
        return new CustomerAccountPageSteps(getDriver());
    }

}
