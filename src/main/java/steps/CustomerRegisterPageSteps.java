package steps;

import io.qameta.allure.Step;
import model.CustomerRegistrationFormData;
import org.openqa.selenium.WebDriver;

public class CustomerRegisterPageSteps extends BaseSteps {

    public CustomerRegisterPageSteps(WebDriver driver) {
        super(driver);
    }

    @Step("Navigate to Registration Page")
    public CustomerRegisterPageSteps navigateToRegisterPage() {
        button.waitAndClick(onHomePage().loginMenuBtn());

        log.info("Scrolling to Register link");
        scrollIntoView(onLoginPage().registerYourAccountLinkText());
        onLoginPage().registerYourAccountLinkText().isDisplayed();

        log.info("Clicking to Register link text");
        button.waitAndClick(onLoginPage().registerYourAccountLinkText());
        onRegistrationPage().registerSubmitBtn().isDisplayed();

        log.info("Navigated to Registration Page");
        return this;
    }

    @Step("Get Customer Registration Page Title")
    public String getCustomerRegisterPageTitle() {
        return getPageTitle();
    }

    @Step("Fill Customer Registration Form")
    public CustomerRegisterPageSteps customerRegistrationFlow (CustomerRegistrationFormData registerFormData) {

        log.info("Starting customer registration flow");

        input.enterText(onRegistrationPage().firstNameInputField(), registerFormData.getFirstName());
        input.enterText(onRegistrationPage().lastNameInputField(), registerFormData.getLastName());
        input.enterText(onRegistrationPage().dateOfBirthField(), registerFormData.getBirthDate());
        input.enterText(onRegistrationPage().streetInputField(), registerFormData.getStreet());
        input.enterText(onRegistrationPage().postalCodeInputField(), registerFormData.getPostalCode());
        input.enterText(onRegistrationPage().cityInputField(), registerFormData.getCity());
        input.enterText(onRegistrationPage().stateInputField(), registerFormData.getState());

        dropdown.selectByVisibleText(onRegistrationPage().countrySelect(), registerFormData.getCountry());

        input.enterText(onRegistrationPage().phoneInputField(), registerFormData.getPhone());
        input.enterText(onRegistrationPage().emailInputField(), registerFormData.getEmail());
        input.enterText(onRegistrationPage().passwordInputField(), registerFormData.getPassword());

        button.waitAndClick(onRegistrationPage().registerSubmitBtn());

        log.info("Customer registration form submitted");
        return this;
    }

    @Step("Register Customer with details")
    public CustomerRegisterPageSteps registerCustomer(
            String firstName,
            String lastName,
            String birthDate,
            String street,
            String postalCode,
            String city,
            String state,
            String country,
            String phone,
            String email,
            String password) {

        CustomerRegistrationFormData registerFormData = new CustomerRegistrationFormData.Builder()
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .street(street)
                .postalCode(postalCode)
                .city(city)
                .state(state)
                .country(country)
                .phone(phone)
                .email(email)
                .password(password)
                .build();

        return customerRegistrationFlow(registerFormData);
    }

    @Step("Capture Registration Result")
    public String captureRegistrationResult() {
        return handleRegistrationResult(onRegistrationPage().globalToastMessage());
    }

}
