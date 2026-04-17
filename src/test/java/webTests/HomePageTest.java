package webTests;

import constants.HomePageConstants;
import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Feature("Home Page")
@Listeners({AllureTestNg.class, TestListener.class})
public class HomePageTest extends BaseTest {

    @Test(priority = 1, enabled = true, groups = {"smoke", "e2e"})
    @Story("Validate Home page logo is visible")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that Home page logo is visible and clickable")
    public void isHomePageLogoVisibleTest() {
        log.info("Executing test: {}", testMethod.getName());

        boolean actualLogoVisible = onHomePage()
                .isHomePageLogoVisible();

        Assert.assertTrue(actualLogoVisible, "Logo is not visible");
        log.info("Home Page Logo visible: {}", actualLogoVisible);
    }

    @Test(priority = 2, enabled = true, groups = {"e2e"})
    @Story("Validate Customer can purchase multiple products with Credit Card")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that customer can purchase multiple products in single order with Credit Card successfully")
    public void validateUserCanSelectAndPurchaseMultipleProductsInSingleOrder() {
        log.info("Executing test: {}", testMethod.getName());

        // Pre-condition: Login and navigate to home page
        onLoginPage()
                .navigateToLoginPage()
                .logInAs(loginUserEmail, loginUserPass)
                .navigateToHomePage();

        String actualMessage = onHomePage()
                .purchaseMultipleProductsWithCreditCard(
                        "5655-6565-7874-7875",
                        "12/2030",
                        "587",
                        "Tester User");

        Assert.assertEquals(actualMessage, HomePageConstants.PRODUCT_PURCHASE_PAYMENT_SUCCESS_MESSAGE,
                "Payment was not successful");
    }

    @Test(priority = 3, enabled = true, groups = {"e2e"})
    @Story("Validate Customer can purchase any product with Bank Account")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that customer can purchase product successfully with Bank Transfer")
    public void validateCustomerCanPurchaseItemSuccessfully() {
        log.info("Executing test: {}", testMethod.getName());

        // Pre-condition: Login and navigate to home page
        onLoginPage()
                .navigateToLoginPage()
                .logInAs(loginUserEmail, loginUserPass)
                .navigateToHomePage();

        // Test execution: Purchase item with bank transfer
        String actualMessage = onHomePage()
                .purchaseItemWithBankTransfer("Test Bank", "Test user", "1015112101512");

        Assert.assertEquals(actualMessage, HomePageConstants.PRODUCT_PURCHASE_PAYMENT_SUCCESS_MESSAGE,
                "Payment was not successful");
    }

    @Test(priority = 4, enabled = true, groups = {"e2e"})
    @Story("Validate guest user can purchase any product with COD")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that guest user can purchase product successfully with Cash on Delivery")
    public void validateWhetherGuestUserCanPurchaseItemSuccessfully() {
        log.info("Executing test: {}", testMethod.getName());

        // Test execution: Purchase as guest with COD (no login required)
        String actualMessage = onHomePage()
                .purchaseItemFlowAsGuestWithCod(
                        "guest@example.com",
                        "Tester",
                        "viki",
                        "123 Test St",
                        "Berlin",
                        "Berlin",
                        "Germany",
                        "10115");

        Assert.assertEquals(actualMessage, HomePageConstants.PRODUCT_PURCHASE_PAYMENT_SUCCESS_MESSAGE,
                "Payment was not successful");
    }
}