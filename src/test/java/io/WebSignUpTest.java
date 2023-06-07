package io;

import io.model.User;
import org.hibernate.Session;
import io.businessobject.HomeBusinessObject;
import io.businessobject.SignUpBussinessObject;
import org.openqa.selenium.WebDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;

import org.hibernate.query.Query;
import java.util.Random;

@Listeners(TestListener.class)
public class WebSignUpTest {

    private WebDriver driver;

    @Parameters("browser")
    @BeforeMethod
    public void before(String browserName) {
        switch (browserName) {
            case "firefox" -> driver = DriverPool.getDriver(DriverType.FIREFOX);
            case "chrome" -> driver = DriverPool.getDriver(DriverType.CHROME);
            default -> throw new RuntimeException("Not support driver " + browserName);
        }
        WebDriverFactoryThreadLocal.set(driver);
        driver.get("https://www.demoblaze.com/");
        HomeBusinessObject homeBusinessObject = new HomeBusinessObject(driver);
        homeBusinessObject.openSignUp();
        AssertJUnit.assertTrue(homeBusinessObject.checkIsOpenSignUpWindow());
    }

    @AfterMethod
    public void after() {
        WebDriverFactoryThreadLocal.remove();
        if(driver != null) {
            DriverPool.quitDriver(driver);
        }
    }

    @Test(description = "Run Sign Up new user action", priority = 1)
    public void runSignUpActionNewUser() {
        String userName = randomUserName();
        String userPassword = randomUserPassword();
        SignUpBussinessObject signUpBussinessObject = new SignUpBussinessObject(driver);
        signUpBussinessObject.signUp(userName, userPassword);
        AssertJUnit.assertTrue(signUpBussinessObject.checkResult().getLeft());

        Session session = HibernateSession.getSession().openSession();
        session.beginTransaction();
        User user = new User();
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        session.save(user);
        session.getTransaction().commit();


    }
    @Test(description = "Run Sign Up if exist user action", priority = 2)
    public void runSignUpActionExistUser() {
        Session session = HibernateSession.getSession().openSession();
        Query<User> query = session.createQuery("FROM User", User.class);
        User user = query.list().stream().findFirst().orElseThrow();

        SignUpBussinessObject signUpBussinessObject = new SignUpBussinessObject(driver);
        signUpBussinessObject.signUp(user.getUserName(), user.getUserPassword());
        AssertJUnit.assertTrue(signUpBussinessObject.checkResult().getRight());
    }
    private String randomUserName() {
        return "test" + getRandomNumberUsingInts(10, 900) + "@test.com";
    }
    private String randomUserPassword() {
        return "test" + getRandomNumberUsingInts(1, 700);
    }
    private int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }
}
