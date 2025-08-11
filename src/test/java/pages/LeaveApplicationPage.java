package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.cucumber.datatable.DataTable;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class LeaveApplicationPage extends BasePage{
    String name;
    @AndroidFindBy(accessibility = "com.android.permissioncontroller:id/permission_allow_foreground_only_button")
    List<WebElement> pictureCapturePermission;

    @AndroidFindBy(accessibility = "Shutter")
    WebElement capturePictureButton;

    @AndroidFindBy(xpath = "//*[@content-desc='Done' or @content-desc='Review done']")
    WebElement confirmCapture;

    @AndroidFindBy(accessibility = "OK")
    WebElement oKPopUpMessage;

    @AndroidFindBy(accessibility = "\uE627")
    WebElement leaveApplicationPlusIcon;

    @AndroidFindBy(accessibility = "+, Application")
    WebElement leaveApplicationButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(12)")
    WebElement employeeName;

    @AndroidFindBy(accessibility = "\uF078, Leave Type*")
    WebElement selectLeaveType;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(2)")
    WebElement leaveReason;

    @AndroidFindBy(accessibility = "Apply")
    WebElement applyLeave;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Date overlap') or contains(@text, 'successfully')]")
    WebElement leaveConfirmationMessage;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Search...\")")
    WebElement searchByName;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc, '.,')]")
    List<WebElement> leaveList;


    public void setCheckIN(String checkIn){
        setWait().until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId(checkIn))).click();
//        if(!pictureCapturePermission.isEmpty()){
//            pictureCapturePermission.getFirst().click();
//        }else{
//            System.out.println("Already permitted");
//        }
    }
    public void capturePicture(){
        setWait().until(ExpectedConditions.visibilityOf(capturePictureButton));
        capturePictureButton.click();

        setWait().until(ExpectedConditions.visibilityOf(confirmCapture));
        confirmCapture.click();
    }
    public void confirmCheckIN(String checkIn){
        // Get a fresh Check-IN button reference and click
        WebElement checkInButton = setWait().until(
                ExpectedConditions.refreshed(
                        ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId(checkIn))
                )
        );
        checkInButton.click();

        setWait().until(ExpectedConditions.visibilityOf(oKPopUpMessage)).click();
    }


    public boolean isElementPresentByAccessibilityId(String accessibilityId) {
        try {
            driver.findElement(AppiumBy.accessibilityId(accessibilityId));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void tapOnMenu(String menuName){
        setWait().until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(menuName))).click();
    }
    public void tapApplicationButton(){
        setWait().until(ExpectedConditions.visibilityOf(leaveApplicationPlusIcon)).click();
        setWait().until(ExpectedConditions.visibilityOf(leaveApplicationButton)).click();
    }

    public void leaveApplicationInformation(DataTable dataTable) throws InterruptedException {
        Map<String, String> leaveData = dataTable.asMap(String.class, String.class);
        name = employeeName.getText().trim();
        setWait().until(ExpectedConditions.visibilityOf(selectLeaveType)).click();
        setWait().until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId(leaveData.get("Leave Type")))).click();

        sendDateRange("From Date", leaveData.get("From Date"));
        sendDateRange("To Date", leaveData.get("To Date"));

        WebElement reason = setWait().until(ExpectedConditions.visibilityOf(leaveReason));
        reason.clear();
        reason.sendKeys(leaveData.get("Reason"));
    }

    public void setApplyLeave(){
        applyLeave.click();
    }

    public void handleLeaveConfirmation() throws InterruptedException{
        String message = leaveConfirmationMessage.getText();
        if (message.contains("Date overlap") || message.contains("insufficient permissions")){
            takeScreenShoot("Leave Application Error");
            Assert.fail("Leave application failed due to error: " + message);
        } else{
            oKPopUpMessage.click();
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            setWait().until(ExpectedConditions.visibilityOf(searchByName)).sendKeys("Nahid");
            sendDateRange("Leave Apply Date",today);

            Assert.assertFalse("Expected leave applications, but the list was empty.", leaveList.isEmpty());

            takeScreenShoot("Leave Application");
        }

    }
}
