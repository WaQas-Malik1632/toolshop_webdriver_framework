@ContactUs
Feature: Contact Us Functionality
  As a customer
  I want to be able to contact the support team
  So that I can get help with my queries

  Background:
    Given user is on the contact us page

  @Smoke @E2E
  Scenario: Validate contact page title
    Then contact us page title should be correct

  @E2E
  Scenario: Validate customer can contact with Admin about payment issue
    When user can fill the form and submit it
    Then success toast message should be correct