package practise;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObject.SamplePage;

import java.util.Map;

public class SampleSteps extends SamplePage {

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        urlLaunch();
    }

    @And("I enter valid email {string} and password {string}")
    public void i_enter_valid_email_and_password(String email, String passWord) {
        clearAndEnter(userName, email);
        clearAndEnter(password, passWord);
    }

    @When("I click on the login button")
    public void i_click_on_the_login_button() {
        click(loginBtn);
    }

    @Then("I should be redirected to the dashboard")
    public void i_should_be_redirected_to_the_dashboard() {
        System.out.println("User should redirect to dashboard page");
    }

    @Then("I should see the welcome message")
    public void i_should_see_the_welcome_message() {
        System.out.println("User should see the sucess toast");
    }

    @When("I enter valid email and invalid password")
    public void i_enter_valid_email_and_invalid_password() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I should see invalid email or password error")
    public void i_should_see_invalid_email_or_password_error() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @And("I enter valid emails {string} and password {string}")
    public void i_enter_valid_emails_and_password(String users, String passw) {
        System.out.println(users + " :" + passw);
    }

    @When("I enter valid email and password")
    public void i_enter_valid_email_and_password(DataTable dataTable) {
        Map<String, String> creds = dataTable.asMap(String.class, String.class);
        System.out.println(creds.get("email"));
        System.out.println(creds.get("pass"));

    }

}
