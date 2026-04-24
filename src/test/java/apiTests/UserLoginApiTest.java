package apiTests;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UserLoginApiTest extends AuthenticatedApiTest {

    @Test(priority = 1, groups = {"smoke", "e2e"})
    @Story("User Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("POST /users/login - valid credentials return access_token")
    public void loginWithValidCredentials() {

        log.info("ACTION: POST /users/login | email: {}", apiUserEmail);

        Response response = given()
                .body("{\"email\":\"" + apiUserEmail + "\",\"password\":\"" + apiUserPassword + "\"}")
                .when()
                .post("/users/login")
                .then()
                .extract()
                .response();

        BaseApiSteps.assertStatusCode(response, 200);
        BaseApiSteps.assertFieldNotEmpty(response, "access_token");
        BaseApiSteps.assertFieldEquals(response, "token_type", "bearer");
        Assert.assertTrue(response.jsonPath().getInt("expires_in") > 0,
                "expires_in should be > 0, was: " + response.jsonPath().getInt("expires_in"));
        log.info("RESULT: Login successful | token_type={} | expires_in={}",
                response.jsonPath().getString("token_type"), response.jsonPath().getInt("expires_in"));
    }

    @Test(priority = 2, groups = {"smoke", "e2e"})
    @Story("User Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("POST /users/login - invalid credentials return 401")
    public void loginWithInvalidCredentials() {

        log.info("ACTION: POST /users/login with wrong password | email: {}", apiUserEmail);

        Response response = given()
                .body("{\"email\":\"" + apiUserEmail + "\",\"password\":\"" + apiWrongLoginPass + "\"}")
                .when()
                .post("/users/login")
                .then()
                .extract()
                .response();

        BaseApiSteps.assertStatusCode(response, 401);
        log.info("RESULT: Login correctly rejected | body='{}'", response.body().asString());
    }

    @Test(priority = 3, groups = {"smoke", "e2e"})
    @Story("User Profile")
    @Severity(SeverityLevel.CRITICAL)
    @Description("GET /users/me - returns authenticated user info")
    public void getAuthenticatedUserProfile() {

        log.info("ACTION: GET /users/me | expected email: {}", apiUserEmail);

        Response response = given()
                .when()
                .get("/users/me")
                .then()
                .extract()
                .response();

        BaseApiSteps.assertStatusCode(response, 200);
        BaseApiSteps.assertFieldEquals(response, "email", apiUserEmail);
        BaseApiSteps.assertFieldNotEmpty(response, "first_name");
        BaseApiSteps.assertFieldNotEmpty(response, "last_name");
        log.info("RESULT: Profile retrieved | email={} | name={} {}",
                response.jsonPath().getString("email"),
                response.jsonPath().getString("first_name"),
                response.jsonPath().getString("last_name"));
    }

    @Test(priority = 4, groups = {"e2e"})
    @Story("Forgot Password")
    @Severity(SeverityLevel.CRITICAL)
    @Description("POST /users/forgot-password - registered email returns success")
    public void forgotPasswordWithRegisteredEmail() {
        log.info("ACTION: POST /users/forgot-password | email: {}", apiResetEmail);

        Response response = given()
                .body("{\"email\":\"" + apiResetEmail + "\"}")
                .when()
                .post("/users/forgot-password")
                .then()
                .extract()
                .response();

        BaseApiSteps.assertStatusCode(response, 200);
        boolean success = response.jsonPath().getBoolean("success");
        Assert.assertTrue(success, "Expected success=true for registered email but was: " + success);
        log.info("RESULT: Password reset successful | success={}", true);
    }

}