package steps;

import basePage.BasePage;
import org.openqa.selenium.WebDriver;
import pages.*;

public class BaseSteps extends BasePage {

    public BaseSteps(WebDriver driver) {
        super(driver);
    }

    public HomePage onHomePage() {
        return on(HomePage.class);
    }

    public LoginPage onLoginPage() {
        return on(LoginPage.class);
    }

    public CustomerRegistrationPage onRegistrationPage() {
        return on(CustomerRegistrationPage.class);
    }

    public CustomerAccountPage onAccountPage() {
        return on(CustomerAccountPage.class);
    }

    public ContactPage onContactPage() {
        return on(ContactPage.class);
    }

}

