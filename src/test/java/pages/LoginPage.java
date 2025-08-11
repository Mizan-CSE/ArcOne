package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;


public class LoginPage extends BasePage {

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Enter Email']")
    WebElement usernameField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='Enter Password']")
    WebElement passwordField;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Login']")
    WebElement loginButton;

    By locationPermission = AppiumBy.accessibilityId("android:id/button1");

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Self Overview\")")
    WebElement homePage;

    public void setUsername(String username) {
        setWait().until(ExpectedConditions.visibilityOf(usernameField));
        usernameField.sendKeys(username);
    }

    public void setPassword(String password) {
        setWait().until(ExpectedConditions.elementToBeClickable(passwordField));
        passwordField.sendKeys(password);
    }

    public void tapLoginButton() {
        setWait().until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    public void setHomePage() {
        //Allow location
//        setWait().until(ExpectedConditions.visibilityOfElementLocated(locationPermission)).click();

        //Homepage validation
        setWait().until(ExpectedConditions.visibilityOf(homePage));
        Assert.assertEquals("Self Overview", homePage.getText());
    }

    public void performLogin(String user, String pass) {
        if (driver == null) {
            driver = DriverManager.launchApp();// Ensure WebDriver is initialized
        }
        setUsername(user);
        setPassword(pass);
        tapLoginButton();

    }
}