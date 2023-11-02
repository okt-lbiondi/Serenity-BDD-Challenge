package SauceDemo.pageobject;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageObject {
    @FindBy(id = "user-name")
    WebElementFacade usernameField;
    @FindBy (id="password")
    WebElementFacade passwordField;
    @FindBy (id = "login-button")
    WebElementFacade loginButton;

    public void enterUsername(String username){
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password){
        passwordField.sendKeys(password);
    }
    public void clickLoginButton(){
        loginButton.click();
    }
}
