package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;

public class DriverManager {
    public static AndroidDriver driver;
    public static ResourceBundle props = ResourceBundle.getBundle("config");
    public static String appDir = new File(System.getProperty("user.dir"), props.getString("appPath")).getAbsolutePath();

    public static AndroidDriver launchApp() {
        try {
            if (driver == null) {
                DesiredCapabilities caps = new DesiredCapabilities();

                // Common capabilities
                caps.setCapability("platformName", props.getString("platformName"));
                caps.setCapability("appium:platformVersion", props.getString("platformVersion"));
                caps.setCapability("appium:deviceName", props.getString("deviceName"));
                caps.setCapability("appium:automationName", props.getString("automationName"));
                caps.setCapability("appium:app", appDir);
                caps.setCapability("appium:autoGrantPermissions", true);

                //Without Installation
//                caps.setCapability("appium:appPackage", props.getString("appPackage"));
//                caps.setCapability("appium:appActivity", props.getString("appActivity"));
//                caps.setCapability("appium:noReset", true);

                // Platform specific initialization
                URL url = URI.create(props.getString("appiumServerUrl")).toURL();

                driver = new AndroidDriver(url, caps);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(props.getString("wait"))));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize Appium driver: " + e.getMessage());
        }

        return driver;
    }


    public static void closeApp() {
        if (driver != null) {
            driver.terminateApp(props.getString("appPackage"));
            // Always quit the driver session
            driver.quit();
            driver = null;
        }
    }
}