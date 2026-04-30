package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class ContactPageSteps extends BaseSteps {

    public ContactPageSteps(WebDriver driver) {
        super(driver);
    }

    @Step("Navigate to Contact Us Page")
    public ContactPageSteps navigateToContactUsPage() {
        button.waitAndClick(onContactPage().contactMenu());
        waitForUrl("/contact");
        log.info("Navigated to Contact Us Page");
        return this;
    }

    @Step("Get Contact Page Title")
    public String getContactPageTitle() {
        return getPageTitle();
    }

    @Step("Fill and Submit Contact Us Form")
    public ContactPageSteps contactUsForm(String fName, String lName, String email, String addSubject,
                                          String message, String attachmentPath) {
        log.info("Filling contact form");

        input.enterText(onContactPage().inputFirstName(), fName);
        input.enterText(onContactPage().inputLastName(), lName);
        input.enterText(onContactPage().inputEmailAddress(), email);

        dropdown.selectByVisibleText(onContactPage().selectSubject(), addSubject);

        input.enterText(onContactPage().inputMessage(), message);
        scrollIntoView(onContactPage().addAttachment());

        input.enterText(onContactPage().addAttachment(), attachmentPath);
        button.waitAndClick(onContactPage().sendSubmitBtn());

        log.info("Contact form submitted");
        return this;
    }

    @Step("Capture Contact Success Message")
    public String captureContactSuccessMessage() {
        String successMsg = label.getText(onContactPage().globalToastMessage());
        log.info("Contact form submitted: Current URL: {} | Success message: {}", getCurrentPageUrl(), successMsg);
        return successMsg;
    }

}