@Authentication
Feature: User Authentication
  As a customer
  I want to be able to login to the application
  So that I can access my account

  Background:
    Given user is on the login page

  @Smoke
  Scenario: Validate Login Page Title
    Then login page title should be correct

  @Smoke @Critical @Login
  Scenario: Customer Login - Positive Flow
    When user logs in with valid credentials
    Then user is redirected to the account page

  @Smoke @E2E @Critical
  Scenario: Customer Login - Negative Flow
    When user logs in with invalid credentials
    Then user should see an authentication error

  @E2E @Critical
  Scenario: Reset Password - Registered Email
    When user requests a password reset for a registered email
    Then user should receive a password reset confirmation

  @E2E @Critical
  Scenario: Reset Password - Negative Flow - Unregistered Email
    When user requests a password reset for an unregistered email
    Then user should see an invalid email error