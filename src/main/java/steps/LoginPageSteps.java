package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class LoginPageSteps extends BaseSteps {

    public LoginPageSteps(WebDriver driver) {
        super(driver);
    }

    @Step("Navigate to Login Page")
    public LoginPageSteps navigateToLoginPage() {
        button.waitAndClick(onHomePage().loginMenuBtn());
        waitForUrl("/auth/login");
        log.info("Navigated to Login Page");
        return this;
    }

    @Step("Get Login Page Title")
    public String getLoginPageTitle() {
        return driver.getTitle();
    }

    @Step("Get current page URL")
    public String getCurrentPageUrl() {
        return super.getCurrentPageUrl();
    }

    @Step("Enter email: {email}")
    public LoginPageSteps enterEmail(String email) {
        input.enterText(onLoginPage().emailInputField(), email);
        return this;
    }

    @Step("Enter password")
    public LoginPageSteps enterPassword(String password) {
        input.enterText(onLoginPage().passwordInputField(), password);
        return this;
    }

    @Step("Click Login button")
    public CustomerAccountPageSteps clickLogin() {
        button.waitAndClick(onLoginPage().loginSubmitBtn());
        waitForUrl("/account");
        log.info("Login successful, redirected to account page");
        return new CustomerAccountPageSteps(driver);
    }

    @Step("Login as user: {email}")
    public CustomerAccountPageSteps logInAs(String email, String password) {
        return enterEmail(email)
                .enterPassword(password)
                .clickLogin();
    }

    @Step("Capture login result")
    public String captureLoginResult() {
        return handleResult(
                onLoginPage().globalToastMessage()
        );
    }

    @Step("Submit login and capture result")
    public String submitLoginAndCaptureResult() {
        button.waitAndClick(onLoginPage().loginSubmitBtn());
        return captureLoginResult();
    }

    @Step("Open Forgot Password form")
    public LoginPageSteps openForgotPasswordForm() {
        button.waitAndClick(onLoginPage().forgotPasswordLinkText());
        return this;
    }

    @Step("Enter forgot password email: {resetEmail}")
    public LoginPageSteps enterForgotPasswordEmail(String resetEmail) {
        input.enterText(onLoginPage().enterForgotPassEmail(), resetEmail);
        return this;
    }

    @Step("Submit forgot password request")
    public LoginPageSteps submitForgotPasswordRequest() {
        button.waitAndClick(onLoginPage().setNewPassBtn());
        return this;
    }

    @Step("Capture forgot password result message")
    public String captureForgotPasswordResultMessage() {
        return label.waitForToast(onLoginPage().globalToastMessage());
    }
}


