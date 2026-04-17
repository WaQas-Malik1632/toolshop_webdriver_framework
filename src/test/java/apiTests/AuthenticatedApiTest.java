package apiTests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.given;

public class AuthenticatedApiTest extends BaseApiTest {

    public String apiUserEmail;
    public String apiUserPassword;
    public String apiResetEmail;
    public String apiWrongLoginPass;

    @BeforeClass(alwaysRun = true, dependsOnMethods = "setupApi")
    public void setupAuth() {
        apiUserEmail = prop.getProperty("api.user.email");
        apiUserPassword = prop.getProperty("api.user.password");
        apiResetEmail = prop.getProperty("api.user.reset.email");
        apiWrongLoginPass = prop.getProperty("api.user.wrong.password");

        log.info("Authenticating API user: {}", apiUserEmail);

        String token = given()
                .relaxedHTTPSValidation()
                .contentType("application/json")
                .body("{\"email\":\"" + apiUserEmail + "\",\"password\":\"" + apiUserPassword + "\"}")
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

        log.info("Authentication successful-> Bearer token set");
    }
}