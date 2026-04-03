package apiTests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import webDriverManager.DriverManager;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import static io.restassured.RestAssured.given;

/**
 * Base for ALL API tests.
 * Sets baseURI, logging, and shared assertion utilities.
 * Public endpoint tests extend this directly.
 * Tests requiring auth extend AuthenticatedApiTest.
 */
public class BaseApiTest {

    protected static final Logger log = LogManager.getLogger(BaseApiTest.class);
    protected Properties prop;
    protected Method testMethod;

    @BeforeClass(alwaysRun = true)
    public void setupApi() throws IOException {
        prop = DriverManager.loadApiProperties();

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

    // ─── Shared Assertion Utilities ──────────────────────────────────────────

    /**
     * Asserts HTTP status code and logs the full response body on every call.
     */
    protected void assertStatusCode(Response response, int expected) {
        int actual = response.statusCode();
        log.info("RESPONSE [{}] : {}", actual, response.body().asString());
        Assert.assertEquals(actual, expected,
                "Unexpected status code."
                        + "\n  Expected : " + expected
                        + "\n  Actual   : " + actual
                        + "\n  Body     : " + response.body().asString());
    }

    /**
     * Asserts a string field from the response is not null and not empty.
     */
    protected void assertFieldNotEmpty(Response response, String fieldPath) {
        String value = response.jsonPath().getString(fieldPath);
        Assert.assertNotNull(value, "Field '" + fieldPath + "' should not be null");
        Assert.assertFalse(value.isEmpty(), "Field '" + fieldPath + "' should not be empty");
    }

    /**
     * Asserts a field equals the expected value.
     */
    protected void assertFieldEquals(Response response, String fieldPath, String expected) {
        String actual = response.jsonPath().getString(fieldPath);
        Assert.assertEquals(actual, expected,
                "Field '" + fieldPath + "' mismatch."
                        + "\n  Expected : " + expected
                        + "\n  Actual   : " + actual);
    }

    /**
     * Asserts a list field is not null and not empty.
     */
    protected void assertListNotEmpty(Response response, String listPath) {
        List<?> list = response.jsonPath().getList(listPath);
        Assert.assertNotNull(list, "List '" + listPath + "' should not be null");
        Assert.assertFalse(list.isEmpty(), "List '" + listPath + "' should not be empty");
    }

    /**
     * Asserts a list of floats is sorted in ascending order.
     */
    protected void assertSortedAsc(List<Float> values, String fieldName) {
        Assert.assertFalse(values.isEmpty(), fieldName + " list should not be empty");
        for (int i = 0; i < values.size() - 1; i++) {
            Assert.assertTrue(values.get(i) <= values.get(i + 1),
                    fieldName + " not sorted asc at index " + i
                            + ": " + values.get(i) + " > " + values.get(i + 1));
        }
        log.info("ASSERT: {} sorted asc verified | first={} last={}",
                fieldName, values.get(0), values.get(values.size() - 1));
    }

    /**
     * Asserts a list of strings is sorted in descending order (case-insensitive).
     */
    protected void assertSortedDesc(List<String> values, String fieldName) {
        Assert.assertFalse(values.isEmpty(), fieldName + " list should not be empty");
        for (int i = 0; i < values.size() - 1; i++) {
            Assert.assertTrue(values.get(i).compareToIgnoreCase(values.get(i + 1)) >= 0,
                    fieldName + " not sorted desc at index " + i
                            + ": '" + values.get(i) + "' < '" + values.get(i + 1) + "'");
        }
        log.info("ASSERT: {} sorted desc verified | first='{}' last='{}'",
                fieldName, values.get(0), values.get(values.size() - 1));
    }
}
