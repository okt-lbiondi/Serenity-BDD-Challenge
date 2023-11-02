package SauceDemo.stepsdefinitions;

import SauceDemo.steps.UserSauce;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class UserStepDefinitions {
    @Steps(shared = true)
    UserSauce user;

    @Given("^user navigates to (.*)$")
    public void userNavigatesToUrl(String link){
        user.navigatesTo(link);
    }

    @When("^user logins with (.*) and (.*)$")
    public void userLogsIn(String username, String password){
       user.entersUsername(username);
       user.entersPasswword(password);
       user.clicksLoginButton();
    }

    @Then("user should be logged in successfully")
    public void verifyHomePage(){
        user.verifiesHomePage();
    }

    @When("^user goes to about page$")
    public void userGoesToAboutPage() {
        user.goToAboutPage();
    }

    @Then("^user validates about page$")
    public void userValidatesAboutPage() {
        user.verifiesAboutPage();
    }

    @And("^user navigates back to previous page$")
    public void userNavigatesBackToPreviousPage() {
        user.navigatesBackToPreviousPage();
    }

    @When("^user orders products by (.*)$")
    public void userOrdersProductsByOption(String option) {
        user.ordersProductsBy(option);
    }

    @Then("^user validates products are ordered by (.*)$")
    public void userValidatesProductsAreOrderedByOption(String option) {
        user.validatesProductOptionIsSelected(option);
        user.validatesProductAreOrderedByOption(option);
    }

    @When("^user adds the first (\\d+) products to cart$")
    public void userAddsTheFirstProductsToCart(int products) {
        user.addsProductsToCart(products);
        user.verifiesCartBadgeMatchesProductAmount(products);
    }

    @Then("^user verifies (\\d+) products were added to cart$")
    public void userVerifiesProductsAreInTheCart(int products) {
        user.goesToCart();
        user.verifiesProductsAddedInCart(products);
    }

    @Given("^user starts checkout process$")
    public void userStartsCheckoutProcess() {
        user.startsCheckoutProcess();
    }

    @When("^user fills personal data to continue$")
    public void userFillsPersonalDataToContinue() {
        user.fillsPersonalData();
        user.continuesCheckoutToStepTwo();
    }

    @Then("^user verifies price is correct$")
    public void userVerifiesPriceIsCorrect() {
        user.verifiesPriceisCorrect();
    }

    @When("^user finishes checkout$")
    public void userFinishesCheckout() {
        user.finishesCheckout();
    }

    @Then("^user verifies checkout was complete$")
    public void userVerifiesCheckoutWasComplete() {
        user.verifiesCheckoutFinishedPage();
    }
}
