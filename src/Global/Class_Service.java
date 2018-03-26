package Global;

import DB.CSClass;
import DB.Course;
import DB.Global;
import DB.Subject;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Created by Anil on 31/01/2018
 */
@Path("/class_services")
@WebService
public class Class_Service
{
    @POST
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CSClass> viewAll()
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        List<CSClass> tlist=session.createQuery("select s.id,s.name,s.course from CSClass s ").list();
        t.commit();
        session.close();
        return tlist;
    }
    @POST
    @Path("getCourseWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll(@FormParam("param1") int cid)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        List<CSClass> tlist=session.createQuery("from CSClass s where s.course.id=:id").setParameter("id",cid).list();
        List list=new ArrayList();
        for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
        {
            CSClass csClass= (CSClass) iterator.next();
            List list1=new ArrayList();
            list1.add(csClass.getId());
            list1.add(csClass.getName());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }
    @POST
    @Path("getClassCourse")
    @Produces(MediaType.TEXT_PLAIN)
    public String getClassCourse(@FormParam("param1") int sid)
    {
        Session session1= Global.getSession();
        Transaction t1=session1.beginTransaction();
        CSClass csClass= (CSClass) session1.createQuery("from CSClass s where s.id=:sid").setParameter("sid",sid).uniqueResult();
        int i=csClass.getCourse().getId();
        t1.commit();
        session1.close();
        if(csClass==null)
            return "0";
        return ""+i+"";
    }
    @POST
    @Path("getClassCourseNames")
    @Produces(MediaType.TEXT_PLAIN)
    public String getClassCourseNames(@FormParam("param1") int sid)
    {
        Session session1= Global.getSession();
        Transaction t1=session1.beginTransaction();
        CSClass csClass= (CSClass) session1.createQuery("from CSClass s where s.id=:sid").setParameter("sid",sid).uniqueResult();
        String s=csClass.getCourse().getName()+", Class: "+csClass.getName();
        t1.commit();
        session1.close();
        if(csClass==null)
            return "0";
        return s;
    }
}