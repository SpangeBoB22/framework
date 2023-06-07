package io;

import io.model.Cart;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.testng.AssertJUnit;
import org.testng.annotations.Listeners;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Random;

@Listeners(TestListener.class)
public class RestApiTest {
    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://api.trello.com/1";
        Session session = HibernateSession.getSession().openSession();
        Cart cart = new Cart();
        cart.setName(randomName());
        cart.setDescription(randomDescription());
        cart.setIdMember(Constants.ID_MEMBER);
        session.beginTransaction();
        session.save(cart);
        session.getTransaction().commit();
    }

    @AfterClass
    public void down() {
        Session session = HibernateSession.getSession().openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Cart").executeUpdate();
        session.getTransaction().commit();
    }
    @Test(
            dataProvider = "test-data",
            dataProviderClass = io.ApiDataProviders.class,
            description = "Create new cart")
    public void createCart(Cart cart) {
        TrelloAPI trelloAPI = new TrelloAPI();

        Response response = trelloAPI.createNewCart(cart);
        AssertJUnit.assertEquals(response.statusCode(), 200);
        AssertJUnit.assertEquals(response.jsonPath().get("name"), cart.getName());
        String cardId = response.jsonPath().get("id").toString();

        Session session = HibernateSession.getSession().openSession();
        Query<Cart> query = session.createQuery("FROM Cart", Cart.class);
        Cart cartDB = query.list().stream().findFirst().orElseThrow();
        cartDB.setIdCart(cardId);
        session.beginTransaction();
        session.save(cartDB);
        session.getTransaction().commit();
    }
    @Test(
            dataProvider = "test-data",
            dataProviderClass = io.ApiDataProviders.class,
            description = "Add member to cart", dependsOnMethods = "createCart")
    public void updateMember(Cart cart) {
        TrelloAPI trelloAPI = new TrelloAPI();
        Response response = trelloAPI.updateMemberCart(cart);
        AssertJUnit.assertEquals(response.statusCode(), 200);
        String[] members = response.jsonPath().getObject("idMembers", String[].class);
        AssertJUnit.assertEquals(Arrays.stream(members).findFirst().orElseThrow(), cart.getIdMember());
    }
    @Test(
            dataProvider = "test-data",
            dataProviderClass = io.ApiDataProviders.class,
            description = "Update description in cart", dependsOnMethods = "updateMember")
    public void updateDesc(Cart cart) {
        TrelloAPI trelloAPI = new TrelloAPI();
        Response response = trelloAPI.updateDescCart(cart);
        AssertJUnit.assertEquals(response.statusCode(), 200);
        AssertJUnit.assertEquals(response.jsonPath().get("desc"), cart.getDescription());
    }
    @Test(
            dataProvider = "test-data",
            dataProviderClass = io.ApiDataProviders.class,
            description = "Move cart", dependsOnMethods = "updateDesc")
    public void updateIdList(Cart cart) {
        TrelloAPI trelloAPI = new TrelloAPI();
        Response response = trelloAPI.updateIdListCart(cart, Constants.ID_LIST_SECOND);
        AssertJUnit.assertEquals(response.statusCode(), 200);
        AssertJUnit.assertEquals(response.jsonPath().get("idList"), Constants.ID_LIST_SECOND);
    }
    private String randomName() {
        return "Name" + getRandomNumberUsingInts(10, 9000) ;
    }
    private String randomDescription() {
        return "Description" + getRandomNumberUsingInts(1, 7000);
    }
    private int getRandomNumberUsingInts(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }
}
