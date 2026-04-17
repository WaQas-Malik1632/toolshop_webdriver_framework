package pages;

import io.qameta.atlas.webdriver.AtlasWebElement;
import io.qameta.atlas.webdriver.WebPage;
import io.qameta.atlas.webdriver.extension.FindBy;

public interface LoginPage extends WebPage {


    @FindBy("//a[contains(text(),'Sign in')]")
    AtlasWebElement<?> loginMenuBtn();

    @FindBy("//a[@aria-label='Register your account']")
    AtlasWebElement<?> registerYourAccountLinkText();

    @FindBy("//input[@id='email']")
    AtlasWebElement<?> emailInputField();

    @FindBy("//input[@id='password']")
    AtlasWebElement<?> passwordInputField();

    @FindBy("//input[@value='Login']")
    AtlasWebElement<?> loginSubmitBtn();

    @FindBy("//a[@aria-label='Forgot your Password?']")
    AtlasWebElement<?> forgotPasswordLinkText();

    @FindBy("//input[@id='email']")
    AtlasWebElement<?> enterForgotPassEmail();

    @FindBy("//input[@value='Set New Password']")
    AtlasWebElement<?> setNewPassBtn();

    @FindBy("//div[contains(@class,'alert') and contains(@class,'mt-3')]")
    AtlasWebElement<?> globalToastMessage();

    @FindBy("//div[contains(@class,'alert') and not(contains(@class,'mt-3'))]")
    AtlasWebElement<?> globalErrorMessage();

}