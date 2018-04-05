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
@Path("/password_services")
@WebService
public class Password {

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("admin_update_pass")
    public String admin_update_pass(@FormParam("param1")String name, @FormParam("param2")String opass, @FormParam("param3")String npass)
    {
        Session session= DB.Global.getSession1();
        Transaction transaction = session.beginTransaction();
        try
        {
            User user = session.load(User.class,name);
            if (opass.equals(user.getPassword())) {
                user.setPassword(npass);
                session.persist(user);
                transaction.commit();
                session.close();
                return "1";
            }
            else {
                transaction.commit();
                session.close();
                return "E";
            }
        }
        catch (Exception e){
//            transaction.commit();
//            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("teacher_update_pass")
    public String teacher_update_pass(@FormParam("param1")int tid, @FormParam("param2")String opass, @FormParam("param3")String npass)
    {
        Session session= DB.Global.getSession();
        Transaction transaction = session.beginTransaction();
        try
        {
            DB.Teacher user = session.load(Teacher.class,tid);
            if (opass.equals(user.getPass())) {
                user.setPass(npass);
                session.persist(user);
                transaction.commit();
                session.close();
                return "1";
            }
            else {
                transaction.commit();
                session.close();
                return "E";
            }
        }
        catch (Exception e){
//            transaction.commit();
//            session.close();
            return "E";
//            return String.valueOf(e);
        }
    }
}
