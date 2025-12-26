package stepDefinitions;

import io.cucumber.java.en.Given;
import pageObject.Hafele_HomePage;

public class hafele_HomePageStep {
    Hafele_HomePage homePage= new Hafele_HomePage();


    @Given("user select accept cookies")
    public void user_select_accept_cookies(){
        homePage.clickOnAcceptCookies();
    }
}
