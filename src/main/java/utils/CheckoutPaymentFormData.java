package utils;

public class CheckoutPaymentFormData {
    // Login user (Registered)
    private final String email;
    private final String password;

    // Guest user
    private final String guestEmail;
    private final String guestFirstName;
    private final String guestLastName;

    // Billing Info
    private final String street;
    private final String city;
    private final String state;
    private final String country;
    private final String postalCode;

    // Payment Info for Bank
    private final String paymentMethod;
    private final String bankName;
    private final String accountName;
    private final String accountNumber;

    // Payment Info for Credit Card
    private final String creditCardNumber;
    private final String expirationDate;
    private final String cardCvv;
    private final String cardHolderName;


    private CheckoutPaymentFormData(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.guestEmail = builder.guestEmail;
        this.guestFirstName = builder.guestFirstName;
        this.guestLastName = builder.guestLastName;
        this.street = builder.street;
        this.city = builder.city;
        this.state = builder.state;
        this.country = builder.country;
        this.postalCode = builder.postalCode;
        this.paymentMethod = builder.paymentMethod;
        this.bankName = builder.bankName;
        this.accountName = builder.accountName;
        this.accountNumber = builder.accountNumber;
        this.creditCardNumber = builder.creditCardNumber;
        this.expirationDate = builder.expirationDate;
        this.cardCvv = builder.cardCvv;
        this.cardHolderName = builder.cardHolderName;
    }

    // --- Getters ---
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public String getGuestFirstName() {
        return guestFirstName;
    }

    public String getGuestLastName() {
        return guestLastName;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getBankName() {
        return bankName;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }


    // --- Builder Class ---
    public static class Builder {
        private String email;
        private String password;
        private String guestEmail;
        private String guestFirstName;
        private String guestLastName;
        private String street;
        private String city;
        private String state;
        private String country;
        private String postalCode;
        private String paymentMethod;
        private String bankName;
        private String accountName;
        private String accountNumber;

        private String creditCardNumber;
        private String expirationDate;
        private String cardCvv;
        private String cardHolderName;

        // Registered User login details
        public Builder setLoginDetails(String email, String password) {
            this.email = email;
            this.password = password;
            return this;
        }

        // Guest user details
        public Builder setGuestDetails(String guestEmail, String guestFirstName, String guestLastName) {
            this.guestEmail = guestEmail;
            this.guestFirstName = guestFirstName;
            this.guestLastName = guestLastName;
            return this;
        }

        // Billing details method for guest user
        public Builder setBillingAddress(String street, String city, String state, String country, String postalCode, String method) {
            this.street = street;
            this.city = city;
            this.state = state;
            this.country = country;
            this.postalCode = postalCode;

            //Payment method COD
            this.paymentMethod = method;

            return this;
        }

        // Payment method for Credit card
        public Builder setPaymentDetailsForCreditCard(String method, String cardNo, String expiryDate, String cvv, String cardHolderName) {
            this.paymentMethod = method;
            this.creditCardNumber = cardNo;
            this.expirationDate = expiryDate;
            this.cardCvv = cvv;
            this.cardHolderName = cardHolderName;
            return this;
        }

        // Payment details method for registered user
        public Builder setPaymentDetailsForBank(String method, String bank, String accName, String accNumber) {
            this.paymentMethod = method;
            this.bankName = bank;
            this.accountName = accName;
            this.accountNumber = accNumber;
            return this;
        }

        public CheckoutPaymentFormData build() {
            return new CheckoutPaymentFormData(this);
        }
    }

}
