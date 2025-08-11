package stepDefinitions;

import io.cucumber.java.en.Given;
import pages.LoginPage;

public class ReuseLogin{
    LoginPage loginPage = new LoginPage();

    @Given("the user launches and login the ABC Company mobile app")
    public void theUserLaunchesAndLoginTheABCCompanyMobileApp() {
        loginPage.performLogin(" azmin@excelbd.com", "D!m77(2SJ,5j");
        loginPage.setHomePage();
    }
}
