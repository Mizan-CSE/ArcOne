package stepDefinitions;

import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.DriverManager;

public class Hooks{
    static AndroidDriver driver;
    @Before
    public void start() {
       driver = DriverManager.launchApp();
    }

    @After
    public static void terminate() {
        DriverManager.closeApp();
    }
}
