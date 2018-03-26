package Global;

import DB.CSClass;
import DB.Division;
import DB.Global;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anil on 12/02/2018
 */
@Path("/division_services")
@WebService
public class Division_Service {
    @POST
    @Path("getClassWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll(@FormParam("param1") int cid)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        List<Division> tlist=session.createQuery("from Division s where s.csClass.id=:id").setParameter("id",cid).list();
        List list=new ArrayList();
        for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
        {
            Division division= (Division) iterator.next();
            List list1=new ArrayList();
            list1.add(division.getName());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }
}
