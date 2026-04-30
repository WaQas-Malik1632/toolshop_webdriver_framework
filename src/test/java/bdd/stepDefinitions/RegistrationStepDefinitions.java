package bdd.stepDefinitions;

import constants.LoginPageConstants;
import constants.RegisterPageConstants;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import steps.CustomerRegisterPageSteps;
import webDriverManager.DriverManager;

import java.io.IOException;
import java.util.Properties;

public class RegistrationStepDefinitions {
    private static final Logger log = LogManager.getLogger(RegistrationStepDefinitions.class);
    private final CustomerRegisterPageSteps registerPageSteps;
    public final Properties prop;
    private String capturedResult;

    private static final String UNIQUE_EMAIL = "apitester_" + System.currentTimeMillis() + "@yopmail.com";
    private static final String EXISTING_EMAIL = "customer@practicesoftwaretesting.com";
    private static final String VALID_PASSWORD = "Toolshopuser@1";

    public RegistrationStepDefinitions() throws IOException {
        this.registerPageSteps = new CustomerRegisterPageSteps(DriverManager.getDriver());
        this.prop = DriverManager.loadProperties();
    }

    @Given("user is on the registration page")
    public void theUserIsOnTheRegistrationPage() {
        log.info("Navigating to registration page");
        registerPageSteps.navigateToRegisterPage();
    }

    @Then("registration page title should be correct")
    public void registrationPageTitleShouldBeCorrect() {
        String actualTitle = registerPageSteps.getCustomerRegisterPageTitle();
        log.info("Verifying page title. Expected: {}, Actual: {}", RegisterPageConstants.CUSTOMER_REGISTER_PAGE_TITLE, actualTitle);
        Assert.assertEquals(actualTitle, RegisterPageConstants.CUSTOMER_REGISTER_PAGE_TITLE, "Customer Register page title mismatch");
    }

    @When("user registers with unique email and valid details")
    public void userRegistersWithUniqueEmailAndValidDetails() {
        log.info("Executing registration with unique email: {}", UNIQUE_EMAIL);

        capturedResult = registerPageSteps.registerCustomer(
                        "Test",
                        "User",
                        "1987-05-20",
                        "Main Street 123",
                        "1130",
                        "H63A",
                        "Budapest",
                        "District 4",
                        "Hungary",
                        "301234567",
                        UNIQUE_EMAIL,
                        VALID_PASSWORD)
                .captureRegistrationResult();

        log.info("RESULT: Redirected to '{}'", capturedResult);
    }

    @When("user attempts to register with an already existing email")
    public void userAttemptsToRegisterWithAnAlreadyExistingEmail() {
        log.info("Executing registration with existing email: {}", EXISTING_EMAIL);

        capturedResult = registerPageSteps.registerCustomer(
                        "Test3",
                        "User",
                        "1987-05-20",
                        "Main Street 123",
                        "1130",
                        "H63A",
                        "Budapest",
                        "District 4",
                        "Hungary",
                        "30123554667",
                        EXISTING_EMAIL,
                        VALID_PASSWORD)
                .captureRegistrationResult();

        log.info("RESULT: '{}'", capturedResult);
    }

    @Then("user should be redirected to login page after registration")
    public void userShouldBeRedirectedToLoginPageAfterRegistration() {
        log.info("Verifying redirect to login page after registration");
        Assert.assertEquals(capturedResult, LoginPageConstants.LOGIN_PAGE_URL,
                "Failed! User registration failed due to already existing email");
    }

    @Then("user should see duplicate email error")
    public void userShouldSeeDuplicateEmailError() {
        log.info("Verifying duplicate email error message");
        Assert.assertEquals(capturedResult, RegisterPageConstants.CUSTOMER_ALREADY_EXISTS_WITH_THIS_EMAIL,
                "Failed! User registered with unique email successfully");
    }
}