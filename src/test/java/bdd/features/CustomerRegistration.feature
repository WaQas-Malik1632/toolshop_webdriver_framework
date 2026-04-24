@Authentication
Feature: Customer Registration
  As a new customer
  I want to be able to register an account
  So that I can use the application

  Background:
    Given user is on the registration page

  @Smoke
  Scenario: Validate Customer Register Page Title
    Then registration page title should be correct

  @Smoke @E2E @Critical
  Scenario: Customer Registration - Positive Flow
    When user registers with unique email and valid details
    Then user should be redirected to login page after registration

  @E2E
  Scenario: Customer Registration - Duplicate Email Validation
    When user attempts to register with an already existing email
    Then user should see duplicate email error