package Global;

import DB.CSClass;
import DB.Division;
import DB.Global;
import DB.Subject;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.kohsuke.rngom.ast.builder.Div;

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
    public List getClassWise(@FormParam("param1") int cid)
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
    @POST
    @Path("getSubjectClassWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List getSubjectClassWise(@FormParam("param1") String cid)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        Subject subject=session.load(Subject.class,cid);
        int clid=subject.getCSClass().getId();
        List<Division> tlist=session.createQuery("from Division s where s.csClass.id=:id").setParameter("id",clid).list();
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
