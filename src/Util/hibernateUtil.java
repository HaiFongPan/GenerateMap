package Util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;


/**
 * Created with IntelliJ IDEA.
 * User: leo
 * Date: 13-4-22
 * Time: 下午12:03
 * Project: GenerateRN
 */
public class hibernateUtil {
    private static SessionFactory sf = buildSession();

    private static SessionFactory buildSession() {
        try {
            Configuration conf = new Configuration().configure();
            ServiceRegistry sr = new ServiceRegistryBuilder().applySettings(conf.getProperties()).buildServiceRegistry();
            return conf.buildSessionFactory(sr);
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }

    }

    public static SessionFactory getSessionFactory() {
        return sf;
    }
}
