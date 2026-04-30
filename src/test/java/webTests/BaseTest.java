package webTests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import steps.*;
import webDriverManager.DriverManager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

public class BaseTest {
    protected static final Logger log = LogManager.getLogger(BaseTest.class);
    public Properties prop;
    protected Method testMethod;

    public String browserName;
    public String appBaseUrl;

    public String loginUserEmail;
    public String loginUserPass;
    public String loginWrongPass;
    public String resetWithUnRegisteredEmail;
    public String resetWithRegisteredEmail;

    public String userCurrentPass;
    public String userNewPass;
    public String userNewConfirmPass;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, org.testng.ITestContext context) throws IOException {
        this.testMethod = method;
        log.info("===== STARTING TEST: {} =====", method.getName());

        prop = DriverManager.loadProperties();
        // Check if browser parameter is passed from TestNG suite XML, otherwise use config file
        String testBrowser = context.getCurrentXmlTest().getParameter("browser");
        browserName = (testBrowser != null && !testBrowser.isEmpty()) ? testBrowser : prop.getProperty("browser");
        appBaseUrl = prop.getProperty("app.base.url");

        loginUserEmail = prop.getProperty("user.login.email");
        loginUserPass = prop.getProperty("user.login.password");
        loginWrongPass = prop.getProperty("user.login.wrong.password");
        resetWithUnRegisteredEmail = prop.getProperty("user.reset.unregistered.email");
        resetWithRegisteredEmail = prop.getProperty("user.reset.registered.email");

        userCurrentPass = prop.getProperty("user.change.current.password");
        userNewPass = prop.getProperty("user.change.new.password");
        userNewConfirmPass = prop.getProperty("user.change.confirm.password");

        DriverManager.initializeDriver(browserName, appBaseUrl);

        log.info("Browser initialized successfully: {}", browserName);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        log.info("===== FINISHED TEST: {} =====", result.getMethod().getMethodName());
        // DriverManager.quitDriver();
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