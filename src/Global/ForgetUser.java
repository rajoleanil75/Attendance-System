package Global;

import DB.Teacher;
import DB.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Anil on 21/03/2018
 */
@Path("/forgetuser")
@WebService
public class ForgetUser {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("check_admin")
    public String add_admin(@FormParam("param1")String name, @FormParam("param2")String ques, @FormParam("param3")String ans)
    {
        Session session = DB.Global.getSession1();
        Transaction t = session.beginTransaction();
        try {

            User user = session.load(User.class, name);
            if(user==null)
                return "E";
            else
            {
                if (user.getQues().equals(ques) && user.getAns().equals(ans))
                {
                    return "1";
                }
                else
                    return "E";
            }
        }
        catch (Exception e)
        {
            t.commit();
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("check_hod")
    public String add_hod(@FormParam("param1")String name, @FormParam("param2")String ques, @FormParam("param3")String ans)
    {
        Session session = DB.Global.getSession1();
        Transaction t = session.beginTransaction();
        try {

            User user = session.load(User.class, name);
            if(user==null)
                return "E";
            else
            {
                if (user.getQues().equals(ques) && user.getAns().equals(ans))
                {
                    return "1";
                }
                else
                    return "E";
            }
        }
        catch (Exception e)
        {
            t.commit();
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("forget_admin")
    public String forget_admin(@FormParam("param1")String name, @FormParam("param2") String pass)
    {
        Session session = DB.Global.getSession1();
        Transaction t = session.beginTransaction();
        try {
            User user = session.load(User.class,name);
            user.setPassword(pass);
            session.persist(user);
            t.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
//            t.commit();
//            session.close();
            return String.valueOf(e);
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("forget_hod")
    public String forget_hod(@FormParam("param1")String name, @FormParam("param2") String pass)
    {
        Session session = DB.Global.getSession1();
        Transaction t = session.beginTransaction();
        try {
            User user = session.load(User.class,name);
            user.setPassword(pass);
            session.persist(user);
            t.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
//            t.commit();
//            session.close();
            return String.valueOf(e);
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("check_teacher")
    public String add_teacher(@FormParam("param1")int tid, @FormParam("param2")String ques, @FormParam("param3")String ans)
    {

        Session session = DB.Global.getSession();
        Transaction t = session.beginTransaction();
        try {

            DB.Teacher user = session.load(Teacher.class, tid);
            if(user==null)
                return "E";
            else
            {
                if (user.getQues().equals(ques) && user.getAns().equals(ans))
                {
                    return "1";
                }
                else
                    return "E";
            }
        }
        catch (Exception e)
        {
            t.commit();
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("forget_teacher")
    public String forget_teacher(@FormParam("param1")int tid, @FormParam("param2") String pass)
    {
        Session session = DB.Global.getSession();
        Transaction t = session.beginTransaction();
        try {
            Teacher user = session.load(Teacher.class, tid);
            user.setPass(pass);
            session.persist(user);
            t.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
//            t.commit();
//            session.close();
            return String.valueOf(e);
        }
    }
}
