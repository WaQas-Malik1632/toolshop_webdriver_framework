package apiTest;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UserApiTest extends BaseApiTest {

    @Test(priority = 1, groups = {"smoke", "e2e"})
    @Story("User Login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("POST /users/login - valid credentials return access_token")
    public void loginWithValidCredentials() {
        log.info("ACTION: POST /users/login with valid credentials");

        Response response = given()
                .body("{\"email\":\"customer@practicesoftwaretesting.com\",\"password\":\"welcome01\"}")
                .when()
                .post("/users/login")
                .then()
                .statusCode(200)
                .body("access_token", notNullValue())
                .body("token_type", equalTo("bearer"))
                .extract().response();

        log.info("RESULT: Login successful | token_type={} | expires_in={}",
                response.path("token_type"), response.path("expires_in"));
    }

    @Test(priority = 2, groups = {"smoke", "e2e"})
    @Story("User Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("POST /users/login - invalid credentials return 401")
    public void loginWithInvalidCredentials() {
        log.info("ACTION: POST /users/login with invalid credentials");

        Response response = given()
                .body("{\"email\":\"customer@practicesoftwaretesting.com\",\"password\":\"wrongpassword\"}")
                .when()
                .post("/users/login")
                .then()
                .statusCode(401)
                .extract().response();

        log.info("RESULT: Login correctly rejected | status={}", response.statusCode());
    }

    @Test(priority = 3, groups = {"smoke", "e2e"})
    @Story("User Profile")
    @Severity(SeverityLevel.CRITICAL)
    @Description("GET /users/me - returns authenticated user info")
    public void getAuthenticatedUserProfile() {
        log.info("ACTION: GET /users/me with valid token");

        Response response = given()
                .when()
                .get("/users/me")
                .then()
                .statusCode(200)
                .body("email", equalTo("customer@practicesoftwaretesting.com"))
                .body("first_name", notNullValue())
                .body("last_name", notNullValue())
                .extract().response();

        log.info("RESULT: Profile retrieved | email={} | name={} {}",
                response.path("email"), response.path("first_name"), response.path("last_name"));
    }

    @Test(priority = 4, groups = {"e2e"})
    @Story("User Profile")
    @Severity(SeverityLevel.NORMAL)
    @Description("GET /users/me - returns 401 without token")
    public void getProfileWithoutTokenReturns401() {
        log.info("ACTION: GET /users/me without Authorization token");

        Response response = given()
                .spec(new RequestSpecBuilder()
                        .setBaseUri(prop.getProperty("api.base.url"))
                        .setContentType("application/json")
                        .build())
                .when()
                .get("/users/me")
                .then()
                .statusCode(401)
                .extract().response();

        log.info("RESULT: Unauthorized correctly returned | status={}", response.statusCode());
    }
}
