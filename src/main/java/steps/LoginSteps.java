package steps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pages.LoginPage;
import webDriverManager.WebDriverFunctional;

public class LoginSteps extends BaseSteps {

    private static final Logger log = LogManager.getLogger(LoginSteps.class);

    private final LoginPage loginPage;

    public LoginSteps(LoginPage loginPage) {
        super();
        this.loginPage = loginPage;
    }

    public LoginSteps goToLoginPage() {
        log.info("Step: go to login page");
        loginPage.navigateToLogin();
        return this;
    }

    public LoginSteps loginWith(String email, String password) {
        log.info("Step: login with email '{}'", email);
        loginPage.login(email, password);
        return this;
    }

    public LoginSteps loginWithDefaultCredentials() {
        return loginWith(
                WebDriverFunctional.prop.getProperty("user.login.email"),
                WebDriverFunctional.prop.getProperty("user.login.password")
        );
    }

    public boolean isLoginErrorVisible() {
        return loginPage.isLoginErrorDisplayed();
    }

    public String getLoginErrorMessage() {
        return loginPage.getLoginErrorText();
    }
}
