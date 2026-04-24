package pages;

import io.qameta.atlas.webdriver.AtlasWebElement;
import io.qameta.atlas.webdriver.WebPage;
import io.qameta.atlas.webdriver.extension.FindBy;

public interface CustomerAccountPage extends WebPage {

    @FindBy("//a[@routerlink='profile']")
    AtlasWebElement<?> profileMenu();

    @FindBy("//input[@id='first_name']")
    AtlasWebElement<?> updateFirstName();

    @FindBy("//input[@id='last_name']")
    AtlasWebElement<?> updateLastNameInput();

    @FindBy("//input[@id='phone']")
    AtlasWebElement<?> updatePhoneNo();

    @FindBy("//button[normalize-space()='Update Profile']")
    AtlasWebElement<?> updateProfileSubmitBtn();

    @FindBy("//input[@id='current-password']")
    AtlasWebElement<?> currentPassword();

    @FindBy("//input[@id='new-password']")
    AtlasWebElement<?> newPassword();

    @FindBy("//input[@id='new-password-confirm']")
    AtlasWebElement<?> confirmNewPassword();

    @FindBy("//button[contains(text(),'Change Password')]")
    AtlasWebElement<?> changePasswordSubmitBtn();

    @FindBy("//button[@id='menu']")
    AtlasWebElement<?> navMenu();

    @FindBy("//a[contains(text(),'Home')]")
    AtlasWebElement<?> homeNavMenu();

    @FindBy("//a[normalize-space()='Sign out']")
    AtlasWebElement<?> signOutLink();

    @FindBy("//div[contains(@class,'alert alert-danger mt-3') or contains(@class,'alert alert-success mt-3')]")
    AtlasWebElement<?> globalToastMessage();

    @FindBy("//div[contains(@class,'alert alert-danger') or contains(@class,'alert alert-success')]")
    AtlasWebElement<?> globalErrorMessage();

}