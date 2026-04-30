package bdd.stepDefinitions;

import constants.LoginPageConstants;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import steps.LoginPageSteps;
import webDriverManager.DriverManager;

import java.io.IOException;
import java.util.Properties;

import static utils.TestContext.getUserData;

public class LoginStepDefinitions {
    private static final Logger log = LogManager.getLogger(LoginStepDefinitions.class);
    private final LoginPageSteps loginPageSteps;
    private String capturedResult;
    public final Properties prop;

    public LoginStepDefinitions() throws IOException {
        this.loginPageSteps = new LoginPageSteps(DriverManager.getDriver());
        this.prop = DriverManager.loadProperties();
    }

    @Given("user is on the login page")
    public void userIsOnTheLoginPage() {
        log.info("Navigating to login page");
        loginPageSteps.navigateToLoginPage();
    }

    @Then("login page title should be correct")
    public void loginPageTitleShouldBeCorrect() {
        String actualTitle = loginPageSteps.getLoginPageTitle();
        log.info("Verifying page title. Expected: {}, Actual: {}", LoginPageConstants.LOGIN_PAGE_TITLE, actualTitle);
        Assert.assertEquals(actualTitle, LoginPageConstants.LOGIN_PAGE_TITLE, "Title mismatch on Login Page");
    }

    @When("user logs in with valid credentials")
    public void userLogsInWithValidCredentials() {
        log.info("Logging in with valid credentials for user: {}", getUserData().loginUserEmail);

        capturedResult = loginPageSteps
                .enterEmail(getUserData().loginUserEmail)
                .enterPassword(getUserData().loginUserPass)
                .submitLoginAndCaptureResult();
        log.info("Login result captured: {}", capturedResult);
    }

    @When("user logs in with invalid credentials")
    public void userLogsInWithInvalidCredentials() {
        log.info("Logging in with invalid credentials for user: {}", getUserData().loginUserEmail);

        capturedResult = loginPageSteps
                .enterEmail(getUserData().loginUserEmail)
                .enterPassword(getUserData().loginWrongPass)
                .submitLoginAndCaptureResult();
        log.info("Login result captured: {}", capturedResult);
    }

    @When("user requests a password reset for a registered email")
    public void userRequestsPasswordResetForRegisteredEmail() {
        log.info("Requesting password reset for registered email: {}", getUserData().resetWithRegisteredEmail);

        loginPageSteps.openForgotPasswordForm()
                .enterForgotPasswordEmail(getUserData().resetWithRegisteredEmail)
                .submitForgotPasswordRequest();
        capturedResult = loginPageSteps.captureForgotPasswordResultMessage();
        log.info("Password reset result: {}", capturedResult);
    }

    @When("user requests a password reset for an unregistered email")
    public void userRequestsPasswordResetForUnregisteredEmail() {
        log.info("Requesting password reset for unregistered email: {}", getUserData().resetWithUnRegisteredEmail);
        loginPageSteps.openForgotPasswordForm()
                .enterForgotPasswordEmail(getUserData().resetWithUnRegisteredEmail)
                .submitForgotPasswordRequest();
        capturedResult = loginPageSteps.captureForgotPasswordResultMessage();
        log.info("Password reset result with unregistered email: {}", capturedResult);
    }

    @Then("user is redirected to the account page")
    public void userIsRedirectedToTheAccountPage() {
        log.info("Verifying user is redirected to account page");
        Assert.assertEquals(capturedResult, LoginPageConstants.CUSTOMER_ACCOUNT_PAGE_URL,
                "Login failed: User not redirected to account page");
    }

    @Then("user should see an authentication error")
    public void userShouldSeeAnAuthenticationError() {
        log.info("Verifying authentication error message");
        Assert.assertEquals(capturedResult, LoginPageConstants.INVALID_EMAIL_OR_PASSWORD,
                "Expected authentication error not displayed");
    }

    @Then("user should receive a password reset confirmation")
    public void userShouldReceivePasswordResetConfirmation() {
        log.info("Verifying password reset confirmation");
        Assert.assertEquals(capturedResult, LoginPageConstants.FORGOT_PASSWORD_RESET_SUCCESS_MESSAGE,
                "Password reset confirmation not received");
    }

    @Then("user should see an invalid email error")
    public void userShouldSeeAnInvalidEmailError() {
        log.info("Verifying invalid email error");
        Assert.assertEquals(capturedResult, LoginPageConstants.FORGOT_PASSWORD_INVALID_EMAIL_NEW_ERROR,
                "Expected invalid email error not displayed");
    }
}