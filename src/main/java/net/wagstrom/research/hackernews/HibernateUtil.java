package net.wagstrom.research.hackernews;

import net.wagstrom.research.hackernews.dbobjs.Item;
import net.wagstrom.research.hackernews.dbobjs.Karma;
import net.wagstrom.research.hackernews.dbobjs.PageContent;
import net.wagstrom.research.hackernews.dbobjs.Story;
import net.wagstrom.research.hackernews.dbobjs.Update;
import net.wagstrom.research.hackernews.dbobjs.User;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure()
                    .addPackage("net.wagstrom.research.hackernews.dbobjs")
                    .addAnnotatedClass(Item.class)
                    .addAnnotatedClass(Karma.class)
                    .addAnnotatedClass(PageContent.class)
                    .addAnnotatedClass(Story.class)
                    .addAnnotatedClass(Update.class)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}