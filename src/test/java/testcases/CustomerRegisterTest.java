package testcases;

import baseTest.BaseTest;
import constants.LoginPageConstants;
import constants.RegisterPageConstants;
import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Feature("Authentication")
@Listeners({AllureTestNg.class, TestListener.class})
public class CustomerRegisterTest extends BaseTest {

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
        log.info("ACTION: Registering new customer with email: Tester@yopmail.com");

        String result = onRegisterPage()
                .navigateToRegisterPage()
                .registerCustomer("Tester", "New", "2000-01-27", "123", "1130",
                        "Adbuston", "District 8", "Hungary", "3012809097",
                        "Tester@yopmail.com", "welcome01@Pass")
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
        log.info("ACTION: Attempting registration with existing email: Tester@yopmail.com");

        String result = onRegisterPage()
                .navigateToRegisterPage()
                .registerCustomer("Test2", "User", "1987-05-20", "Main Street 123", "1130",
                        "Budapest", "District 4", "Hungary", "301234567",
                        "Tester@yopmail.com", "welcome01")
                .captureRegistrationResult();

        log.info("RESULT: '{}'", result);
        Assert.assertEquals(result, RegisterPageConstants.CUSTOMER_ALREADY_EXISTS_WITH_THIS_EMAIL,
                "Failed! User registered with unique email successfully");
    }
}

