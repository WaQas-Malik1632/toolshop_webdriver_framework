package steps;

import io.qameta.allure.Step;
import utils.CheckoutPaymentFormData;
import org.openqa.selenium.WebDriver;

public class HomePageSteps extends BaseSteps {

    public HomePageSteps(WebDriver driver) {
        super(driver);
    }

    @Step("Navigate to Home Page")
    public void navigateToHomePage() {
        button.waitAndClick(onHomePage().homeNavMenu());
        onHomePage().homePageLogo().isDisplayed();
        log.info("Navigated to Home Page");
    }

    @Step("Verify Home Page Logo is visible and clickable")
    public boolean isHomePageLogoVisible() {
        return button.isClickable(onHomePage().homePageLogo());
    }

    @Step("Purchase multiple products")
    public String purchaseMultipleProducts(CheckoutPaymentFormData formData) {

        log.info("<<< STARTING MULTIPLE PRODUCTS PURCHASE FLOW >>>");

        button.waitAndClick(onHomePage().PliersItem());
        button.waitAndClick(onHomePage().addToCartBtn());

        button.waitAndClick(onHomePage().openCartBtn());
        button.waitAndClick(onHomePage().continueShoppingBtn());

        scrollIntoView(onHomePage().thorHammerItem());
        button.waitAndClick(onHomePage().thorHammerItem());
        button.waitAndClick(onHomePage().addToCartBtn());

        button.waitAndClick(onHomePage().openCartBtn());
        button.waitAndClick(onHomePage().continueShoppingBtn());

        scrollIntoView(onHomePage().page2());
        button.waitAndClick(onHomePage().page2());

        button.waitAndClick(onHomePage().philipScrewdriver());
        button.waitAndClick(onHomePage().IncQuantityOfItem());
        button.waitAndClick(onHomePage().IncQuantityOfItem());
        button.waitAndClick(onHomePage().addToCartBtn());

        button.waitAndClick(onHomePage().openCartBtn());
        button.waitAndClick(onHomePage().deleteItem());

        button.waitAndClick(onHomePage().proceedToCheckoutBtn());
        button.waitAndClick(onHomePage().proceedToCheckoutBtnAfterLogin());
        input.enterText(onHomePage().houseNoInputField(), formData.getHouseNo());
        button.waitAndClick(onHomePage().proceedToCheckoutBtnAfterBillingAddress());

        dropdown.selectByVisibleText(onHomePage().selectPaymentMethod(), formData.getPaymentMethod());

        input.enterText(onHomePage().inputCreditCardNo(), formData.getCreditCardNumber());
        input.enterText(onHomePage().inputCardExpiryDate(), formData.getExpirationDate());
        input.enterText(onHomePage().inputCardCvv(), formData.getCardCvv());
        input.enterText(onHomePage().inputCardHolderName(), formData.getCardHolderName());

        button.waitAndClick(onHomePage().confirmBtn());

        String message = label.waitForToast(onHomePage().globalToastMessage());

        log.info("Payment result message: {}", message);
        log.info("<<< MULTIPLE PRODUCTS PURCHASE FLOW COMPLETED >>>");

        return message;
    }

    @Step("Purchase multiple products with Credit Card")
    public String purchaseMultipleProductsWithCreditCard(
            String cardNumber,
            String expiryDate,
            String cvv,
            String cardHolderName,
            String houseNo) {

        CheckoutPaymentFormData formData = new CheckoutPaymentFormData.Builder()
                .setPaymentDetailsForCreditCard("Credit Card", cardNumber, expiryDate, cvv, cardHolderName)
                .setHouseNo(houseNo)
                .build();

        return purchaseMultipleProducts(formData);
    }

