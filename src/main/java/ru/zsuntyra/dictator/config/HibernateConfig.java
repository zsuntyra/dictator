package ru.zsuntyra.dictator.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.zsuntyra.dictator.domain.*;

public class HibernateConfig {

    private static SessionFactory sessionFactory = null;

    private HibernateConfig() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration().configure();
            // TODO add annotated classes to configuration
            configuration.addAnnotatedClass(Answer.class);
            configuration.addAnnotatedClass(Associate.class);
            configuration.addAnnotatedClass(Avatar.class);
            configuration.addAnnotatedClass(Question.class);
            configuration.addAnnotatedClass(Rating.class);
            configuration.addAnnotatedClass(User.class);

            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        }
        return sessionFactory;
    }

}
