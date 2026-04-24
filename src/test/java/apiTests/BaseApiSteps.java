package apiTests;

import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;

public class BaseApiSteps extends BaseApiTest {
    /**
     * Asserts HTTP status code and logs the full response body on every call.
     */
    protected static void assertStatusCode(Response response, int expected) {
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
    protected static void assertFieldNotEmpty(Response response, String fieldPath) {
        String value = response.jsonPath().getString(fieldPath);
        Assert.assertNotNull(value, "Field '" + fieldPath + "' should not be null");
        Assert.assertFalse(value.isEmpty(), "Field '" + fieldPath + "' should not be empty");
    }

    /**
     * Asserts a field equals the expected value.
     */
    protected static void assertFieldEquals(Response response, String fieldPath, String expected) {
        String actual = response.jsonPath().getString(fieldPath);
        Assert.assertEquals(actual, expected,
                "Field '" + fieldPath + "' mismatch."
                        + "\n  Expected : " + expected
                        + "\n  Actual   : " + actual);
    }

    /**
     * Asserts a list field is not null and not empty.
     */
    protected static void assertListNotEmpty(Response response, String listPath) {
        List<?> list = response.jsonPath().getList(listPath);
        Assert.assertNotNull(list, "List '" + listPath + "' should not be null");
        Assert.assertFalse(list.isEmpty(), "List '" + listPath + "' should not be empty");
    }

    /**
     * Asserts a list of floats is sorted in ascending order.
     */
    protected static void assertSortedAsc(List<Float> values, String fieldName) {
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
    protected static void assertSortedDesc(List<String> values, String fieldName) {
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