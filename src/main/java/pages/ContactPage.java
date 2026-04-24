package pages;

import io.qameta.atlas.webdriver.AtlasWebElement;
import io.qameta.atlas.webdriver.WebPage;
import io.qameta.atlas.webdriver.extension.FindBy;

public interface ContactPage extends WebPage {

    @FindBy("//a[contains(text(),'Contact')]")
    AtlasWebElement<?> contactMenu();

    @FindBy("//input[@id='first_name']")
    AtlasWebElement<?> inputFirstName();

    @FindBy("//input[@id='last_name']")
    AtlasWebElement<?> inputLastName();

    @FindBy("//input[@id='email']")
    AtlasWebElement<?> inputEmailAddress();

    @FindBy("//select[@id='subject']")
    AtlasWebElement<?> selectSubject();

    @FindBy("//textarea[@id='message']")
    AtlasWebElement<?> inputMessage();

    @FindBy("//input[@id='attachment']")
    AtlasWebElement<?> addAttachment();

    @FindBy("//input[@value='Send']")
    AtlasWebElement<?> sendSubmitBtn();

    @FindBy("//div[contains(@class,'alert alert-danger mt-3') or contains(@class,'alert alert-success mt-3')]")
    AtlasWebElement<?> globalToastMessage();

    @FindBy("//div[contains(@class,'alert alert-danger') or contains(@class,'alert alert-success')]")
    AtlasWebElement<?> globalErrorMessage();

}