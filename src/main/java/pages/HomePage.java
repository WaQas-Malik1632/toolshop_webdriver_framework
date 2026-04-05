package pages;

import io.qameta.atlas.webdriver.AtlasWebElement;
import io.qameta.atlas.webdriver.WebPage;
import io.qameta.atlas.webdriver.extension.FindBy;

public interface HomePage extends WebPage {

    @FindBy(value = "//a[contains(text(),'Sign in')]")
    AtlasWebElement<?> loginMenuBtn();

    @FindBy("//a[contains(text(),'Home')]")
    AtlasWebElement<?> homeNavMenu();

    @FindBy("//*[@id='Layer_1']")
    AtlasWebElement<?> homePageLogo();

    @FindBy("//img[@alt='Combination Pliers']")
    AtlasWebElement<?> combPliersItem();

    @FindBy("//img[@alt='Pliers']")
    AtlasWebElement<?> PliersItem();

    @FindBy("//img[@alt='Bolt Cutters']")
    AtlasWebElement<?> boltCluttersItem();

    @FindBy("//img[@alt='Thor Hammer']")
    AtlasWebElement<?> thorHammerItem();

    @FindBy("//img[@alt='Claw Hammer']")
    AtlasWebElement<?> clawHammer();

    @FindBy("//img[@alt='Long Nose Pliers']")
    AtlasWebElement<?> longNosePliers();

    @FindBy("//a[@aria-label='Page-2']")
    AtlasWebElement<?> page2();

    @FindBy("//img[@alt='Phillips Screwdriver']")
    AtlasWebElement<?> philipScrewdriver();

    @FindBy("//button[@id='btn-increase-quantity']//fa-icon[@class='ng-fa-icon']//*[name()='svg']")
    AtlasWebElement<?> IncQuantityOfItem();

    @FindBy("//button[@id='btn-add-to-cart']")
    AtlasWebElement<?> addToCartBtn();

    @FindBy("//a[@aria-label='cart']")
    AtlasWebElement<?> openCartBtn();

    @FindBy("//button[text()='pages.checkout.cart.continue-shopping']")
    AtlasWebElement<?> continueShoppingBtn();

    @FindBy("//tbody/tr[1]/td[5]/a[1]/fa-icon[1]//*[name()='svg']")
    AtlasWebElement<?> deleteItem();

    @FindBy("//button[@data-test='proceed-1']")
    AtlasWebElement<?> proceedToCheckoutBtn();

    @FindBy("//div[@aria-label='Product added to shopping cart.']")
    AtlasWebElement<?> productAddedToCardSuccess();

    @FindBy("//div[@aria-label='Product added to your favorites list.']")
    AtlasWebElement<?> productAddedToFavoriteSuccess();

    @FindBy("(//button[contains(text(),'Proceed to checkout')])[2]")
    AtlasWebElement<?> proceedToCheckoutBtnAfterLogin();

    @FindBy("(//button[contains(text(),'Proceed to checkout')])[3]")
    AtlasWebElement<?> proceedToCheckoutBtnAfterBillingAddress();

    //Login user fields
    @FindBy("//input[@id='email']")
    AtlasWebElement<?> emailInputField();

    @FindBy("//input[@id='password']")
    AtlasWebElement<?> passwordInputField();

    @FindBy("//input[@value='Login']")
    AtlasWebElement<?> loginSubmitBtn();

    //Billing address fields
    @FindBy("//input[@id='street']")
    AtlasWebElement<?> streetInputField();

    @FindBy("//input[@id='city']")
    AtlasWebElement<?> cityInputField();

    @FindBy("//input[@id='state']")
    AtlasWebElement<?> stateInputField();

    @FindBy("//input[@id='country']")
    AtlasWebElement<?> countryInputField();

    @FindBy("//input[@id='postal_code']")
    AtlasWebElement<?> postalCodeInputField();

    //Payment related fields
    @FindBy("//select[@id='payment-method']")
    AtlasWebElement<?> selectPaymentMethod();

    @FindBy("//input[@id='credit_card_number']")
    AtlasWebElement<?> inputCreditCardNo();

    @FindBy("//input[@id='expiration_date']")
    AtlasWebElement<?> inputCardExpiryDate();

    @FindBy("//input[@id='cvv']")
    AtlasWebElement<?> inputCardCvv();

    @FindBy("//input[@id='card_holder_name']")
    AtlasWebElement<?> inputCardHolderName();

    @FindBy("//input[@id='bank_name']")
    AtlasWebElement<?> bankNameInputField();

    @FindBy("//input[@id='account_name']")
    AtlasWebElement<?> accountNameInputField();

    @FindBy("//input[@id='account_number']")
    AtlasWebElement<?> accountNoInputField();

    @FindBy("//button[contains(text(),'Confirm')]")
    AtlasWebElement<?> confirmBtn();

    @FindBy("//div[contains(text(),'Payment was successful')]")
    AtlasWebElement<?> paymentSuccess();

    @FindBy("//div[@aria-label='Product deleted.']")
    AtlasWebElement<?> productDeletedFromCartMsge();

    //Guest User
    @FindBy("//a[contains(text(),'Continue as Guest')]")
    AtlasWebElement<?> switchToGuestTab();

    @FindBy("//input[@id='guest-email']")
    AtlasWebElement<?> inputGuestEmail();

    @FindBy("//input[@id='guest-first-name']")
    AtlasWebElement<?> inputGuestFirstName();

    @FindBy("//input[@id='guest-last-name']")
    AtlasWebElement<?> inputGuestLastName();

    @FindBy("//input[@value='Continue as Guest']")
    AtlasWebElement<?> continueAsGuestBtn();

    @FindBy("//div[contains(@class,'alert-danger') or contains(@class,'alert-success')]")
    AtlasWebElement<?> globalToastMessage();
}
