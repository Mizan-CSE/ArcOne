package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AttendanceReportPage;

public class AttendanceReportSteps {
    AttendanceReportPage attendanceReportPage = new AttendanceReportPage();

    @When("the user navigates to {string}")
    public void theUserNavigatesTo(String subModule) {
        attendanceReportPage.navigateToAttendanceScreen(subModule);
    }

    @And("the user enters {string} as {string}")
    public void theUserEntersAs(String fieldName, String dateStr) throws InterruptedException {
        attendanceReportPage.sendDateRange(fieldName, dateStr);
    }

    @And("the user selects status filter as {string}")
    public void theUserSelectsStatusFilterAs(String status) {
        attendanceReportPage.filteredByAttendanceStatus(status);
    }

    @Then("the user should see the attendance results filtered by {string}")
    public void theUserShouldSeeTheAttendanceResultsFilteredBy(String filterType) {
        attendanceReportPage.verifyFilteredResults(filterType);
    }

    @And("the user takes a screenshot of the search results")
    public void theUserTakesAScreenshotOfTheSearchResults() {
        attendanceReportPage.takeScreenShoot("Leave Report");
    }
}
