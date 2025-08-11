package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LeaveApplicationPage;

import java.io.IOException;


public class LeaveApplicationSteps {
    LeaveApplicationPage leaveApplicationPage = new LeaveApplicationPage();

    public boolean shouldSkipCheckIn() {
        return leaveApplicationPage.isElementPresentByAccessibilityId("Check-OUT");
    }

    @When("the user taps the {string} button")
    public void theUserTapsTheButton(String checkIn) {
        if (shouldSkipCheckIn()) {
            System.out.println("Check-OUT present. Skipping capture picture.");
            return;
        }
        leaveApplicationPage.setCheckIN(checkIn);
    }

    @And("the camera opens and the user captures a picture")
    public void theCameraOpensAndTheUserCapturesAPicture() {
        if (shouldSkipCheckIn()) {
            System.out.println("Check-OUT present. Skipping capture picture.");
            return;
        }
        leaveApplicationPage.capturePicture();
    }

    @And("the user confirms the {string}")
    public void theUserConfirmsTheCheckIn(String checkIn) {
        if (shouldSkipCheckIn()) {
            System.out.println("Check-OUT present. Skipping capture picture.");
            return;
        }
        leaveApplicationPage.confirmCheckIN(checkIn);
    }

    @When("the user taps the {string} option")
    public void theUserTapsTheOption(String menuName) {
        leaveApplicationPage.tapOnMenu(menuName);
    }

    @And("the user tap the add application")
    public void theUserTapTheAddApplication() {
        leaveApplicationPage.tapApplicationButton();
    }

    @And("the user creates a new leave application with:")
    public void theUserCreatesANewLeaveApplicationWith(DataTable dataTable) throws InterruptedException {
        leaveApplicationPage.leaveApplicationInformation(dataTable);
    }

    @And("the user submits the leave application")
    public void theUserSubmitsTheLeaveApplication() {
        leaveApplicationPage.setApplyLeave();
    }

    @Then("the user should see the leave confirmation or listing")
    public void theUserShouldSeeTheLeaveConfirmationOrListing() throws InterruptedException {
        leaveApplicationPage.handleLeaveConfirmation();
    }

    @And("the user takes a screenshot of the confirmation")
    public void theUserTakesAScreenshotOfTheConfirmation() {
        leaveApplicationPage.takeScreenShoot("Leave Application");
    }
}
