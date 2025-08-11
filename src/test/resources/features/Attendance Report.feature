@Attendance-Report
Feature: Attendance Report Search with "On Leave" Status

  Background:
    Given the user launches and login the ABC Company mobile app

  Scenario: Search attendance with "On Leave" status for a specific date range
    When the user navigates to "My Attendance"
    And the user enters "From Date" as "2025-06-01"
    And the user enters "To Date" as "2025-07-30"
    And the user selects status filter as "On Leave"
    Then the user should see the attendance results filtered by "On Leave"
    And the user takes a screenshot of the search results
