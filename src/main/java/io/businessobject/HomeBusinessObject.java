package io.businessobject;

import io.pageobject.HomePageObject;
import io.pageobject.LoginPageObject;
import io.pageobject.SignUpPageObject;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomeBusinessObject {
    private final HomePageObject homePageObject;
    private final SignUpPageObject sigUpPageObject;

    private final LoginPageObject loginPageObject;


    public HomeBusinessObject(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 50);
        homePageObject = new HomePageObject(driver, wait);
        sigUpPageObject = new SignUpPageObject(driver, wait);
        loginPageObject = new LoginPageObject(driver, wait);
    }

    @Step("Open Sing Up modal window")
    public void openSignUp() {
        homePageObject.getSignUp().clickButton();
    }

    @Step("Open log in modal window")
    public void openLogIn() {
        homePageObject.getLogIn().clickButton();
    }

    @Step("Check is open Log In modal window")
    public boolean checkIsOpenLogInWindow() {
        return loginPageObject.getLogInButton().isVisible();
    }
    @Step("Check name user {0} log in")
    public boolean checkNameUserLogIn(String userName) {
        return homePageObject.getLoginUserName().getText().equals("Welcome " + userName);
    }
    @Step("Check is open Sign Up modal window")
    public boolean checkIsOpenSignUpWindow() {
        return sigUpPageObject.getSignUpButton().isVisible();
    }

    @Step("Open product page")
    public void openProductPage() {
        homePageObject.getLinkProductByTitle().clickLink();
    }

    @Step("Open cart page")
    public void openCart() {
        homePageObject.getCart().clickLink();
    }
}
