package io;


import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateSession {

    static SessionFactory sessionFactory;
    private static final HibernateSession instance;
    private HibernateSession() {}


    static {
        try {
            instance = new HibernateSession();
            BootstrapServiceRegistry bootstrapServiceRegistry =
                    new BootstrapServiceRegistryBuilder().build();
            StandardServiceRegistryBuilder standardServiceRegistryBuilder =
                    new StandardServiceRegistryBuilder(bootstrapServiceRegistry);

            StandardServiceRegistry standardServiceRegistry = standardServiceRegistryBuilder
                    .configure("hibernate.cfg.xml")
                    .build();

            MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);

            metadataSources.addAnnotatedClass(io.model.User.class);
            metadataSources.addAnnotatedClass(io.model.Product.class);
            metadataSources.addAnnotatedClass(io.model.Cart.class);

            Metadata metadata = metadataSources.getMetadataBuilder().build();

            sessionFactory = metadata.buildSessionFactory();

        } catch (Exception ex) {
            assert sessionFactory != null;
            sessionFactory.close();
            throw new RuntimeException("Exception occurred in creating singleton instance");
        }
    }

    public static HibernateSession getInstance() {
        return instance;
    }

    public static SessionFactory getSession() {
        return sessionFactory;
    }
}
