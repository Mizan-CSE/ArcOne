package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class BasePage extends DriverManager {
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'From')]")
    WebElement fromDate;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'To')]")
    WebElement toDate;

    @AndroidFindBy(accessibility = "\uF073, Apply Date")
    WebElement leaveApplicationDate;

    @AndroidFindBy(id = "android:id/datePicker")
    WebElement datePicker;

    @AndroidFindBy(id = "android:id/date_picker_header_year")
    WebElement displayedYear;

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Previous month']")
    WebElement prevBtn;

    @AndroidFindBy(xpath = "//android.widget.ImageButton[@content-desc='Next month']")
    WebElement nextBtn;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc, ' ')]")
    WebElement dayView;

    @AndroidFindBy(id = "android:id/button1")
    WebElement okDatePicker;


    public BasePage() {
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(Integer.parseInt(props.getString("wait")))), this);
    }

    public WebDriverWait setWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(60));
    }

    public void sendDateRange(String fieldName, String dateStr) throws InterruptedException {
        // Parse date string
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int targetYear = date.getYear();
        Month targetMonth = date.getMonth();
        int targetDay = date.getDayOfMonth();

        // Open the date picker for the specified field
        switch (fieldName) {
            case "From Date":
                setWait().until(ExpectedConditions.elementToBeClickable(fromDate)).click();
                break;
            case "To Date":
                setWait().until(ExpectedConditions.elementToBeClickable(toDate)).click();
                break;
            case "Leave Apply Date":
                setWait().until(ExpectedConditions.elementToBeClickable(leaveApplicationDate)).click();
                break;
            default:
                throw new IllegalArgumentException("Unknown field: " + fieldName);
        }

        setWait().until(ExpectedConditions.visibilityOf(datePicker));

        // Get the currently displayed year text (e.g., "2025")
        String displayedYearText = displayedYear.getText();
        int year = Integer.parseInt(displayedYearText);

        if (year != targetYear) {
            // Only click and select year if targetYear is different
            displayedYear.click();
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().resourceId(\"android:id/date_picker_year_picker\"))" +
                            ".scrollIntoView(new UiSelector().text(\"" + targetYear + "\"));"
            )).click();
        } else {
            System.out.println("Target year is already displayed: " + targetYear);
        }

        // --- Navigate to correct month ---


        int attempts = 0;
        final int MAX_ATTEMPTS = 12;

        while (attempts++ < MAX_ATTEMPTS) {
            // Get the first visible day's content description
            String contentDesc = dayView.getAttribute("content-desc");

            // Parse month from content description (format: "01 June 2025")
            String monthStr = contentDesc.split(" ")[1];
            Month currentMonth = Month.valueOf(monthStr.toUpperCase());

            if (currentMonth == targetMonth) {
                break; // Found correct month
            }

            // Decide direction to navigate
            if (currentMonth.getValue() < targetMonth.getValue()) {
                nextBtn.click();
            } else {
                prevBtn.click();
            }

            // Add small delay to allow UI to update
            Thread.sleep(300);
        }

        if (attempts >= MAX_ATTEMPTS) {
            throw new RuntimeException("Failed to navigate to target month after " + MAX_ATTEMPTS + " attempts");
        }

        // Select the day
        String dayString = String.valueOf(targetDay);
        driver.findElement(By.xpath("//android.view.View[@text='" + dayString + "']")).click();

        // Click OK button
        okDatePicker.click();
    }

    public void takeScreenShoot(String fileName) {
        // Take screenshot
        File srcFile = driver.getScreenshotAs(OutputType.FILE);
        // Define destination path (you can customize file path)
        String screenshotPath = System.getProperty("user.dir") + "/screenshots/"+fileName + System.currentTimeMillis() + ".png";
        // Save screenshot file
        try {
            FileHandler.copy(srcFile, new File(screenshotPath));
            System.out.println("Screenshot saved at: " + screenshotPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
