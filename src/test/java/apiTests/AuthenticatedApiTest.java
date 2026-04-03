package apiTests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

/**
 * Base for API tests that require authentication.
 * Extends BaseApiTest (gets baseURI + logging), then adds JWT Bearer token.
 * Tests for protected endpoints (users/me, favorites, invoices etc.) extend this.
 */
public class AuthenticatedApiTest extends BaseApiTest {

    @BeforeClass(alwaysRun = true, dependsOnMethods = "setupApi")
    public void setupAuth() {
        String apiEmail = prop.getProperty("api.user.email");
        String apiPassword = prop.getProperty("api.user.password");

        log.info("Authenticating API user: {}", apiEmail);

        String token = given()
                .relaxedHTTPSValidation()
                .contentType("application/json")
                .body("{\"email\":\"" + apiEmail + "\",\"password\":\"" + apiPassword + "\"}")
                .post("/users/login")
                .then()
                .statusCode(200)
                .extract()
                .path("access_token");

        // Override default spec to include Bearer token
        RestAssured.requestSpecification = given()
                .relaxedHTTPSValidation()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token);

        log.info("Authentication successful. Bearer token set.");
    }
}
