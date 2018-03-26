package Global;

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
}
