package apiTest;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BrandApiTest extends BaseApiTest {

    @Test(priority = 1, groups = {"smoke", "e2e"})
    @Story("Brands")
    @Severity(SeverityLevel.NORMAL)
    @Description("GET /brands - returns list of all brands")
    public void getAllBrandsReturns200() {
        log.info("ACTION: GET /brands");

        Response response = given()
                .when()
                .get("/brands")
                .then()
                .statusCode(200)
                .body("$", not(empty()))
                .body("[0].id", notNullValue())
                .body("[0].name", notNullValue())
                .body("[0].slug", notNullValue())
                .extract().response();

        log.info("RESULT: {} brand(s) retrieved | first='{}'",
                response.jsonPath().getList("$").size(), response.path("[0].name"));
    }

    @Test(priority = 2, groups = {"e2e"})
    @Story("Brands")
    @Severity(SeverityLevel.NORMAL)
    @Description("GET /brands/search?q=forge - returns matching brands")
    public void searchBrandByName() {
        log.info("ACTION: GET /brands/search?q=forge");

        Response response = given()
                .queryParam("q", "forge")
                .when()
                .get("/brands/search")
                .then()
                .statusCode(200)
                .body("$", not(empty()))
                .body("[0].name", containsString("ForgeFlex"))
                .extract().response();

        log.info("RESULT: Brand search returned match | name='{}'", (Object) response.path("[0].name"));
    }

    @Test(priority = 3, groups = {"e2e"})
    @Story("Brands")
    @Severity(SeverityLevel.NORMAL)
    @Description("GET /brands/{id} - returns 404 for non-existent brand")
    public void getNonExistentBrandReturns404() {
        log.info("ACTION: GET /brands/non-existent-id-99999");

        Response response = given()
                .when()
                .get("/brands/non-existent-id-99999")
                .then()
                .statusCode(404)
                .extract().response();

        log.info("RESULT: 404 correctly returned for non-existent brand | status={}", response.statusCode());
    }
}
