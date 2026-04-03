package testcases;

import baseTest.BaseTest;
import constants.LoginPageConstants;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("Authentication")
public class LoginTest extends BaseTest {

    @Test(priority = 1, enabled = true, groups = {"smoke"})
    @Story("Validate Login Page Title")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the Login page title matches the expected value")
    public void validateLoginPageTitle() {
        log.info("Executing test: {}", testMethod.getName());

        Assert.assertEquals(
                onLoginPage()
                        .navigateToLoginPage()
                        .getLoginPageTitle(),
                LoginPageConstants.LOGIN_PAGE_TITLE,
                "Title mismatch on Login Page"
        );
    }

    @Test(priority = 2, enabled = true, groups = {"smoke", "e2e"})
    @Story("Customer Login - Positive Flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that user can login with valid credentials successfully")
    public void validateCustomerLogsInSuccessfully() {
        log.info("Executing test: {}", testMethod.getName());
        log.info("ACTION: Attempting login with valid user");

        Assert.assertEquals(
                onLoginPage()
                        .navigateToLoginPage()
                        .enterEmail(prop.getProperty("user.login.email"))
                        .enterPassword(prop.getProperty("user.login.password"))
                        .submitLoginAndCaptureResult(),
                LoginPageConstants.CUSTOMER_ACCOUNT_PAGE_URL,
                "Login failed: URL mismatch"
        );
    }

    @Test(priority = 3, enabled = true, groups = {"smoke", "e2e"})
    @Story("Customer Login - Negative Flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that user can't login with invalid credentials")
    public void validateCustomerLogInWithInvalidCredentials() {
        log.info("Executing test: {}", testMethod.getName());
        log.info("ACTION: Attempting login with invalid credentials");

        Assert.assertEquals(
                onLoginPage()
                        .navigateToLoginPage()
                        .enterEmail(prop.getProperty("user.login.email"))
                        .enterPassword(prop.getProperty("user.login.wrong.password"))
                        .submitLoginAndCaptureResult(),
                LoginPageConstants.INVALID_EMAIL_OR_PASSWORD,
                "Failed! User logged in with invalid credentials"
        );
    }

    @Test(priority = 4, enabled = true, groups = {"e2e"})
    @Story("Reset Password - Registered Email")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that user can reset password using Registered email successfully")
    public void validateUserCanResetForgotPasswordWithRegisteredEmail() {
        log.info("Executing test: {}", testMethod.getName());

        Assert.assertEquals(
                onLoginPage()
                        .navigateToLoginPage()
                        .openForgotPasswordForm()
                        .enterForgotPasswordEmail(prop.getProperty("user.reset.email"))
                        .submitForgotPasswordRequest()
                        .captureForgotPasswordResultMessage(),
                LoginPageConstants.FORGOT_PASSWORD_RESET_SUCCESS_MESSAGE,
                "Failed! Entered email not registered"
        );
    }

    @Test(priority = 5, enabled = true, groups = {"e2e"})
    @Story("Reset Password - Negative Flow - Unregistered Email")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that user can not reset password using un-registered email")
    public void validateUserCanNotResetForgotPasswordWithUnregisteredEmail() {
        log.info("Executing test: {}", testMethod.getName());

        Assert.assertEquals(
                onLoginPage()
                        .navigateToLoginPage()
                        .openForgotPasswordForm()
                        .enterForgotPasswordEmail(prop.getProperty("user.reset.registered.email"))
                        .submitForgotPasswordRequest()
                        .captureForgotPasswordResultMessage(),
                LoginPageConstants.FORGOT_PASSWORD_INVALID_EMAIL_ERROR,
                "Failed! Password reset successful with unregistered email"
        );
    }
}

