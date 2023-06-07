package io.pageobject;

import io.WebElementWrapper;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignUpPageObject {

    @FindBy(css = "#signInModal .btn-primary")
    private WebElement signUpButton;

    @FindBy(css ="#signInModal .btn-secondary" )
    private WebElement closeButton;

    @FindBy(xpath = "//*[@id='sign-username']")
    private WebElement signUserName;

    @FindBy(xpath = "//*[@id='sign-password']")
    private WebElement signPassword;

    private final WebDriverWait wait;
    private final WebDriver driver;

    public SignUpPageObject(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public WebElementWrapper getSignUpButton() {
        wait.until(ExpectedConditions.elementToBeClickable(signUpButton));
        return new WebElementWrapper(signUpButton);
    }
    public WebElementWrapper getCloseButton() {
        wait.until(ExpectedConditions.elementToBeClickable(closeButton));
        return new WebElementWrapper(closeButton);
    }
    public WebElementWrapper getUserNameInput() {
        wait.until(ExpectedConditions.visibilityOf(signUserName));
        return new WebElementWrapper((signUserName));
    }
    public WebElementWrapper getPasswordInput() {
        wait.until(ExpectedConditions.visibilityOf(signPassword));
        return new WebElementWrapper(signPassword);
    }
    public WebElementWrapper getAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        return new WebElementWrapper(driver.switchTo().alert());
    }
}
