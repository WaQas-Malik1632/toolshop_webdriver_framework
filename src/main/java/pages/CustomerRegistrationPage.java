package pages;

import io.qameta.atlas.webdriver.AtlasWebElement;
import io.qameta.atlas.webdriver.WebPage;
import io.qameta.atlas.webdriver.extension.FindBy;

public interface CustomerRegistrationPage extends WebPage {

    @FindBy("//a[normalize-space()='Sign in']")
    AtlasWebElement<?> loginMenuBtn();

    @FindBy("//a[@aria-label='Register your account']")
    AtlasWebElement<?> registerYourAccountLinkText();

    @FindBy("//input[@id='first_name']")
    AtlasWebElement<?> firstNameInputField();

    @FindBy("//input[@id='last_name']")
    AtlasWebElement<?> lastNameInputField();

    @FindBy("//input[@id='dob']")
    AtlasWebElement<?> dateOfBirthField();

    @FindBy("//input[@id='street']")
    AtlasWebElement<?> streetInputField();

    @FindBy("//input[@id='postal_code']")
    AtlasWebElement<?> postalCodeInputField();

    @FindBy("//input[@id='city']")
    AtlasWebElement<?> cityInputField();

    @FindBy("//input[@id='state']")
    AtlasWebElement<?> stateInputField();

    @FindBy("//select[@id='country']")
    AtlasWebElement<?> countrySelect();

    @FindBy("//input[@id='phone']")
    AtlasWebElement<?> phoneInputField();

    @FindBy("//input[@id='email']")
    AtlasWebElement<?> emailInputField();

    @FindBy("//input[@id='password']")
    AtlasWebElement<?> passwordInputField();

    @FindBy("//button[@type='submit']")
    AtlasWebElement<?> registerSubmitBtn();

    @FindBy("//div[contains(@class,'alert alert-danger mt-3') or contains(@class,'alert alert-success mt-3')]")
    AtlasWebElement<?> globalToastMessage();

    @FindBy("//div[contains(@class,'alert alert-danger') or contains(@class,'alert alert-success')]")
    AtlasWebElement<?> globalErrorMessage();

}