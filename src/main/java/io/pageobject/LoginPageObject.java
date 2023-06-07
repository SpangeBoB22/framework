package io.pageobject;

import io.WebElementWrapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPageObject {
    @FindBy(xpath = ".//button[contains(text(),'Log in')]")
    private WebElement logInButton;

    @FindBy(xpath = "//*[@id='loginusername']")
    private WebElement userName;

    @FindBy(xpath = "//*[@id='loginpassword']")
    private WebElement password;
    private final WebDriverWait wait;

    public LoginPageObject(WebDriver driver, WebDriverWait wait) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public WebElementWrapper getLogInButton() {
        wait.until(ExpectedConditions.elementToBeClickable(logInButton));
        return new WebElementWrapper(logInButton);
    }
    public WebElementWrapper getUserNameInput() {
        wait.until(ExpectedConditions.visibilityOf(userName));
        return new WebElementWrapper((userName));
    }
    public WebElementWrapper getPasswordInput() {
        wait.until(ExpectedConditions.visibilityOf(password));
        return new WebElementWrapper(password);
    }
}
