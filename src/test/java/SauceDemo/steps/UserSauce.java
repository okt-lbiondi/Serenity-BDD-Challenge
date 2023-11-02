package SauceDemo.steps;

import SauceDemo.pageobject.CartPage;
import SauceDemo.pageobject.HomePage;
import SauceDemo.pageobject.LoginPage;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.steps.ScenarioActor;
import net.thucydides.core.annotations.Steps;
import org.assertj.core.api.Assertions;

public class UserSauce extends ScenarioActor {
    String actor;

    @Steps(shared = true)
    LoginPage loginPage;
    HomePage homePage;
    CartPage cartPage;

    public void navigatesTo(String link){
        loginPage.setDefaultBaseUrl(link);
        loginPage.open();
    }

    public void entersUsername(String username){
        loginPage.enterUsername(username);
    }

    public void entersPasswword(String password){
        loginPage.enterPassword(password);
    }

    public void clicksLoginButton(){
        Serenity.takeScreenshot();
        loginPage.clickLoginButton();
    }

    public void verifiesHomePage(){
        Assertions.assertThat(homePage.isInventoryContainerVisible()).isTrue();
    }

    public void goToAboutPage() {
        homePage.openMenu();
        homePage.selectAboutItem();
    }

    public void verifiesAboutPage() {
        Assertions.assertThat(homePage.getDriver().getCurrentUrl()).isEqualTo("https://saucelabs.com/");
    }

    public void navigatesBackToPreviousPage() {
        homePage.getDriver().navigate().back();
        verifiesHomePage();
    }

    public void ordersProductsBy(String option) {
        homePage.selectProductSortOption(option);
    }

    public void validatesProductOptionIsSelected(String option) {
        Assertions.assertThat(homePage.isProductSortOptionSelected()).isEqualTo(option);
    }

    public void validatesProductAreOrderedByOption(String option) {
        Assertions.assertThat(homePage.areProductsOrderedByOption(option)).isTrue();
    }

    public void addsProductsToCart(int products) {
        homePage.addNumberOfProductsToCart(products);
    }

    public void verifiesCartBadgeMatchesProductAmount(int products) {
        Assertions.assertThat(HomePage.getCartBadgeNumber()).isEqualTo(products);
    }

    public void goesToCart() {
        homePage.goToCart();
    }

    public void verifiesProductsAddedInCart(int products) {
        Assertions.assertThat(cartPage.isNumberOfProductsEqualTo(products)).isTrue();
    }

    public void startsCheckoutProcess() {
        cartPage.clickCheckoutButton();
    }

    public void fillsPersonalData() {
        cartPage.fillPersonalData("Lucas", "Serenity", "123456");
        Serenity.takeScreenshot();
    }

    public void continuesCheckoutToStepTwo() {
        cartPage.clickContinueToStepTwo();
    }

    public void verifiesPriceisCorrect() {
        Assertions.assertThat(cartPage.getCartTotalPrice()).isEqualTo(cartPage.getCartSumPrice(),Assertions.within(0.009));
        Assertions.assertThat(cartPage.getCartTotalPrice()).isEqualTo(cartPage.getInventorySumPrice(),Assertions.within(0.009));
        Serenity.recordReportData().withTitle("Cart Information").andContents(
                "Payment info is: " + cartPage.getSummaryValues(0)
                + "\nShipping info is: " + cartPage.getSummaryValues(1));
        Serenity.recordReportData().asEvidence().withTitle("Cart Prices").andContents(
                "Cart Total Price is: $" + cartPage.getCartTotalPrice()
                + "\nCart SubTotal Price is: $" + cartPage.getCartSubTotalPrice()
                + "\nCart Tax Price is: $" + cartPage.getTaxPrice());
    }

    public void finishesCheckout() {
        cartPage.clickFinishCheckout();
    }

    public void verifiesCheckoutFinishedPage() {
        Assertions.assertThat(cartPage.isCheckoutCompletedPageVisible()).isTrue();
        Assertions.assertThat(cartPage.getCheckoutCompleteHeaderText()).isEqualTo("Thank you for your order!");
    }
}
