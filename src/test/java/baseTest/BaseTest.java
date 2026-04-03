package baseTest;

import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import steps.*;
import webDriverManager.DriverManager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class BaseTest {
    protected static final Logger log = LogManager.getLogger(BaseTest.class);
    protected Properties prop;
    protected Method testMethod;

    @BeforeClass(enabled = false)
    public void setupRestAssured() throws IOException {
        prop = DriverManager.loadProperties();

        String apiBaseUrl = prop.getProperty("api.base.url");
        String apiEmail = prop.getProperty("api.user.email");
        String apiPassword = prop.getProperty("api.user.password");

        RestAssured.baseURI = apiBaseUrl;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        // Obtain JWT token via POST /users/login
        String token = given()
                .relaxedHTTPSValidation()
                .contentType("application/json")
                .body("{\"email\":\"" + apiEmail + "\",\"password\":\"" + apiPassword + "\"}")
                .post("/users/login")
                .then()
                .statusCode(200)
                .extract()
                .path("access_token");

        // Set Bearer token as default for all subsequent requests
        RestAssured.requestSpecification = given()
                .relaxedHTTPSValidation()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token);

        log.info("REST Assured configured. Base URI: {}", apiBaseUrl);
    }

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
    public void tearDown(ITestResult result) {
        log.info("===== FINISHED TEST: {} =====", result.getMethod().getMethodName());
        DriverManager.quitDriver();
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
