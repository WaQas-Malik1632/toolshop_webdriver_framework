package bdd.stepDefinitions;

import constants.AccountPageConstants;
import constants.LoginPageConstants;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import steps.CustomerAccountPageSteps;
import steps.LoginPageSteps;
import webDriverManager.DriverManager;

import java.io.IOException;
import java.util.Properties;

public class CustomerAccountStepDefinitions {
    private static final Logger log = LogManager.getLogger(CustomerAccountStepDefinitions.class);
    private final CustomerAccountPageSteps accountPageSteps;
    private final LoginPageSteps loginPageSteps;
    private final Properties prop;
    private String capturedResult;

    public CustomerAccountStepDefinitions() throws IOException {
        this.accountPageSteps = new CustomerAccountPageSteps(DriverManager.getDriver());
        this.loginPageSteps = new LoginPageSteps(DriverManager.getDriver());
        this.prop = DriverManager.loadProperties();
    }

    @Given("user logs in as a customer")
    public void userLogsInAsACustomer() {
        String email = prop.getProperty("user.login.email");
        String password = prop.getProperty("user.login.password");
        log.info("Logging in as customer: {}", email);

        loginPageSteps.navigateToLoginPage().logInAs(email, password);
    }

    @When("user logs out from account")
    public void userLogsOutFromAccount() {
        log.info("Executing logout flow");
        capturedResult = accountPageSteps.userSignOut();
        log.info("RESULT: Redirected to '{}'", capturedResult);
    }

    @When("user updates profile details")
    public void userUpdatesProfileDetails() {
        log.info("ACTION: Updating profile with new details");

        accountPageSteps.navigateToProfilePage().updateProfileDetails("FirstTest", "Last",
                "365648244");
        capturedResult = accountPageSteps.captureProfileUpdateMessage();

        log.info("RESULT: '{}'", capturedResult);
    }

    @When("user updates password with valid credentials")
    public void userUpdatesPasswordWithValidCredentials() {
        String currentPassword = prop.getProperty("user.current.password");
        String newPassword = prop.getProperty("user.new.password");
        String confirmPassword = prop.getProperty("user.new.confirm.password");

        log.info("ACTION: Updating password with valid credentials");

        accountPageSteps.navigateToProfilePage().updatePassword(currentPassword, newPassword, confirmPassword);
        capturedResult = accountPageSteps.capturePasswordUpdateMessage();

        log.info("RESULT: '{}'", capturedResult);
    }

    @When("user attempts to update password with mismatched confirmation")
    public void userAttemptsToUpdatePasswordWithMismatchedConfirmation() {
        String currentPassword = prop.getProperty("user.current.password");
        String newPassword = prop.getProperty("user.new.password");
        String confirmPassword = prop.getProperty("user.new.confirm.password");

        log.info("ACTION: Attempting password update with non-matching passwords");

        accountPageSteps.navigateToProfilePage()
                .updatePassword(currentPassword, newPassword, confirmPassword);
        capturedResult = accountPageSteps.capturePasswordUpdateMessage();

        log.info("RESULT: '{}'", capturedResult);
    }

    @Then("user should be redirected to login page after logout")
    public void userShouldBeRedirectedToLoginPageAfterLogout() {
        log.info("Verifying redirect to login page after logout");
        Assert.assertEquals(capturedResult, LoginPageConstants.LOGIN_PAGE_URL,
                "Failed! User can't find the logout button");
    }

    @Then("profile should be updated successfully")
    public void profileShouldBeUpdatedSuccessfully() {
        log.info("Verifying profile update success");
        Assert.assertEquals(capturedResult, AccountPageConstants.UPDATE_PROFILE_DATA_SUCCESS_MESSAGE,
                "Failed! User profile didn't update successfully");
    }

    @Then("password should be updated successfully")
    public void passwordShouldBeUpdatedSuccessfully() {
        log.info("Verifying password update success");
        Assert.assertEquals(capturedResult, AccountPageConstants.UPDATE_PASSWORD_SUCCESS_MESSAGE,
                "Failed! Update password success message didn't display");
    }

    @Then("user should see password mismatch error")
    public void userShouldSeePasswordMismatchError() {
        log.info("Verifying password mismatch error");
        Assert.assertEquals(capturedResult, AccountPageConstants.NEW_PASSWORD_AND_CONFIRM_PASSWORD_DONT_MATCH,
                "Failed! Password changed successfully");
    }

}
