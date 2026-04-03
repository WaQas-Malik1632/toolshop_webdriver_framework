package apiTests;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Feature("Customer Registration API")
public class CustomerRegisterApiTest extends BaseApiTest {

    private static final String UNIQUE_EMAIL = "apitester_" + System.currentTimeMillis() + "@yopmail.com";
    private static final String VALID_PASSWORD = "Toolshopuser@1";

    @Test(priority = 1, groups = {"smoke", "e2e"})
    @Story("Register - Positive Flow")
    @Severity(SeverityLevel.BLOCKER)
    @Description("POST /users/register - new customer registers successfully with valid data, returns 201")
    public void registerNewCustomerSuccessfully() {
        log.info("ACTION: POST /users/register | email: {}", UNIQUE_EMAIL);

        Response response = given()
                .body("{"
                        + "\"first_name\":\"ApiTest\","
                        + "\"last_name\":\"User\","
                        + "\"email\":\"" + UNIQUE_EMAIL + "\","
                        + "\"password\":\"" + VALID_PASSWORD + "\","
                        + "\"phone\":\"0987654321\","
                        + "\"dob\":\"1990-01-15\","
                        + "\"address\":{"
                        + "\"street\":\"Test Street 1\","
                        + "\"city\":\"Vienna\","
                        + "\"state\":\"Vienna\","
                        + "\"country\":\"Austria\","
                        + "\"postal_code\":\"1010\""
                        + "}}"
                )
                .when()
                .post("/users/register")
                .then()
                .extract().response();

        assertStatusCode(response, 201);
        Assert.assertNotNull(response.path("id"), "id should not be null");
        Assert.assertEquals(response.path("first_name"), "ApiTest", "first_name mismatch");
        Assert.assertEquals(response.path("last_name"), "User", "last_name mismatch");
        Assert.assertEquals(response.path("email"), UNIQUE_EMAIL, "email mismatch");

        log.info("RESULT: Customer registered | id={} | email={}",
                (Object) response.path("id"), (Object) response.path("email"));
    }

    @Test(priority = 2, groups = {"smoke", "e2e"})
    @Story("Register - Positive Flow")
    @Severity(SeverityLevel.NORMAL)
    @Description("POST /users/register - minimal required fields only, returns 201")
    public void registerCustomerWithRequiredFieldsOnly() {
        String minimalEmail = "api_minimal_" + System.currentTimeMillis() + "@yopmail.com";
        log.info("ACTION: POST /users/register minimal | email: {}", minimalEmail);

        Response response = given()
                .body("{"
                        + "\"first_name\":\"Minimal\","
                        + "\"last_name\":\"User\","
                        + "\"email\":\"" + minimalEmail + "\","
                        + "\"password\":\"" + VALID_PASSWORD + "\""
                        + "}"
                )
                .when()
                .post("/users/register")
                .then()
                .extract().response();

        assertStatusCode(response, 201);
        Assert.assertNotNull(response.path("id"), "id should not be null");
        Assert.assertEquals(response.path("email"), minimalEmail, "email mismatch");

        log.info("RESULT: Minimal registration successful | id={}", (Object) response.path("id"));
    }

    @Test(priority = 3, groups = {"smoke", "e2e"})
    @Story("Register - Duplicate Email")
    @Severity(SeverityLevel.CRITICAL)
    @Description("POST /users/register - duplicate email returns 422")
    public void registerWithDuplicateEmailReturns422() {
        String existingEmail = prop.getProperty("api.user.email");
        log.info("ACTION: POST /users/register with existing email: {}", existingEmail);

        Response response = given()
                .body("{"
                        + "\"first_name\":\"Duplicate\","
                        + "\"last_name\":\"User\","
                        + "\"email\":\"" + existingEmail + "\","
                        + "\"password\":\"" + VALID_PASSWORD + "\""
                        + "}"
                )
                .when()
                .post("/users/register")
                .then()
                .extract().response();

        assertStatusCode(response, 422);
        log.info("RESULT: Duplicate email correctly rejected | body: {}", response.body().asString());
    }

