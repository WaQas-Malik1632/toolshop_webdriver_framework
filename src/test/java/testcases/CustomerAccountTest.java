package testcases;

import baseTest.BaseTest;
import constants.AccountPageConstants;
import constants.LoginPageConstants;
import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic("E2E Automation")
@Feature("Customer Account Page")
@Listeners({AllureTestNg.class, TestListener.class})
public class CustomerAccountTest extends BaseTest {

    @Test(priority = 1, enabled = true, invocationCount = 1)
    @Story("Validate logged out flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that customer can logged out successfully")
    public void validateUserCanLogOutSuccessfully() {
        log.info("Executing test: {}", testMethod.getName());

        Assert.assertEquals(
                onLoginPage()
                        .navigateToLoginPage()
                        .logInAs(prop.getProperty("user.login.email"), prop.getProperty("user.login.password"))
                        .userSignOut(),
                LoginPageConstants.LOGIN_PAGE_URL,
                "Failed! User can't find the logout button"
        );
    }

    @Test(priority = 2, enabled = true, invocationCount = 1)
    @Story("Validate accounts profile data update")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that customer can update his profile data")
    public void validateUserCanUpdateProfileDetails() {
        log.info("Executing test: {}", testMethod.getName());
        log.info("ACTION: Updating profile with new details");

        Assert.assertEquals(
                onLoginPage()
                        .navigateToLoginPage()
                        .logInAs(prop.getProperty("user.login.email"), prop.getProperty("user.login.password"))
                        .navigateToProfilePage()

                        .updateProfileDetails("FirstTest", "Last", "365648244")
                        .captureProfileUpdateMessage(),
                AccountPageConstants.UPDATE_PROFILE_DATA_SUCCESS_MESSAGE,
                "Failed! User profile didn't update successfully"
        );
    }

    @Test(priority = 3, enabled = true, invocationCount = 1)
    @Story("Validate update password flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that customer can update password")
    public void validateUserCanUpdatePasswordSuccessfully() {
        log.info("Executing test: {}", testMethod.getName());
        log.info("ACTION: Updating password with valid credentials");

        Assert.assertEquals(
                onLoginPage()
                        .navigateToLoginPage()
                        .logInAs(prop.getProperty("user.login.email"), prop.getProperty("user.login.password"))
                        .navigateToProfilePage()

                        .updatePassword(
                                prop.getProperty("user.change.current.password"),
                                prop.getProperty("user.change.new.password"),
                                prop.getProperty("user.change.confirm.password"))
                        .capturePasswordUpdateMessage(),
                AccountPageConstants.UPDATE_PASSWORD_SUCCESS_MESSAGE,
                "Failed! Update password success message didn't display"
        );
    }

    @Test(priority = 4, enabled = true, invocationCount = 1)
    @Story("Validate update password -Negative flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that customer can update password")
    public void validateUserCanNotUpdatePassword() {
        log.info("Executing test: {}", testMethod.getName());
        log.info("ACTION: Attempting password update with non-matching passwords");

        Assert.assertEquals(
                onLoginPage()
                        .navigateToLoginPage()
                        .logInAs(prop.getProperty("user.login.email"), prop.getProperty("user.login.password"))
                        .navigateToProfilePage()

                        .updatePassword(
                                prop.getProperty("user.change.current.password"),
                                prop.getProperty("user.change.new.password"),
                                prop.getProperty("user.change.confirm.password"))
                        .capturePasswordUpdateMessage(),
                AccountPageConstants.NEW_PASSWORD_AND_CONFIRM_PASSWORD_DONT_MATCH,
                "Failed! Password changed successfully"
        );
    }
}

