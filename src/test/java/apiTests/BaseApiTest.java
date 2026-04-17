package apiTests;

import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import webDriverManager.DriverManager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class BaseApiTest {

    protected static final Logger log = LogManager.getLogger(BaseApiTest.class);
    protected Properties prop;
    protected Method testMethod;

    public String apiUserEmail;
    public String apiUserPassword;
    public String apiResetEmail;
    public String apiWrongLoginPass;

    @BeforeClass(alwaysRun = true)
    public void setupApi() throws IOException {
        prop = DriverManager.loadApiProperties();

        apiUserEmail = prop.getProperty("api.user.email");
        apiUserPassword = prop.getProperty("api.user.password");
        apiResetEmail = prop.getProperty("api.user.reset.email");
        apiWrongLoginPass = prop.getProperty("api.user.wrong.password");

        RestAssured.baseURI = prop.getProperty("api.base.url");
        RestAssured.requestSpecification = given()
                .relaxedHTTPSValidation()
                .contentType("application/json");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        log.info("API configured. Base URI: {}", RestAssured.baseURI);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        this.testMethod = method;
        log.info("===== STARTING API TEST: {} =====", method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        log.info("===== FINISHED API TEST: {} =====", result.getMethod().getMethodName());
    }

}