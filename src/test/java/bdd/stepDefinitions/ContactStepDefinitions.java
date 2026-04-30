package bdd.stepDefinitions;

import constants.ContactPageConstants;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import steps.ContactPageSteps;
import webDriverManager.DriverManager;

public class ContactStepDefinitions {
    private static final Logger log = LogManager.getLogger(ContactStepDefinitions.class);
    private final ContactPageSteps contactPageSteps;
    private String capturedResult;

    public ContactStepDefinitions() {
        this.contactPageSteps = new ContactPageSteps(DriverManager.getDriver());
    }

    @Given("user is on the contact us page")
    public void userIsOnTheContactUsPage() {
        log.info("Navigating to contact us page");
        contactPageSteps.navigateToContactUsPage();
    }

    @Then("contact us page title should be correct")
    public void contactUsPageTitleShouldBeCorrect() {
        String actualTitle = contactPageSteps.getContactPageTitle();
        log.info("Verifying page title. Expected: {}, Actual: {}", ContactPageConstants.CONTACT_PAGE_TITLE, actualTitle);
        Assert.assertEquals(actualTitle, ContactPageConstants.CONTACT_PAGE_TITLE, "Failed! Title didn't match");
    }

    @When("user can fill the form and submit it")
    public void userCanFillTheFormAndSendsTheMessageSuccessfully() {
        String firstName = "Tester";
        String lastName = "User";
        String email = "test@gmail.com";
        String subject = "Payments";
        String message = "TestUser is contacting related to payment refund status, as products returned two weeks ago, thanks";
        String attachmentPath = "C:\\Users\\waqas.naseer\\Downloads\\EmptyFile.txt";

        log.info("ACTION: Filling Contact Us form --> [Name: {} {}, Email: {}, Subject: {}]", firstName, lastName, email, subject);

        contactPageSteps.contactUsForm(firstName, lastName, email, subject, message, attachmentPath);
        capturedResult = contactPageSteps.captureContactSuccessMessage();
    }

    @Then("success toast message should be correct")
    public void successMessageShouldBeCorrect() {
        log.info("Verifying success toast message correct");
        Assert.assertEquals(capturedResult, ContactPageConstants.CONTACT_MESSAGE_SUCCESS_MESSAGE,
                "Failed! Form didn't submit successfully");
    }
}