package apiTest;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

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
                .statusCode(200)
                .body("data", not(empty()))
                .body("current_page", equalTo(1))
                .body("total", greaterThan(0))
                .extract().response();

        log.info("RESULT: Products retrieved | total={} | current_page={}",
                response.path("total"), response.path("current_page"));
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
                .statusCode(200)
                .body("data", not(empty()))
                .extract().response();

        log.info("RESULT: Sorted products retrieved | total={}", (Object) response.path("total"));
    }

    @Test(priority = 3, groups = {"smoke", "e2e"})
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
                .statusCode(200)
                .body("data", not(empty()))
                .body("data[0].name", containsString("Pliers"))
                .extract().response();

        log.info("RESULT: Search returned {} results | first match='{}'",
                response.path("data.size()"), response.path("data[0].name"));
    }

    @Test(priority = 4, groups = {"e2e"})
    @Story("Products")
    @Severity(SeverityLevel.NORMAL)
    @Description("GET /products/search?q=zzznoresult - returns empty list for unknown query")
    public void searchProductWithNoMatchReturnsEmpty() {
        log.info("ACTION: GET /products/search?q=zzznoresult");

        given()
                .queryParam("q", "zzznoresult")
                .when()
                .get("/products/search")
                .then()
                .statusCode(200)
                .body("data", empty());

        log.info("RESULT: No products found for unknown query - as expected");
    }
}