    @Test(priority = 4, groups = {"e2e"})
    @Story("Register - Missing Required Fields")
    @Severity(SeverityLevel.NORMAL)
    @Description("POST /users/register - missing email returns 422")
    public void registerWithMissingEmailReturns422() {
        log.info("ACTION: POST /users/register without email");

        Response response = given()
                .body("{"
                        + "\"first_name\":\"NoEmail\","
                        + "\"last_name\":\"User\","
                        + "\"password\":\"" + VALID_PASSWORD + "\""
                        + "}"
                )
                .when()
                .post("/users/register")
                .then()
                .extract().response();

        assertStatusCode(response, 422);
        log.info("RESULT: Missing email correctly rejected | body: {}", response.body().asString());
    }

    @Test(priority = 5, groups = {"e2e"})
    @Story("Register - Missing Required Fields")
    @Severity(SeverityLevel.NORMAL)
    @Description("POST /users/register - missing password returns 422")
    public void registerWithMissingPasswordReturns422() {
        String email = "nopass_" + System.currentTimeMillis() + "@yopmail.com";
        log.info("ACTION: POST /users/register without password | email: {}", email);

        Response response = given()
                .body("{"
                        + "\"first_name\":\"NoPass\","
                        + "\"last_name\":\"User\","
                        + "\"email\":\"" + email + "\""
                        + "}"
                )
                .when()
                .post("/users/register")
                .then()
                .extract().response();

        assertStatusCode(response, 422);
        log.info("RESULT: Missing password correctly rejected | body: {}", response.body().asString());
    }

    @Test(priority = 6, groups = {"e2e"})
    @Story("Register - Invalid Data")
    @Severity(SeverityLevel.NORMAL)
    @Description("POST /users/register - invalid email format returns 422")
    public void registerWithInvalidEmailFormatReturns422() {
        log.info("ACTION: POST /users/register with invalid email format");

        Response response = given()
                .body("{"
                        + "\"first_name\":\"Invalid\","
                        + "\"last_name\":\"Email\","
                        + "\"email\":\"not-an-email\","
                        + "\"password\":\"" + VALID_PASSWORD + "\""
                        + "}"
                )
                .when()
                .post("/users/register")
                .then()
                .extract().response();

        assertStatusCode(response, 422);
        log.info("RESULT: Invalid email format correctly rejected | body: {}", response.body().asString());
    }

    @Test(priority = 7, groups = {"e2e"})
    @Story("Register - Invalid Data")
    @Severity(SeverityLevel.NORMAL)
    @Description("POST /users/register - weak password returns 422")
    public void registerWithWeakPasswordReturns422() {
        String email = "weakpass_" + System.currentTimeMillis() + "@yopmail.com";
        log.info("ACTION: POST /users/register with weak password | email: {}", email);

        Response response = given()
                .body("{"
                        + "\"first_name\":\"Weak\","
                        + "\"last_name\":\"Pass\","
                        + "\"email\":\"" + email + "\","
                        + "\"password\":\"12345678\""
                        + "}"
                )
                .when()
                .post("/users/register")
                .then()
                .extract().response();

        assertStatusCode(response, 422);
        log.info("RESULT: Weak password correctly rejected | body: {}", response.body().asString());
    }

    @Test(priority = 8, groups = {"e2e"})
    @Story("Register - Invalid Data")
    @Severity(SeverityLevel.NORMAL)
    @Description("POST /users/register - dob under 18 years returns 422")
    public void registerWithUnderAgeDobReturns422() {
        String email = "young_" + System.currentTimeMillis() + "@yopmail.com";
        log.info("ACTION: POST /users/register with under-age dob | email: {}", email);

        Response response = given()
                .body("{"
                        + "\"first_name\":\"Young\","
                        + "\"last_name\":\"User\","
                        + "\"email\":\"" + email + "\","
                        + "\"password\":\"" + VALID_PASSWORD + "\","
                        + "\"dob\":\"2020-01-01\""
                        + "}"
                )
                .when()
                .post("/users/register")
                .then()
                .extract().response();

        assertStatusCode(response, 422);
        log.info("RESULT: Under-age dob correctly rejected | body: {}", response.body().asString());
    }
}
