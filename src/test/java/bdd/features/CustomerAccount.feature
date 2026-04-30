@CustomerAccount
Feature: Customer Account Management
  As a registered customer
  I want to manage my account
  So that I can update my profile and settings

  Background:
    Given user logs in as a customer

  @Smoke @E2E @Critical
  Scenario: Validate user logged out flow
    When user logs out from account
    Then user should be redirected to login page after logout

  @E2E @Critical
  Scenario: Validate accounts profile data update
    When user navigates to profile page from accounts page
    When user updates profile details
    Then profile should be updated successfully

  @E2E @Critical @profile
  Scenario: Validate update password flow
    When user navigates to profile page from accounts page
    When user updates password with valid credentials
    Then password should be updated successfully and redirected on login page

  @E2E @Critical @profile
  Scenario: Validate update password - Negative flow
    When user navigates to profile page from accounts page
    When user attempts to update password with mismatched confirmation
    Then user should see password mismatch error