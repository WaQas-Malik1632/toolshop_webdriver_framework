@HomePage
Feature: Home Page Functionality
  As a customer
  I want to interact with the home page
  So that I can browse and purchase products

  @Smoke @E2E @Ignore
  Scenario: Validate Home page logo is visible
    Given user is on the home page
    Then home page logo should be visible

  @E2E @Critical
  Scenario: Validate Customer can purchase multiple products with Credit Card
    Given user is logged in and on home page
    When user purchases multiple products with credit card
    Then payment should be successful

  @E2E @Critical
  Scenario: Validate Customer can purchase any product with Bank Account
    Given user is logged in and on home page
    When user purchases product with bank transfer
    Then payment should be successful

  @E2E @Critical
  Scenario: Validate guest user can purchase any product with COD
    Given user is on the home page
    When guest user purchases product with cash on delivery
    Then payment should be successful