    @Step("Purchase item as guest user")
    public String purchaseItemFlowAsGuestUser(CheckoutPaymentFormData formData) {

        log.info("<<< STARTING PURCHASE FLOW AS GUEST USER >>>");

        button.waitAndClick(onHomePage().boltCluttersItem());
        button.waitAndClick(onHomePage().addToCartBtn());
        button.waitAndClick(onHomePage().openCartBtn());

        button.waitAndClick(onHomePage().proceedToCheckoutBtn());
        button.waitAndClick(onHomePage().switchToGuestTab());

        input.enterText(onHomePage().inputGuestEmail(), formData.getGuestEmail());
        input.enterText(onHomePage().inputGuestFirstName(), formData.getGuestFirstName());
        input.enterText(onHomePage().inputGuestLastName(), formData.getGuestLastName());

        button.waitAndClick(onHomePage().continueAsGuestBtn());
        button.waitAndClick(onHomePage().proceedToCheckoutBtnAfterLogin());

        input.enterText(onHomePage().streetInputField(), formData.getStreet());
        input.enterText(onHomePage().cityInputField(), formData.getCity());
        input.enterText(onHomePage().stateInputField(), formData.getState());
        input.enterText(onHomePage().countryInputField(), formData.getCountry());
        input.enterText(onHomePage().postalCodeInputField(), formData.getPostalCode());
        input.enterText(onHomePage().houseNoInputField(), formData.getHouseNo());
        button.waitAndClick(onHomePage().proceedToCheckoutBtnAfterBillingAddress());

        dropdown.selectByVisibleText(onHomePage().selectPaymentMethod(), formData.getPaymentMethod());

        button.waitAndClick(onHomePage().confirmBtn());

        String message = label.waitForToast(onHomePage().globalToastMessage());

        log.info("Payment result: {}", message);
        log.info("<<< GUEST PURCHASE FLOW COMPLETED >>>");

        return message;
    }

    @Step("Purchase item as guest with Cash on Delivery")
    public String purchaseItemFlowAsGuestWithCod(
            String guestEmail,
            String guestFirstName,
            String guestLastName,
            String street,
            String city,
            String state,
            String country,
            String postalCode,
            String houseNo) {

        CheckoutPaymentFormData formData = new CheckoutPaymentFormData.Builder()
                .setGuestDetails(guestEmail, guestFirstName, guestLastName)
                .setBillingAddress(street, city, state, country, postalCode, houseNo, "Cash on Delivery")
                .build();

        return purchaseItemFlowAsGuestUser(formData);
    }

    @Step("Purchase item with Bank Transfer")
    public String purchaseItemFlow(CheckoutPaymentFormData formData) {

        log.info("<<< STARTING PURCHASE ITEM FLOW >>>");

        button.waitAndClick(onHomePage().combPliersItem());
        button.waitAndClick(onHomePage().addToCartBtn());
        button.waitAndClick(onHomePage().openCartBtn());

        button.waitAndClick(onHomePage().proceedToCheckoutBtn());
        button.waitAndClick(onHomePage().proceedToCheckoutBtnAfterLogin());
        input.enterText(onHomePage().houseNoInputField(), formData.getHouseNo());
        button.waitAndClick(onHomePage().proceedToCheckoutBtnAfterBillingAddress());
        dropdown.selectByVisibleText(onHomePage().selectPaymentMethod(), formData.getPaymentMethod());
        input.enterText(onHomePage().bankNameInputField(), formData.getBankName());
        input.enterText(onHomePage().accountNameInputField(), formData.getAccountName());
        input.enterText(onHomePage().accountNoInputField(), formData.getAccountNumber());

        button.waitAndClick(onHomePage().confirmBtn());

        String message = label.waitForToast(onHomePage().globalToastMessage());

        log.info("Payment result: {}", message);
        log.info("<<< PURCHASE FLOW COMPLETED >>>");

        return message;
    }

    @Step("Purchase item with Bank Transfer (with custom house number)")
    public String purchaseItemWithBankTransfer(
            String bankName,
            String accountName,
            String accountNumber,
            String houseNo) {

        CheckoutPaymentFormData formData = new CheckoutPaymentFormData.Builder()
                .setPaymentDetailsForBank("Bank Transfer", bankName, accountName, accountNumber)
                .setHouseNo(houseNo)
                .build();

        return purchaseItemFlow(formData);
    }
}