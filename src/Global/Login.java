package Global;

import DB.Backup;
import DB.Global;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import DB.User;
import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ANIL on 17/01/2018.
 */
@Path("/Login")
@WebService
public class Login {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("login")
    public String login_check()
    {
        try {
            Session session1 = Global.getSession1();
            Transaction transaction1 = session1.beginTransaction();
            transaction1.commit();
            session1.close();
            Session session2 = Global.getSession();
            Transaction t15 = session2.beginTransaction();
            t15.commit();
            session2.close();
            return "1";
        }
        catch (Exception e)
        {
            return String.valueOf(e);
        }
    }
}