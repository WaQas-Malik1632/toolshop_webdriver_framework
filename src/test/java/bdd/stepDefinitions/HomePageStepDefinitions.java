package bdd.stepDefinitions;

import constants.HomePageConstants;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import steps.HomePageSteps;
import steps.LoginPageSteps;
import webDriverManager.DriverManager;

import java.io.IOException;
import java.util.Properties;

public class HomePageStepDefinitions {
    private static final Logger log = LogManager.getLogger(HomePageStepDefinitions.class);
    private final HomePageSteps homePageSteps;
    private final LoginPageSteps loginPageSteps;
    private final Properties prop;
    private String capturedResult;

    public HomePageStepDefinitions() throws IOException {
        this.homePageSteps = new HomePageSteps(DriverManager.getDriver());
        this.loginPageSteps = new LoginPageSteps(DriverManager.getDriver());
        this.prop = DriverManager.loadProperties();
    }

    @Given("user is on the home page")
    public void userIsOnTheHomePage() {
        log.info("Navigating to home page");
        homePageSteps.navigateToHomePage();
    }

    @Given("user is logged in and on home page")
    public void userIsLoggedInAndOnHomePage() {
        String email = prop.getProperty("user.login.email");
        String password = prop.getProperty("user.login.password");
        log.info("Logging in and navigating to home page");

        loginPageSteps.navigateToLoginPage()
                .logInAs(email, password)
                .navigateToHomePage();
    }

    @Then("home page logo should be visible")
    public void homePageLogoShouldBeVisible() {
        log.info("Executing test: Validate Home page logo is visible");
        boolean actualLogoVisible = homePageSteps.isHomePageLogoVisible();

        Assert.assertTrue(actualLogoVisible, "Logo is not visible");
        log.info("Home Page Logo visible: {}", actualLogoVisible);
    }

    @When("user purchases multiple products with credit card")
    public void userPurchasesMultipleProductsWithCreditCard() {
        log.info("Executing test: Validate Customer can purchase multiple products with Credit Card");

        capturedResult = homePageSteps.purchaseMultipleProductsWithCreditCard(
                "5655-6565-7874-7875",
                "12/2030",
                "587",
                "Tester User",
                "H#73");

        log.info("Purchase result: {}", capturedResult);
    }

    @When("user purchases product with bank transfer")
    public void userPurchasesProductWithBankTransfer() {
        log.info("Executing test: Validate Customer can purchase any product with Bank Account");

        capturedResult = homePageSteps.purchaseItemWithBankTransfer(
                "Test Bank",
                "Test user",
                "1015112101512",
                "H#73");

        log.info("Purchase result: {}", capturedResult);
    }

    @When("guest user purchases product with cash on delivery")
    public void guestUserPurchasesProductWithCashOnDelivery() {
        log.info("Executing test: Validate guest user can purchase any product with COD");

        capturedResult = homePageSteps.purchaseItemFlowAsGuestWithCod(
                "guest@example.com",
                "Tester",
                "viki",
                "123 Test St",
                "Berlin",
                "Berlin",
                "Germany",
                "10115",
                "H#874");

        log.info("Purchase result: {}", capturedResult);
    }

    @Then("payment should be successful")
    public void paymentShouldBeSuccessful() {
        log.info("Verifying payment success");
        Assert.assertEquals(capturedResult, HomePageConstants.PRODUCT_PURCHASE_PAYMENT_SUCCESS_MESSAGE,
                "Payment was not successful");
    }

}
