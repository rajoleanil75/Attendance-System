package Global;

import DB.*;
import DB.Teacher;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
 * Created by Anil on 13/02/2018
 */
@Path("/labbatch_services")
@WebService
public class LabBatch_Service {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1") String lname, @FormParam("param2") int from, @FormParam("param3") int to, @FormParam("param4") int cid, @FormParam("param5") String did)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

//            CSClass csClass = (CSClass) session.createQuery("from CSClass c where c.id= :id").setParameter("id", cid).uniqueResult();
            Division division =(Division) session.createQuery("from Division c where c.name= :id and c.csClass.id=:id1").setParameter("id1",cid).setParameter("id", did).uniqueResult();
            LabBatch labBatch=new LabBatch();
            labBatch.setName(lname);
            labBatch.setFrom(from);
            labBatch.setTo(to);
//            labBatch.setCSClass(csClass);
            labBatch.setDivision(division);
            session.persist(labBatch);
//            int i=labBatch.getId();
            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
//            transaction.commit();
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("addteacherlab")
    public String add(@FormParam("param1") int tname, @FormParam("param2") String lname,@FormParam("param3")int cid,@FormParam("param4")String did)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            DB.Teacher teacher= (Teacher) session.createQuery("from Teacher c where c.id= :id").setParameter("id", tname).uniqueResult();
            LabBatch labBatch= (LabBatch) session.createQuery("from LabBatch c where c.name= :id and c.division.name=:id1 and c.division.csClass.id=:id2").setParameter("id1",did).setParameter("id2",cid).setParameter("id", lname).uniqueResult();
            LabInstructor labInstructor=new LabInstructor();
            labInstructor.setLabBatch(labBatch);
            labInstructor.setTeacher(teacher);
            session.persist(labInstructor);
            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
//            transaction.commit();
            session.close();
            return "E";
        }
    }
    @POST
    @Path("getClassWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List getClassWise(@FormParam("param1") int cid)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        List<LabBatch> tlist=session.createQuery("from LabBatch s where s.division.csClass.id=:id").setParameter("id",cid).list();
        List list=new ArrayList();
        for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
        {
            LabBatch labBatch= (LabBatch) iterator.next();
            List list1=new ArrayList();
//            list1.add(labBatch.getId());
            list1.add(labBatch.getName());
            list1.add(labBatch.getFrom());
            list1.add(labBatch.getTo());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }

    @POST
    @Path("getTeacherWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List getTeacherWise(@FormParam("param1") int cid)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        List<LabInstructor> tlist=session.createQuery("from LabInstructor s where s.teacher.id=:id").setParameter("id",cid).list();
        List list=new ArrayList();
        for(Iterator iterator=tlist.iterator();iterator.hasNext();)
        {
            LabInstructor teacher_labBatch= (LabInstructor) iterator.next();
            List list1=new ArrayList();
//            list1.add(teacher_labBatch.getLabBatch().getId());
            list1.add(teacher_labBatch.getLabBatch().getName());
            list1.add(teacher_labBatch.getLabBatch().getFrom());
            list1.add(teacher_labBatch.getLabBatch().getTo());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("delete")
    public String delete(@FormParam("param1")String lid,@FormParam("param2")String did,@FormParam("param3")int clid)
    {
        Session session = DB.Global.getSession();
        Transaction t = session.beginTransaction();
        LabBatch labBatch= (LabBatch) session.createQuery("from LabBatch c where c.name= :id and c.division.name=:id1 and c.division.csClass.id=:id2").setParameter("id1",did).setParameter("id2",clid).setParameter("id", lid).uniqueResult();
        if (labBatch== null){
            t.commit();
            session.close();
            return "0";
        }
        else {
            Query query=session.createQuery("delete from LabAttendance s where s.labTimetable.labInstructor.labBatch.name=:id and s.labTimetable.labInstructor.labBatch.division.name=:id1 and s.labTimetable.labInstructor.labBatch.division.csClass.id=:id2").setParameter("id",lid).setParameter("id1",did).setParameter("id2",clid);
            Query query1=session.createQuery("delete from LabTimetable s where s.labInstructor.labBatch.name=:id and s.labInstructor.labBatch.division.name=:id1 and s.labInstructor.labBatch.division.csClass.id=:id2").setParameter("id",lid).setParameter("id1",did).setParameter("id2",clid);
            Query query2=session.createQuery("delete from LabInstructor s where s.labBatch.name=:id and s.labBatch.division.name=:id1 and s.labBatch.division.csClass.id=:id2").setParameter("id",lid).setParameter("id1",did).setParameter("id2",clid);
            query.executeUpdate();
            query1.executeUpdate();
            query2.executeUpdate();
            session.delete(labBatch);
            t.commit();
            session.close();
            return "1";

        }
    }
    @POST
    @Path("getClassDivWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List getClassDivWise(@FormParam("param1") int cid,@FormParam("param2")String did)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        List<LabInstructor> tlist=session.createQuery("from LabInstructor s where s.labBatch.division.name=:id and s.labBatch.division.csClass.id=:id1").setParameter("id",did).setParameter("id1",cid).list();
        List list=new ArrayList();
        for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
        {
            LabInstructor labInstructor= (LabInstructor) iterator.next();
            List list1=new ArrayList();
            list1.add(labInstructor.getLabBatch().getName()+"-"+labInstructor.getTeacher().getName());
            list1.add(labInstructor.getLabBatch().getName());
            list1.add(labInstructor.getTeacher().getId());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }
}
