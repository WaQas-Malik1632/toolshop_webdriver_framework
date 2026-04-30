package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class CustomerAccountPageSteps extends BaseSteps {

    public CustomerAccountPageSteps(WebDriver driver) {
        super(driver);
    }

    @Step("Navigate to Profile Page")
    public CustomerAccountPageSteps navigateToProfilePage() {
        button.waitAndClick(onAccountPage().profileMenu());
        waitForUrl("/account/profile");
        log.info("Navigated to Profile Page");
        return this;
    }

    @Step("Navigate to Profile Page from Accounts Page")
    public String navigateToProfilePageFromAccountsPage() {
        button.waitAndClick(onAccountPage().profileMenu());
        waitForUrl("/account/profile");
        log.info("Navigated to Profile Page from Accounts Page");
        return driver.getCurrentUrl();
    }

    @Step("Get Current Page URL")
    public String getCurrentPageUrl() {
        return super.getCurrentPageUrl();
    }

    @Step("Navigate to Home Page")
    public void navigateToHomePage() {
        button.waitAndClick(onAccountPage().homeNavMenu());
    }

    @Step("User Sign Out from account page")
    public String userSignOut() {
        button.waitAndClick(onAccountPage().navMenu());
        button.waitAndClick(onAccountPage().signOutLink());
        String actualUrl = waitForUrl("/auth/login");
        log.info("User signed out successfully. Redirected to: {}", actualUrl);
        return actualUrl;
    }

    @Step("Update Profile Details")
    public CustomerAccountPageSteps updateProfileDetails(String fName, String lastName, String phone) {
        log.info("Updating profile details");

        input.enterText(onAccountPage().updateFirstName(), fName);
        input.enterText(onAccountPage().updateLastNameInput(), lastName);
        input.enterText(onAccountPage().updatePhoneNo(), phone);

        button.waitAndClick(onAccountPage().updateProfileSubmitBtn());

        log.info("Profile update submitted");
        return this;
    }

    @Step("Capture Profile Update Message")
    public String captureProfileUpdateMessage() {

        String successMessage = label.waitForToast(
                onAccountPage().globalToastMessage());

        log.info("Profile updated: {}", successMessage);
        return successMessage;
    }

    @Step("Update Password")
    public CustomerAccountPageSteps updatePassword(String currentPass, String newPass, String confirmNewPass) {
        log.info("Updating password");

        scrollIntoView(onAccountPage().changePasswordSubmitBtn());

        input.enterText(onAccountPage().currentPassword(), currentPass);
        input.enterText(onAccountPage().newPassword(), newPass);
        input.enterText(onAccountPage().confirmNewPassword(), confirmNewPass);
        button.waitAndClick(onAccountPage().changePasswordSubmitBtn());

        log.info("Password update submitted");
        return this;
    }

    @Step("Capture Password Update Message")
    public String capturePasswordUpdateMessage() {

        String message = label.waitForToast(onAccountPage().globalToastMessage());

        log.info("Password update completed. Result: {}", message);
        return message;
    }
}