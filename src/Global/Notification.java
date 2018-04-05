package Global;

import DB.Teacher;
import DB.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anil on 23/03/2018
 */
@Path("/notification_services")
@WebService
public class Notification {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("admin_notification")
    public String admin_notification(@FormParam("param1")String name, @FormParam("param2") String uname)
    {
        Session session = DB.Global.getSession1();
        Transaction t = session.beginTransaction();
        try {
            User user=session.load(User.class,uname);
            DB.Notification notification=new DB.Notification();
            notification.setName(name);
            notification.setDate(LocalDate.now());
            notification.setTime(LocalTime.now());
            notification.setUser(user);
            session.persist(notification);
            t.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
//            t.commit();
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewAllAdmin")
    public List viewAllAdmin(@FormParam("param1")String uid)
    {
        Session session = DB.Global.getSession1();
        Transaction t = session.beginTransaction();
        try {

            java.util.List<DB.Notification> tlist = session.createQuery("from DB.Notification s where s.user.name=:id order by s.date desc ,s.time desc ").setParameter("id", uid).setMaxResults(25).list();
            List list = new ArrayList();
            for (Iterator iterator = tlist.iterator(); iterator.hasNext(); ) {
                DB.Notification subject = (DB.Notification) iterator.next();
                List list1 = new ArrayList();
                list1.add(subject.getDate());
                list1.add(subject.getTime());
                list1.add(subject.getName());
                list.add(list1);
            }
            t.commit();
            session.close();
//            return "111";
            return list;

        }
        catch (Exception e)
        {
            t.commit();
            session.close();
            return Collections.singletonList(e);
//            return String.valueOf(e);
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("teacher_notification")
    public String teacher_notification(@FormParam("param1")String name, @FormParam("param2") int tid)
    {
        Session session = DB.Global.getSession();
        Transaction t = session.beginTransaction();
        try {
            DB.Teacher user=session.load(Teacher.class,tid);
            DB.TNotification notification=new DB.TNotification();
            notification.setName(name);
            notification.setDate(LocalDate.now());
            notification.setTime(LocalTime.now());
            notification.setTeacher(user);
            session.persist(notification);
            t.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
//            t.commit();
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewAllTeacher")
    public List viewAllTeacher(@FormParam("param1")int uid)
    {
        Session session = DB.Global.getSession();
        Transaction t = session.beginTransaction();
        try {

            java.util.List<DB.TNotification> tlist = session.createQuery("from TNotification s where s.teacher.id=:id order by s.date desc ,s.time desc ").setParameter("id", uid).setMaxResults(25).list();
            List list = new ArrayList();
            for (Iterator iterator = tlist.iterator(); iterator.hasNext(); ) {
                DB.TNotification subject = (DB.TNotification) iterator.next();
                List list1 = new ArrayList();
                list1.add(subject.getDate());
                list1.add(subject.getTime());
                list1.add(subject.getName());
                list.add(list1);
            }
            t.commit();
            session.close();
//            return "111";
            return list;

        }
        catch (Exception e)
        {
//            t.commit();
            session.close();
            return Collections.singletonList(e);
//            return String.valueOf(e);
        }
    }
}
