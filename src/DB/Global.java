package DB;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by Anil on 20/03/2018
 */
public class Global {
    private static SessionFactory ourSessionFactory;
    static
    {
        try
        {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex)
        {
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static void reload()
    {
        try
        {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex)
        {
            throw new ExceptionInInitializerError(ex);
        }

    }
    public static Session getSession() throws HibernateException
    {
//        reload();
        try {
            return ourSessionFactory.getCurrentSession();

        }
        catch (Exception e)
        {
            return ourSessionFactory.openSession();
        }
    }
    public static void closeFactory()
    {
        try {
            ourSessionFactory.close();
        }
        catch (Exception e)
        {
            ourSessionFactory.getCurrentSession().close();
            ourSessionFactory.close();
        }
    }

    private static final SessionFactory ourSessionFactory1;
    static
    {
        try {
            Configuration configuration=new Configuration();
            configuration.configure("conn1.cfg.xml");
            ourSessionFactory1=configuration.buildSessionFactory();
        }catch (Throwable e){
            throw new ExceptionInInitializerError(e);
        }
    }
    public static Session getSession1() throws HibernateException
    {
        try {
            return ourSessionFactory1.getCurrentSession();
//        return ourSessionFactory.getCurrentSession();
        }
        catch (Exception e)
        {
            return ourSessionFactory1.openSession();
        }
    }
    //    public static Session getCSession1() throws HibernateException
//    {
//        return ourSessionFactory1.getCurrentSession();
//    }
    public static void closeFactory1()
    {
        try {
            ourSessionFactory1.close();
        }
        catch (Exception e)
        {
            ourSessionFactory1.getCurrentSession().close();
            ourSessionFactory1.close();
        }
    }

}
