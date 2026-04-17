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

public class ProductApiTest extends BaseApiTest {

    @Test(priority = 1, groups = {"smoke", "e2e"})
    @Story("Products")
    @Severity(SeverityLevel.CRITICAL)
    @Description("GET /products - returns paginated product list")
    public void getAllProductsReturns200() {
        log.info("ACTION: GET /products");

        Response response = given()
                .when()
                .get("/products")
                .then()
                .extract()
                .response();

        apiBaseSteps.assertStatusCode(response, 200);
        apiBaseSteps.assertListNotEmpty(response, "data");
        Assert.assertEquals(response.jsonPath().getInt("current_page"), 1, "current_page should be 1");
        Assert.assertTrue(response.jsonPath().getInt("total") > 0, "total should be > 0");
        log.info("RESULT: total={} | current_page={}", response.jsonPath().getInt("total"), response.jsonPath().getInt("current_page"));
    }

    @Test(priority = 2, groups = {"e2e"})
    @Story("Products")
    @Severity(SeverityLevel.NORMAL)
    @Description("GET /products?sort=price,asc - products sorted by price ascending")
    public void getProductsSortedByPriceAsc() {
        log.info("ACTION: GET /products?sort=price,asc");

        Response response = given()
                .queryParam("sort", "price,asc")
                .when()
                .get("/products")
                .then()
                .extract()
                .response();

        apiBaseSteps.assertStatusCode(response, 200);
        apiBaseSteps.assertSortedAsc(response.jsonPath().getList("data.price", Float.class), "price");
    }

    @Test(priority = 3, groups = {"e2e"})
    @Story("Products")
    @Severity(SeverityLevel.NORMAL)
    @Description("GET /products?sort=name,desc - products sorted by name descending")
    public void getProductsSortedByNameDsc() {
        log.info("ACTION: GET /products?sort=name,desc");

        Response response = given()
                .queryParam("sort", "name,desc")
                .when()
                .get("/products")
                .then()
                .extract()
                .response();

        apiBaseSteps.assertStatusCode(response, 200);
        apiBaseSteps.assertSortedDesc(response.jsonPath().getList("data.name", String.class), "name");
    }

    @Test(priority = 4, groups = {"smoke", "e2e"})
    @Story("Products")
    @Severity(SeverityLevel.NORMAL)
    @Description("GET /products/search?q=pliers - returns matching products")
    public void searchProductByName() {
        log.info("ACTION: GET /products/search?q=pliers");

        Response response = given()
                .queryParam("q", "pliers")
                .when()
                .get("/products/search")
                .then()
                .extract()
                .response();

        apiBaseSteps.assertStatusCode(response, 200);
        apiBaseSteps.assertListNotEmpty(response, "data");
        List<String> names = response.jsonPath().getList("data.name", String.class);
        Assert.assertTrue(names.get(0).contains("Pliers"),
                "First result should contain 'Pliers' but was: " + names.get(0));
        log.info("RESULT: {} results | first='{}'", names.size(), names.get(0));
    }

    @Test(priority = 5, groups = {"e2e"})
    @Story("Products")
    @Severity(SeverityLevel.NORMAL)
    @Description("GET /products/search?q=zzznoresult - returns empty list for unknown query")
    public void searchProductWithNoMatchReturnsEmpty() {
        log.info("ACTION: GET /products/search?q=zzznoresult");

        Response response = given()
                .queryParam("q", "zzznoresult")
                .when()
                .get("/products/search")
                .then()
                .extract()
                .response();

        apiBaseSteps.assertStatusCode(response, 200);
        List<?> data = response.jsonPath().getList("data");
        Assert.assertTrue(data.isEmpty(), "Expected empty data but got " + data.size() + " results");
        log.info("RESULT: No products found - as expected");
    }
}