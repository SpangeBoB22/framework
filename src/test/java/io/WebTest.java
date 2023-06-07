package io;

import io.businessobject.HomeBusinessObject;
import io.businessobject.SignUpBussinessObject;
import io.businessobject.LogInBussinessObject;
import io.businessobject.ProductBusinessObject;
import io.businessobject.CartBusinessObject;
import io.model.Product;
import io.model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.openqa.selenium.WebDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.Random;


@Listeners(TestListener.class)
public class WebTest {

    private WebDriver driver;
    private HomeBusinessObject homeBusinessObject;

    SignUpBussinessObject signUpBussinessObject;
    private LogInBussinessObject logInBussinessObject;
    private ProductBusinessObject productBusinessObject;
    private CartBusinessObject cartBusinessObject;

    @Parameters("browser")
    @BeforeClass
    public void before(String browserName) {
        switch (browserName) {
            case "firefox" -> driver = DriverPool.getDriver(DriverType.FIREFOX);
            case "chrome" -> driver = DriverPool.getDriver(DriverType.CHROME);
            default -> throw new RuntimeException("Not support driver " + browserName);
        }
        WebDriverFactoryThreadLocal.set(driver);

        driver.get("https://www.demoblaze.com/");
        homeBusinessObject = new HomeBusinessObject(driver);
        signUpBussinessObject = new SignUpBussinessObject(driver);
        logInBussinessObject = new LogInBussinessObject(driver);
        productBusinessObject = new ProductBusinessObject(driver);
        cartBusinessObject = new CartBusinessObject(driver);
        Session session = HibernateSession.getSession().openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM User").executeUpdate();
        session.createQuery("DELETE FROM Product").executeUpdate();
        session.getTransaction().commit();

    }

    @AfterClass
    public void after() {
        WebDriverFactoryThreadLocal.remove();
        if(driver != null) {
            DriverPool.quitDriver(driver);
        }
    }

    @Test(description = "Run Sign Up new user action")
    public void runSignUpActionNewUser() {
        String userName = randomUserName();
        String userPassword = randomUserPassword();

        homeBusinessObject.openSignUp();
        AssertJUnit.assertTrue(homeBusinessObject.checkIsOpenSignUpWindow());

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
    @Test(description = "Run Login user after sign up", dependsOnMethods = "runSignUpActionNewUser")
    public void runLogInUser() {

        Session session = HibernateSession.getSession().openSession();
        Query<User> query = session.createQuery("FROM User", User.class);
        long size = query.list().stream().toList().size();
        User user = query.list().stream().skip(size - 1 ).findFirst().orElseThrow();

        homeBusinessObject.openLogIn();
        AssertJUnit.assertTrue(homeBusinessObject.checkIsOpenLogInWindow());

        logInBussinessObject.logIn(user.getUserName(), user.getUserPassword());
        AssertJUnit.assertTrue(homeBusinessObject.checkNameUserLogIn(user.getUserName()));
        session.beginTransaction();
        user.setLogIn(true);
        session.save(user);
        session.getTransaction().commit();
    }
    @Test(description = "Add product to cart", dependsOnMethods = "runLogInUser")
    public void addProductToCart() {
        homeBusinessObject.openProductPage();
        int price  = Integer.parseInt(productBusinessObject.getPriceProduct());
        productBusinessObject.addProductToCart();
        AssertJUnit.assertTrue(productBusinessObject.checkAddProductToCart());
        Session session = HibernateSession.getSession().openSession();
        Product product = new Product();
        product.setPrice(price);
        product.setName("Samsung galaxy s6");
        session.beginTransaction();
        session.saveOrUpdate(product);

        session.getTransaction().commit();
    }

    @Test(description = "Check total price in cart", dependsOnMethods = "addProductToCart")
    public void checkTotalPriceInCart() {
        Session session = HibernateSession.getSession().openSession();
        Query<Product> query = session.createQuery("FROM Product", Product.class);
        long size = query.list().stream().toList().size();

        Product product = query.list().stream().skip(size - 1 ).findFirst().orElseThrow();
        homeBusinessObject.openCart();

        AssertJUnit.assertEquals(Integer.parseInt(cartBusinessObject.getTotal()), product.getPrice());
    }

    private String randomUserName() {
        return "test" + getRandomNumberUsingInts(10, 9000) + "@test.com";
    }
    private String randomUserPassword() {
        return "test" + getRandomNumberUsingInts(1, 7000);
    }
    private int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }
}
