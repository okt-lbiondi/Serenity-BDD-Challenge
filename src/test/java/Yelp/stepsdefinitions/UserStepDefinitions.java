package Yelp.stepsdefinitions;

import Yelp.steps.UserYelp;
import io.cucumber.java.en.*;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Steps;

public class UserStepDefinitions {
    @Steps(shared = true)
    UserYelp user;

    @Given("^user navigates to https://www.yelp.com$")
    public void userNavigatesToYelp(){
        user.navigatesTo();
        Serenity.takeScreenshot();
    }

    @And("^selects find Restaurants$")
    public void userSearch() {
        user.searchFor();
    }

    @Given("^User search restaurant (.*)$")
    public void userSearchRestaurant(String restaurant) {
        user.searchRestaurant(restaurant);
        Serenity.takeScreenshot();
    }

    @When("^filters by (.*)$")
    public void userSelectFilter(String filter) {
        user.filterBy(filter);
        Serenity.takeScreenshot();
    }

    @And("^select first search results$")
    public void userSelectsFirstResult() {
        user.enterFirstResult();
        Serenity.takeScreenshot();
    }

    @Then("^User views restaurant information$")
    public void userViewDetails() throws InterruptedException {
        user.viewDetail();
        Serenity.takeScreenshot();
    }
}
