package utils;

public class CustomerRegistrationFormData {
    private final String firstName;
    private final String lastName;
    private final String birthDate;
    private final String street;
    private final String postalCode;
    private final String city;
    private final String state;
    private final String country;
    private final String phone;
    private final String email;
    private final String password;

    public CustomerRegistrationFormData(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.birthDate = builder.birthDate;
        this.street = builder.street;
        this.postalCode = builder.postalCode;
        this.city = builder.city;
        this.state = builder.state;
        this.country = builder.country;
        this.phone = builder.phone;
        this.email = builder.email;
        this.password = builder.password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
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

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Builder Class
    public static class Builder {
        private String firstName;
        private String lastName;
        private String birthDate;
        private String street;
        private String postalCode;
        private String city;
        private String state;
        private String country;
        private String phone;
        private String email;
        private String password;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder birthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public CustomerRegistrationFormData build() {
            return new CustomerRegistrationFormData(this);
        }
    }

}
