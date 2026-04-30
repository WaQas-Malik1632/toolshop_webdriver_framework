package bdd.stepDefinitions;

import constants.AccountPageConstants;
import constants.LoginPageConstants;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import steps.CustomerAccountPageSteps;
import steps.LoginPageSteps;
import webDriverManager.DriverManager;

import java.io.IOException;
import java.util.Properties;

import static utils.TestContext.getUserData;

public class CustomerAccountStepDefinitions {
    private static final Logger log = LogManager.getLogger(CustomerAccountStepDefinitions.class);
    private final CustomerAccountPageSteps accountPageSteps;
    private final LoginPageSteps loginPageSteps;

    public final Properties prop;
    private String capturedResult;

    public CustomerAccountStepDefinitions() throws IOException {

        WebDriver driver = DriverManager.getDriver();
        this.loginPageSteps = new LoginPageSteps(driver);
        this.accountPageSteps = new CustomerAccountPageSteps(driver);
        this.prop = DriverManager.loadProperties();
    }

    @Given("user logs in as a customer")
    public void userLogsInAsACustomer() {
        log.info("Logging in as customer: {}", getUserData().loginUserEmail);

        loginPageSteps.navigateToLoginPage()
                .logInAs(getUserData().loginUserEmail, getUserData().loginUserPass);
    }

    @When("user logs out from account")
    public void userLogsOutFromAccount() {
        capturedResult = accountPageSteps.userSignOut();
        log.info("RESULT: Redirected to '{}'", capturedResult);
    }

    @When("user navigates to profile page from accounts page")
    public void userNavigatesToProfilePage() {
        capturedResult = accountPageSteps.navigateToProfilePageFromAccountsPage();
        Assert.assertEquals(
                capturedResult,
                AccountPageConstants.CUSTOMER_UPDATE_PROFILE_PAGE_URL,
                "Failed! User didn't land on profile page");
    }

    @When("user updates profile details")
    public void userUpdatesProfileDetails() {
        accountPageSteps.updateProfileDetails("FirstTest", "Last", "365648244");
        capturedResult = accountPageSteps.captureProfileUpdateMessage();
        log.info("RESULT: '{}'", capturedResult);
    }

    @When("user updates password with valid credentials")
    public void userUpdatesPasswordWithValidCredentials() {
        log.info("ACTION: Updating password with valid credentials");
        accountPageSteps.updatePassword(getUserData().userCurrentPass, getUserData().userNewPass, getUserData().userNewConfirmPass);
        capturedResult = accountPageSteps.capturePasswordUpdateMessage();

        log.info("RESULT: '{}'", capturedResult);
    }

    @When("user attempts to update password with mismatched confirmation")
    public void userAttemptsToUpdatePasswordWithMismatchedConfirmation() {
        log.info("ACTION: Attempting password update with non-matching passwords");
        accountPageSteps.updatePassword(getUserData().userCurrentPass, getUserData().userNewPass, getUserData().userNewConfirmPass);
        capturedResult = accountPageSteps.capturePasswordUpdateMessage();
        Assert.assertEquals(
                capturedResult,
                AccountPageConstants.NEW_PASSWORD_AND_CONFIRM_PASSWORD_DONT_MATCH,
                "Failed! Password changed successfully");
    }

    @Then("user should be redirected to login page after logout")
    public void userShouldBeRedirectedToLoginPageAfterLogout() {
        Assert.assertEquals(
                capturedResult,
                LoginPageConstants.LOGIN_PAGE_URL,
                "Failed! User can't find the logout button");
    }

    @Then("profile should be updated successfully")
    public void profileShouldBeUpdatedSuccessfully() {

        Assert.assertEquals(
                capturedResult,
                AccountPageConstants.UPDATE_PROFILE_DATA_SUCCESS_MESSAGE,
                "Failed! User profile didn't update successfully");
    }

    @Then("password should be updated successfully and redirected on login page")
    public void passwordShouldBeUpdatedSuccessfully() {
        Assert.assertEquals(
                capturedResult,
                AccountPageConstants.UPDATE_PASSWORD_SUCCESS_MESSAGE,
                "Failed! Update password success message didn't display");
    }

    @Then("user should see password mismatch error")
    public void userShouldSeePasswordMismatchError() {

        Assert.assertEquals(
                capturedResult,
                AccountPageConstants.NEW_PASSWORD_AND_CONFIRM_PASSWORD_DONT_MATCH,
                "Failed! Password changed successfully");
    }

}