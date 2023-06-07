package io;

import io.model.Cart;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.testng.annotations.DataProvider;

public class ApiDataProviders {

    @DataProvider(name = "test-data")
    public static Object[] setCardTest() {
        Session session = HibernateSession.getSession().openSession();
        Query<Cart> query = session.createQuery("FROM Cart", Cart.class);
        return query.list().toArray();
    }
}
