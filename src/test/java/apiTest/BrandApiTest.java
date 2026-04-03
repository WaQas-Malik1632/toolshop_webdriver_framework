package apiTest;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BrandApiTest extends BaseApiTest {

    @Test(priority = 1, groups = {"smoke", "e2e"})
    @Story("Brands")
    @Severity(SeverityLevel.NORMAL)
    @Description("GET /brands - returns list of all brands")
    public void getAllBrandsReturns200() {
        given()
        .when()
            .get("/brands")
        .then()
            .statusCode(200)
            .body("$", not(empty()))
            .body("[0].id", notNullValue())
            .body("[0].name", notNullValue())
            .body("[0].slug", notNullValue());
    }

    @Test(priority = 2, groups = {"e2e"})
    @Story("Brands")
    @Severity(SeverityLevel.NORMAL)
    @Description("GET /brands/search?q=forge - returns matching brands")
    public void searchBrandByName() {
        given()
            .queryParam("q", "forge")
        .when()
            .get("/brands/search")
        .then()
            .statusCode(200)
            .body("$", not(empty()))
            .body("[0].name", containsString("ForgeFlex"));
    }

    @Test(priority = 3, groups = {"e2e"})
    @Story("Brands")
    @Severity(SeverityLevel.NORMAL)
    @Description("GET /brands/{id} - returns 404 for non-existent brand")
    public void getNonExistentBrandReturns404() {
        given()
        .when()
            .get("/brands/non-existent-id-99999")
        .then()
            .statusCode(404);
    }
}
