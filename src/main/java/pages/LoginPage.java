package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    @FindBy(css = "input[data-test='email']")
    public WebElement emailField;

    @FindBy(css = "input[data-test='password']")
    public WebElement passwordField;

    @FindBy(css = "input[data-test='login-submit']")
    public WebElement loginButton;

    @FindBy(css = "[data-test='login-error']")
    public WebElement loginErrorMessage;

    @FindBy(css = "a[href='/auth/login']")
    public WebElement signInNavLink;

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
        log.debug("LoginPage initialised");
    }

    public LoginPage navigateToLogin() {
        log.info("Navigating to login page");
        click(signInNavLink);
        return this;
    }

    public LoginPage enterEmail(String email) {
        log.info("Entering email: {}", email);
        type(emailField, email);
        return this;
    }

    public LoginPage enterPassword(String password) {
        log.info("Entering password");
        type(passwordField, password);
        return this;
    }

    public LoginPage clickLoginButton() {
        log.info("Clicking login button");
        click(loginButton);
        return this;
    }

    /** Convenience: full login in one chain. */
    public LoginPage login(String email, String password) {
        return enterEmail(email)
                .enterPassword(password)
                .clickLoginButton();
    }

    // ── State / assertion helpers ─────────────────────────────────────────────

    public boolean isLoginErrorDisplayed() {
        return isDisplayed(loginErrorMessage);
    }

    public String getLoginErrorText() {
        return getText(loginErrorMessage);
    }
}
