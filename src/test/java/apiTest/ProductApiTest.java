package apiTest;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProductApiTest extends BaseApiTest {

    @Test(priority = 1, groups = {"smoke", "e2e"})
    @Story("Products")
    @Severity(SeverityLevel.CRITICAL)
    @Description("GET /products - returns paginated product list")
    public void getAllProductsReturns200() {
        given()
        .when()
            .get("/products")
        .then()
            .statusCode(200)
            .body("data", not(empty()))
            .body("current_page", equalTo(1))
            .body("total", greaterThan(0));
    }

    @Test(priority = 2, groups = {"e2e"})
    @Story("Products")
    @Severity(SeverityLevel.NORMAL)
    @Description("GET /products?sort=price,asc - products sorted by price ascending")
    public void getProductsSortedByPriceAsc() {
        given()
            .queryParam("sort", "price,asc")
        .when()
            .get("/products")
        .then()
            .statusCode(200)
            .body("data", not(empty()));
    }

    @Test(priority = 3, groups = {"smoke", "e2e"})
    @Story("Products")
    @Severity(SeverityLevel.NORMAL)
    @Description("GET /products/search?q=pliers - returns matching products")
    public void searchProductByName() {
        given()
            .queryParam("q", "pliers")
        .when()
            .get("/products/search")
        .then()
            .statusCode(200)
            .body("data", not(empty()))
            .body("data[0].name", containsString("Pliers"));
    }

    @Test(priority = 4, groups = {"e2e"})
    @Story("Products")
    @Severity(SeverityLevel.NORMAL)
    @Description("GET /products/search?q=zzznoresult - returns empty list for unknown query")
    public void searchProductWithNoMatchReturnsEmpty() {
        given()
            .queryParam("q", "zzznoresult")
        .when()
            .get("/products/search")
        .then()
            .statusCode(200)
            .body("data", empty());
    }
}
