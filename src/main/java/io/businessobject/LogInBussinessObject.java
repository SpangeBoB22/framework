package io.businessobject;

import io.pageobject.LoginPageObject;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogInBussinessObject {
    private final LoginPageObject loginPageObject;

    public LogInBussinessObject(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 10, 500);
        loginPageObject = new LoginPageObject(driver, wait);
    }

    @Step("Log in user {0} by password {1}")
    public void logIn(String userName, String userPassword) {
        loginPageObject.getUserNameInput().setText(userName);
        loginPageObject.getPasswordInput().setText(userPassword);
        loginPageObject.getLogInButton().clickButton();
    }
}
