package Global;

import DB.Events;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by Anil on 10/04/2018
 */
@Path("/event_services")
@WebService
public class Event_Service {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1")String ename, @FormParam("param2")String sd, @FormParam("param3")String ed)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        try
        {
            LocalDate sdate=LocalDate.parse(sd);
            LocalDate edate=LocalDate.parse(ed);
            DB.Events events=new Events();
            events.setName(ename);
            events.setSdate(sdate);
            events.setEdate(edate);
            session.save(events);
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
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll()
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<DB.Events> tlist=session.createQuery("from DB.Events order by sdate asc ").list();
        t.commit();
        session.close();
        return tlist;
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("delete")
    public String delete(@FormParam("param1") String ename) {
        try {
            Session session = DB.Global.getSession();
            Transaction t = session.beginTransaction();
            Events events = session.load(Events.class, ename);
            session.delete(events);
            t.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            return "0";
        }
    }
}
