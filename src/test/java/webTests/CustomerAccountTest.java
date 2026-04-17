package webTests;

import constants.AccountPageConstants;
import constants.LoginPageConstants;
import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Feature("Customer Account Page")
@Listeners({AllureTestNg.class, TestListener.class})
public class CustomerAccountTest extends BaseTest {

    @Test(priority = 1, enabled = true, invocationCount = 1, groups = {"smoke", "e2e"})
    @Story("Validate logged out flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that customer can logged out successfully")
    public void validateUserCanLogOutSuccessfully() {
        log.info("Executing test: {}", testMethod.getName());

        String result = onLoginPage()
                .navigateToLoginPage()
                .logInAs(loginUserEmail, loginUserPass)
                .userSignOut();

        log.info("RESULT: Redirected to '{}'", result);
        Assert.assertEquals(result, LoginPageConstants.LOGIN_PAGE_URL, "Failed! User can't find the logout button");
    }

    @Test(priority = 2, enabled = true, invocationCount = 1, groups = {"e2e"})
    @Story("Validate accounts profile data update")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that customer can update his profile data")
    public void validateUserCanUpdateProfileDetails() {
        log.info("Executing test: {}", testMethod.getName());
        log.info("ACTION: Updating profile with new details");

        String result = onLoginPage()
                .navigateToLoginPage()
                .logInAs(loginUserEmail, loginUserPass)
                .navigateToProfilePage()
                .updateProfileDetails("FirstTest", "Last", "365648244")
                .captureProfileUpdateMessage();

        log.info("RESULT: '{}'", result);
        Assert.assertEquals(result, AccountPageConstants.UPDATE_PROFILE_DATA_SUCCESS_MESSAGE, "Failed! User profile didn't update successfully");
    }

    @Test(priority = 3, enabled = true, invocationCount = 1, groups = {"e2e"})
    @Story("Validate update password flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that customer can update password")
    public void validateUserCanUpdatePasswordSuccessfully() {
        log.info("Executing test: {}", testMethod.getName());
        log.info("ACTION: Updating password with valid credentials");

        String result = onLoginPage()
                .navigateToLoginPage()
                .logInAs(loginUserEmail, loginUserPass)
                .navigateToProfilePage()
                .updatePassword(userCurrentPass,userNewPass,userNewConfirmPass)
                .capturePasswordUpdateMessage();

        log.info("RESULT: '{}'", result);
        Assert.assertEquals(result, AccountPageConstants.UPDATE_PASSWORD_SUCCESS_MESSAGE, "Failed! Update password success message didn't display");
    }

    @Test(priority = 4, enabled = true, invocationCount = 1, groups = {"e2e"})
    @Story("Validate update password -Negative flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that customer can update password")
    public void validateUserCanNotUpdatePassword() {
        log.info("Executing test: {}", testMethod.getName());
        log.info("ACTION: Attempting password update with non-matching passwords");

        String result = onLoginPage()
                .navigateToLoginPage()
                .logInAs(loginUserEmail, loginUserPass)
                .navigateToProfilePage()
                .updatePassword(userCurrentPass, userNewPass, userNewConfirmPass)
                .capturePasswordUpdateMessage();

        log.info("RESULT: '{}'", result);
        Assert.assertEquals(result, AccountPageConstants.NEW_PASSWORD_AND_CONFIRM_PASSWORD_DONT_MATCH, "Failed! Password changed successfully");
    }
}