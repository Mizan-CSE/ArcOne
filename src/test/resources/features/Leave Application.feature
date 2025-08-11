@Leave-Application
Feature: Check-IN and Leave Application Submission

  Background:
    Given the user launches and login the ABC Company mobile app

  Scenario: Perform check-in and submit a leave application
    When the user taps the "Check-IN" button
    And the camera opens and the user captures a picture
    And the user confirms the "Check-IN"
    When the user taps the "Leave Application" option
    And the user tap the add application
    And the user creates a new leave application with:
      | Leave Type | Annual Leave |
      | From Date  | 2025-10-01   |
      | To Date    | 2025-10-02   |
      | Reason     | Family Event |
    And the user submits the leave application
    Then the user should see the leave confirmation or listing
    And the user takes a screenshot of the confirmation
