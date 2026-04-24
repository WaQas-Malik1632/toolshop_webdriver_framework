package apiTests;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

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
                .extract()
                .response();

        BaseApiSteps.assertStatusCode(response, 200);
        BaseApiSteps.assertListNotEmpty(response, "$");
        BaseApiSteps.assertFieldNotEmpty(response, "[0].id");
        BaseApiSteps.assertFieldNotEmpty(response, "[0].name");
        BaseApiSteps.assertFieldNotEmpty(response, "[0].slug");
        log.info("RESULT: {} brand(s) | first='{}'",
                response.jsonPath().getList("$").size(), response.jsonPath().getString("[0].name"));
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
                .extract()
                .response();

        BaseApiSteps.assertStatusCode(response, 200);
        BaseApiSteps.assertListNotEmpty(response, "$");
        List<String> names = response.jsonPath().getList("name", String.class);
        Assert.assertTrue(names.get(0).contains("ForgeFlex"),
                "First brand should contain 'ForgeFlex' but was: " + names.get(0));
        log.info("RESULT: Brand match | name='{}'", names.get(0));
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
                .extract()
                .response();

        BaseApiSteps.assertStatusCode(response, 404);
        log.info("RESULT: 404 correctly returned");
    }
}