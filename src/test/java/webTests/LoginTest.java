package webTests;

import constants.LoginPageConstants;
import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Feature("Authentication")
@Listeners({AllureTestNg.class, TestListener.class})
public class LoginTest extends BaseTest {

    @Test(priority = 1, enabled = true, groups = {"smoke"})
    @Story("Validate Login Page Title")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the Login page title matches the expected value")
    public void validateLoginPageTitle() {
        log.info("Executing test: {}", testMethod.getName());

        String actualTitle = onLoginPage()
                .navigateToLoginPage()
                .getLoginPageTitle();
        log.info("RESULT: Page title = '{}'", actualTitle);
        Assert.assertEquals(actualTitle, LoginPageConstants.LOGIN_PAGE_TITLE, "Title mismatch on Login Page");
    }

    @Test(priority = 2, enabled = true, groups = {"smoke", "e2e"})
    @Story("Customer Login - Positive Flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that user can login with valid credentials successfully")
    public void validateCustomerLogsInSuccessfully() {
        log.info("Executing test: {}", testMethod.getName());
        log.info("ACTION: Attempting login with valid user: {}", prop.getProperty("user.login.email"));

        String result = onLoginPage()
                .navigateToLoginPage()
                .enterEmail(prop.getProperty("user.login.email"))
                .enterPassword(prop.getProperty("user.login.password"))
                .submitLoginAndCaptureResult();

        log.info("RESULT: {}", result);
        Assert.assertEquals(result, LoginPageConstants.CUSTOMER_ACCOUNT_PAGE_URL, "Login failed: URL mismatch");
    }

    @Test(priority = 3, enabled = true, groups = {"smoke", "e2e"})
    @Story("Customer Login - Negative Flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that user can't login with invalid credentials")
    public void validateCustomerLogInWithInvalidCredentials() {
        log.info("Executing test: {}", testMethod.getName());
        log.info("ACTION: Attempting login with invalid credentials");

        String result = onLoginPage()
                .navigateToLoginPage()
                .enterEmail(prop.getProperty("user.login.email"))
                .enterPassword(prop.getProperty("user.login.wrong.password"))
                .submitLoginAndCaptureResult();

        log.info("RESULT: {}", result);
        Assert.assertEquals(result, LoginPageConstants.INVALID_EMAIL_OR_PASSWORD, "Failed! User logged in with invalid credentials");
    }

    @Test(priority = 4, enabled = true, groups = {"e2e"})
    @Story("Reset Password - Registered Email")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that user can reset password using Registered email successfully")
    public void validateUserCanResetForgotPasswordWithRegisteredEmailSuccessfully() {
        log.info("Executing test: {}", testMethod.getName());
        log.info("ACTION: Requesting password reset for: {}", prop.getProperty("user.reset.email"));

        String result = onLoginPage()
                .navigateToLoginPage()
                .openForgotPasswordForm()
                .enterForgotPasswordEmail(prop.getProperty("user.reset.email"))
                .submitForgotPasswordRequest()
                .captureForgotPasswordResultMessage();

        log.info("RESULT: {}", result);
        Assert.assertEquals(result, LoginPageConstants.FORGOT_PASSWORD_RESET_SUCCESS_MESSAGE, "Failed! Entered email not registered");
    }

    @Test(priority = 5, enabled = true, groups = {"e2e"})
    @Story("Reset Password - Negative Flow - Unregistered Email")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that user can not reset password using un-registered email")
    public void validateUserCanNotResetForgotPasswordWithUnregisteredEmail() {
        log.info("Executing test: {}", testMethod.getName());
        log.info("ACTION: Requesting password reset for unregistered: {}", prop.getProperty("user.reset.registered.email"));

        String result = onLoginPage()
                .navigateToLoginPage()
                .openForgotPasswordForm()
                .enterForgotPasswordEmail(prop.getProperty("user.reset.registered.email"))
                .submitForgotPasswordRequest()
                .captureForgotPasswordResultMessage();

        log.info("RESULT: {}", result);
        Assert.assertEquals(result, LoginPageConstants.FORGOT_PASSWORD_INVALID_EMAIL_NEW_ERROR, "Failed! Password reset successful with unregistered email");
    }
}

