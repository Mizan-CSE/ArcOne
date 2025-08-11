package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendanceReportPage extends BasePage {

    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc, 'Status')]")
    WebElement attendanceStatus;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Status')]/following-sibling::android.widget.TextView[1]")
    WebElement statusFilterLocator;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc, ', ')]/android.widget.TextView[3]")
    List<WebElement> statusElements;

    public void navigateToAttendanceScreen(String subModule) {
        setWait().until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId(subModule))).click();
    }


    public void filteredByAttendanceStatus(String status) {
        setWait().until(ExpectedConditions.elementToBeClickable(attendanceStatus)).click();
        setWait().until(ExpectedConditions.elementToBeClickable(AppiumBy.accessibilityId(status))).click();
    }

    public void verifyFilteredResults(String filterType) {
        // Verify the status filter is set correctly
        setWait().until(ExpectedConditions.visibilityOf(statusFilterLocator));
        Assert.assertEquals("Status filter not set to ", filterType, statusFilterLocator.getText().trim());
        // Verify all displayed results match the filter
        if (!statusElements.isEmpty()) {
            for (WebElement statusElement : statusElements) {
                Assert.assertEquals("Result status doesn't match filter", statusElement.getText(), filterType);
            }
        } else {
            System.out.println("No record found");
        }

    }
}
