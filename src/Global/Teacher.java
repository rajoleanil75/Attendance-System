package Global;

import DB.TNotification;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.management.Query;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anil on 25/03/2018
 */
@Path("/teacher_services")
@WebService
public class Teacher {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add( @FormParam("param1")String tname)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        try
        {
            DB.Teacher teacher=new DB.Teacher();
            teacher.setName(tname);
            session.save(teacher);
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
        java.util.List<DB.Teacher> tlist=session.createQuery("from DB.Teacher").list();
        t.commit();
        session.close();
        return tlist;
    }
    @POST
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public List  search(@FormParam("param1") String tname)
    {
        Session session = DB.Global.getSession();
        Transaction transaction=session.beginTransaction();
        try {

            java.util.List<DB.Teacher> tlist=session.createQuery("from DB.Teacher s where s.name like :id").setParameter("id","%"+tname+"%").list();
            List list=new ArrayList();
            for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
            {
                DB.Teacher teacher= (DB.Teacher) iterator.next();
                List list1=new ArrayList();
                list1.add(teacher.getId());
                list1.add(teacher.getName());
                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
//            transaction.commit();
            session.close();
            return null;
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("delete")
    public String delete(@FormParam("param1") int tid) {
        Session session = DB.Global.getSession();
        Transaction t = session.beginTransaction();
        DB.Teacher teacher = session.load(DB.Teacher.class, tid);
        if (teacher== null){
            t.commit();
            session.close();
            return "0";
        }
        else {
            org.hibernate.query.Query query=session.createQuery("delete from TNotification s where s.teacher.id=:id").setParameter("id",tid);
            query.executeUpdate();
            org.hibernate.query.Query query1=session.createQuery("delete from LabAttendance s where s.teacher.id=:id").setParameter("id",tid);
            query1.executeUpdate();
            org.hibernate.query.Query query2=session.createQuery("delete from LabTimetable s where s.labInstructor.teacher.id=:id").setParameter("id",tid);
            query2.executeUpdate();
            org.hibernate.query.Query query3=session.createQuery("delete from LabInstructor s where s.teacher.id=:id").setParameter("id",tid);
            query3.executeUpdate();
            org.hibernate.query.Query query4=session.createQuery("delete from SubjectAttendance s where s.teacher.id=:id").setParameter("id",tid);
            query4.executeUpdate();
            org.hibernate.query.Query query5=session.createQuery("delete from SubjectTimetable s where s.subject.teacher.id=:id").setParameter("id",tid);
            query5.executeUpdate();
            org.hibernate.query.Query query6=session.createQuery("delete from Subject s where s.teacher.id=:id").setParameter("id",tid);
            query6.executeUpdate();
            session.delete(teacher);
            t.commit();
            session.close();
            return "1";

        }
//        t.commit();
//        session.close();
//        return "0";
    }
}
