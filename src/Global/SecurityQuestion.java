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

/**
 * Created by Anil on 23/03/2018
 */
@Path("/securityques_services")
@WebService
public class SecurityQuestion {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("admin_seq_change")
    public String admin_seq_change(@FormParam("param1")String name, @FormParam("param2") String pass,@FormParam("param3") String ques, @FormParam("param4") String ans)
    {
        Session session = DB.Global.getSession1();
        Transaction t = session.beginTransaction();
        try {
            User user = session.load(User.class,name);
            if(pass.equals(user.getPassword()))
            {
                user.setQues(ques);
                user.setAns(ans);
                session.persist(user);
                t.commit();
                session.close();
                return "1";
            }
            else {
                t.commit();
                session.close();
                return "E";
            }
        }
        catch (Exception e)
        {
//            t.commit();
//            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("hod_seq_change")
    public String hod_seq_change(@FormParam("param1")String name, @FormParam("param2") String pass,@FormParam("param3") String ques, @FormParam("param4") String ans)
    {
        Session session = DB.Global.getSession1();
        Transaction t = session.beginTransaction();
        try {
            User user = session.load(User.class,name);
            if(pass.equals(user.getPassword()))
            {
                user.setQues(ques);
                user.setAns(ans);
                session.persist(user);
                t.commit();
                session.close();
                return "1";
            }
            else {
                t.commit();
                session.close();
                return "E";
            }
        }
        catch (Exception e)
        {
//            t.commit();
//            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("teacher_seq_change")
    public String teacher_seq_change(@FormParam("param1")int tid, @FormParam("param2") String pass,@FormParam("param3") String ques, @FormParam("param4") String ans)
    {
        Session session = DB.Global.getSession();
        Transaction t = session.beginTransaction();
        try {
            DB.Teacher user= session.load(Teacher.class,tid);
            if(pass.equals(user.getPass()))
            {
                user.setQues(ques);
                user.setAns(ans);
                session.persist(user);
                t.commit();
                session.close();
                return "1";
            }
            else {
                t.commit();
                session.close();
                return "E";
            }
        }
        catch (Exception e)
        {
//            t.commit();
//            session.close();
            return "E";
//            return String.valueOf(e);
        }
    }
}
