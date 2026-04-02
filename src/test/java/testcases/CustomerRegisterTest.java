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

@Epic("Authentication Module")
@Feature("Registration")
@Listeners({AllureTestNg.class, TestListener.class})
public class CustomerRegisterTest extends BaseTest {

    @Test(priority = 1, enabled = false)
    @Story("Validate Customer Register Page Title")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the customer register page title matches the expected value")
    public void validateCustomerRegisterPageTitle() {
        log.info("Executing test: {}", testMethod.getName());

        Assert.assertEquals(
                onRegisterPage()
                        .navigateToRegisterPage()
                        .getCustomerRegisterPageTitle(),
                RegisterPageConstants.CUSTOMER_REGISTER_PAGE_TITLE,
                "Failed! Title didn't match"
        );
    }

    @Test(priority = 2, enabled = true)
    @Story("Customer Registration - Positive Flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that new customer can register successfully with unique email and valid details")
    public void validateCustomerCanRegisterWithUniqueEmailSuccessfully() {
        log.info("Executing test: {}", testMethod.getName());

        Assert.assertEquals(
                onRegisterPage()
                        .navigateToRegisterPage()
                        .registerCustomer(
                                "Tester",
                                "New",
                                "2000-01-27",
                                "123",
                                "1130",
                                "Adbuston",
                                "District 8",
                                "Hungary",
                                "3012809097",
                                "Tester@yopmail.com",
                                "Angry@200")
                        .captureRegistrationResult(),
                LoginPageConstants.LOGIN_PAGE_URL,
                "Failed! User registration failed due to already existing email");
    }

    @Test(priority = 3, enabled = true)
    @Story("Customer Registration - Duplicate Email Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the system prevents registration when customer tries to register with an already existing email address")
    public void validateCustomerCanNotRegisterUserWithAlreadyExistingEmail() {
        log.info("Executing test: {}", testMethod.getName());

        Assert.assertEquals(
                onRegisterPage()
                        .navigateToRegisterPage()
                        .registerCustomer(
                                "Test2",
                                "User",
                                "1987-05-20",
                                "Main Street 123",
                                "1130",
                                "Budapest",
                                "District 4",
                                "Hungary",
                                "301234567",
                                "Tester@yopmail.com",
                                "Angry@200")
                        .captureRegistrationResult(),
                RegisterPageConstants.CUSTOMER_ALREADY_EXISTS_WITH_THIS_EMAIL,
                "Failed! User registered with unique email successfully");
    }
}

