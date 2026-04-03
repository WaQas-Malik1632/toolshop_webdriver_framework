package apiTest;

import apiTest.BaseApiTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserApiTest extends BaseApiTest {

    @Test(priority = 1, groups = {"smoke", "e2e"})
    @Story("User Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("POST /users/login - valid credentials return access_token")
    public void loginWithValidCredentials() {
        given()
            .body("{\"email\":\"customer@practicesoftwaretesting.com\",\"password\":\"welcome01\"}")
        .when()
            .post("/users/login")
        .then()
            .statusCode(200)
            .body("access_token", notNullValue())
            .body("token_type", equalTo("bearer"));
    }

    @Test(priority = 2, groups = {"smoke", "e2e"})
    @Story("User Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("POST /users/login - invalid credentials return 401")
    public void loginWithInvalidCredentials() {
        given()
            .body("{\"email\":\"customer@practicesoftwaretesting.com\",\"password\":\"wrongpassword\"}")
        .when()
            .post("/users/login")
        .then()
            .statusCode(401);
    }

    @Test(priority = 3, groups = {"smoke", "e2e"})
    @Story("User Profile")
    @Severity(SeverityLevel.CRITICAL)
    @Description("GET /users/me - returns authenticated user info")
    public void getAuthenticatedUserProfile() {
        given()
        .when()
            .get("/users/me")
        .then()
            .statusCode(200)
            .body("email", equalTo("customer@practicesoftwaretesting.com"))
            .body("first_name", notNullValue())
            .body("last_name", notNullValue());
    }

    @Test(priority = 4, groups = {"e2e"})
    @Story("User Profile")
    @Severity(SeverityLevel.NORMAL)
    @Description("GET /users/me - returns 401 without token")
    public void getProfileWithoutTokenReturns401() {
        // Reset default spec to send request with no Authorization header
        given()
            .spec(new RequestSpecBuilder()
                .setBaseUri(prop.getProperty("api.base.url"))
                .setContentType("application/json")
                .build())
        .when()
            .get("/users/me")
        .then()
            .statusCode(401);
    }
}
