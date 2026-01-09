package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pageObject.Hafele_HomePage;

public class hafele_HomePageStep {
    Hafele_HomePage homePage= new Hafele_HomePage();

    @Given("user select accept cookies")
    public void user_select_accept_cookies(){
        homePage.clickOnAcceptCookies();
    }

    @When("user enter input {string} in search box")
    public void user_enter_input_in_search_box(String productName) {
       homePage.searchProduct(productName);
    }

    @Then("add to cart button should be displayed for the searched item")
    public void add_to_cart_button_should_be_displayed_for_the_searched_item() {
        Assert.assertTrue(homePage.isAddToCartButtonDisplayed(), "Add to cart button is not displayed");
    }

    @Then ("validate no results found message is displayed")
    public void validate_no_results_found_message_is_displayed() {
        Assert.assertTrue(homePage.isNoResultsFoundMessageDisplayed(), "No results found message is not displayed");
    }

    }
