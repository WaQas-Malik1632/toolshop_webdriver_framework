package testcases;

import baseTest.BaseTest;
import constants.ContactPageConstants;
import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Feature("Contact Us Page")
@Listeners({AllureTestNg.class, TestListener.class})
public class ContactTest extends BaseTest {

    @Test(priority = 1, enabled = true, groups = {"smoke", "e2e"})
    @Story("Validate contact page title")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the customer contact page title matches the expected value")
    public void validateNavigationToContactPage() {
        log.info("Executing test: {}", testMethod.getName());

        String title = onContactPage()
                .navigateToContactUsPage()
                .getContactPageTitle();
        log.info("RESULT: Page title = '{}'", title);
        Assert.assertEquals(title, ContactPageConstants.CONTACT_PAGE_TITLE, "Failed! Title didn't match");
    }

    @Test(priority = 2, enabled = true, groups = {"e2e"})
    @Story("Validate customer can contact with Admin")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the customer can contact successfully")
    public void validateUserCanSendContactMessageSuccessfully() {
        log.info("Executing test: {}", testMethod.getName());

        String firstName = "Tester";
        String lastName = "User";
        String email = "test@gmail.com";
        String subject = "Payments";
        String message = "TestUser is contacting related to payment refund status, as products returned two weeks ago, thanks";
        String attachmentPath = "C:\\Users\\waqas.naseer\\Downloads\\EmptyFile.txt";

        log.info("ACTION: Filling Contact Us form --> [Name: {} {}, Email: {}, Subject: {}]", firstName, lastName, email, subject);

        String result = onContactPage()
                .navigateToContactUsPage()
                .contactUsForm(firstName, lastName, email, subject, message, attachmentPath)
                .captureContactSuccessMessage();

        log.info("RESULT: '{}'", result);
        Assert.assertEquals(result, ContactPageConstants.CONTACT_MESSAGE_SUCCESS_MESSAGE, "Failed! Form didn't submit successfully");
    }
}


