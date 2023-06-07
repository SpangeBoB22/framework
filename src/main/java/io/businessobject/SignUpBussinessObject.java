package io.businessobject;

import io.WebElementWrapper;
import io.pageobject.SignUpPageObject;
import io.qameta.allure.Step;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpBussinessObject {
    private final SignUpPageObject sigUpPageObject;

    public SignUpBussinessObject(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, 20, 500);
        sigUpPageObject = new SignUpPageObject(driver, wait);
    }

    @Step("Sign Up new user {0} by password {1}")
    public void signUp(String userName, String userPassword) {
        sigUpPageObject.getUserNameInput().setText(userName);
        sigUpPageObject.getPasswordInput().setText(userPassword);
        sigUpPageObject.getSignUpButton().clickButton();
    }

    @Step("Check result sign up action")
    public Pair<Boolean, Boolean> checkResult() {
        WebElementWrapper alert = sigUpPageObject.getAlert();
        if(alert.getAlertText().equals("This user already exist.")) {
            alert.accept();
            sigUpPageObject.getCloseButton().clickButton();
            return result(false, true);
        } else {
            alert.accept();
            return result(true, false);
        }
    }
    private Pair<Boolean, Boolean> result(Boolean newUser, Boolean existUser) {
        return new Pair<Boolean, Boolean>() {
            @Override
            public Boolean getLeft() {
                return newUser;
            }

            @Override
            public Boolean getRight() {
                return existUser;
            }

            @Override
            public Boolean setValue(Boolean aBoolean) {
                return null;
            }
        };
    }
}
