package webTests;

import constants.LoginPageConstants;
import constants.RegisterPageConstants;
import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import webTests.listeners.TestListener;

@Feature("Authentication")
@Listeners({AllureTestNg.class, TestListener.class})
public class CustomerRegisterTest extends BaseTest {

    private static final String UNIQUE_EMAIL = "tester_" + System.currentTimeMillis() + "@yopmail.com";
    private static final String VALID_PASSWORD = "Toolshopuser@1";

    private static final String EXISTING_EMAIL = "Tester@yopmail.com";
    private static final String PASSWORD = "welcome01@Pass";

    @Test(priority = 1, enabled = false, groups = {"smoke"})
    @Story("Validate Customer Register Page Title")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the customer register page title matches the expected value")
    public void validateCustomerRegisterPageTitle() {
        log.info("Executing test: {}", testMethod.getName());

        String title = onRegisterPage()
                .navigateToRegisterPage()
                .getCustomerRegisterPageTitle();

        log.info("Actual Register Page Title: {}", title);

        Assert.assertEquals(title, RegisterPageConstants.CUSTOMER_REGISTER_PAGE_TITLE,
                "Customer Register page title mismatch");
    }

    @Test(priority = 2, enabled = true, groups = {"smoke", "e2e"})
    @Story("Customer Registration - Positive Flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that new customer can register successfully with unique email and valid details")
    public void validateCustomerCanRegisterWithUniqueEmailSuccessfully() {
        log.info("Executing test: {}", testMethod.getName());

        String result = onRegisterPage()
                .navigateToRegisterPage()
                .registerCustomer(
                        "Test",
                        "User",
                        "1987-05-20",
                        "Main Street 123",
                        "1130",
                        "H63A",
                        "Budapest",
                        "District 4",
                        "Hungary",
                        "301234567",
                        UNIQUE_EMAIL, VALID_PASSWORD)
                .captureRegistrationResult();

        log.info("RESULT: Redirected to '{}'", result);
        Assert.assertEquals(result, LoginPageConstants.LOGIN_PAGE_URL,
                "Failed! User registration failed due to already existing email");
    }

    @Test(priority = 3, enabled = false, groups = {"e2e"})
    @Story("Customer Registration - Duplicate Email Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the system prevents registration when customer tries to register with an already existing email address")
    public void validateCustomerCanNotRegisterUserWithAlreadyExistingEmail() {
        log.info("Executing test: {}", testMethod.getName());

        String result = onRegisterPage()
                .navigateToRegisterPage()
                .registerCustomer(
                        "Test3",
                        "User",
                        "1987-05-20",
                        "Main Street 123",
                        "1130",
                        "H63A",
                        "Budapest",
                        "District 4",
                        "Hungary",
                        "30123554667",
                        loginUserEmail, loginWrongPass)
                .captureRegistrationResult();

        log.info("RESULT: '{}'", result);
        Assert.assertEquals(result, RegisterPageConstants.CUSTOMER_ALREADY_EXISTS_WITH_THIS_EMAIL,
                "Failed! User registered with unique email successfully");
    }
}