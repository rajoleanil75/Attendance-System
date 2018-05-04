package Global;

import DB.Teacher;
import DB.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;

import javax.jws.WebService;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Anil on 21/03/2018
 */
@Path("/newuser")
@WebService
public class NewUser {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add_admin")
    public String add_admin(@FormParam("param1")String name, @FormParam("param2") String pass, @FormParam("param3")String ques,@FormParam("param4")String ans)
    {
        Session session = DB.Global.getSession1();
        Transaction t = session.beginTransaction();
        try {

            User user = new User();
            user.setName(name);
            user.setPassword(pass);
            user.setQues(ques);
            user.setAns(ans);
            user.setRole(4);
            user.setDate(LocalDate.now());
            user.setTime(LocalTime.now());
            session.persist(user);
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
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add_hod")
    public String add_hod(@FormParam("param1")String name, @FormParam("param2") String pass, @FormParam("param3")String ques,@FormParam("param4")String ans)
    {
        Session session = DB.Global.getSession1();
        Transaction t = session.beginTransaction();
        try {

            User user = new User();
            user.setName(name);
            user.setPassword(pass);
            user.setQues(ques);
            user.setAns(ans);
            user.setRole(3);
            user.setDate(LocalDate.now());
            user.setTime(LocalTime.now());
            session.persist(user);
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
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add_teacher")
    public String add_teacher(@FormParam("param1")int tid, @FormParam("param2") String pass, @FormParam("param3")String ques,@FormParam("param4")String ans)
    {
        Session session = DB.Global.getSession();
        Transaction t = session.beginTransaction();
        try {
            DB.Teacher teacher= (DB.Teacher) session.createQuery("from Teacher s where s.id=:id").setParameter("id",tid).uniqueResult();
            teacher.setPass(pass);
            teacher.setQues(ques);
            teacher.setAns(ans);
            teacher.setDate(LocalDate.now());
            teacher.setTime(LocalTime.now());
            session.persist(teacher);
            t.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
//            t.commit();
            session.close();
//            return String.valueOf(e);
            return "E";
        }
    }
}
