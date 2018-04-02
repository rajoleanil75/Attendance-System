package Global;

import DB.*;
import DB.Teacher;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Created by Anil on 31/01/2018
 */
@Path("/subject_services")
@WebService
public class Subject_Service
{
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1") String scode, @FormParam("param2") String sname, @FormParam("param3")int tid, @FormParam("param4") int cid)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            CSClass csClass = (CSClass) session.createQuery("from CSClass c where c.id= :id").setParameter("id", cid).uniqueResult();
            Teacher teacher = (Teacher) session.createQuery("from Teacher t where t.id=:id").setParameter("id", tid).uniqueResult();
            Subject subject = new Subject();
            subject.setId(scode);
            subject.setName(sname);
            subject.setCSClass(csClass);
            subject.setTeacher(teacher);
            session.persist(subject);
            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            transaction.commit();
            session.close();
            return "E";
        }
    }
    @POST
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll()
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        List<Subject> tlist=session.createQuery("from Subject").list();
        List list=new ArrayList();
        for(Iterator iterator=tlist.iterator();iterator.hasNext();)
        {
            Subject subject= (Subject) iterator.next();
            List list1=new ArrayList();
            list1.add(subject.getId());
            list1.add(subject.getName());
            list1.add(subject.getCSClass().getCourse().getId());
            list1.add(subject.getCSClass().getId());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }

    @POST
    @Path("getCourseWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List getCourseWise(@FormParam("param1") int cid)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        List<Subject> tlist=session.createQuery("from Subject s where s.CSClass.course.id=:id").setParameter("id",cid).list();
        List list=new ArrayList();
        for(Iterator iterator=tlist.iterator();iterator.hasNext();)
        {
            Subject subject= (Subject) iterator.next();
            List list1=new ArrayList();
            list1.add(subject.getId());
            list1.add(subject.getName());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }
    @POST
    @Path("getClassWise1")
    @Produces(MediaType.APPLICATION_JSON)
    public List getClassWise1(@FormParam("param1") int cid)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        List<Subject> tlist=session.createQuery("from Subject s where s.CSClass.id=:id").setParameter("id",cid).list();
        List list=new ArrayList();
        for(Iterator iterator=tlist.iterator();iterator.hasNext();)
        {
            Subject subject= (Subject) iterator.next();
            List list1=new ArrayList();
            list1.add(subject.getId());
            list1.add(subject.getName());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }
    @POST
    @Path("getCourseTeachWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List getCourseTeachWise(@FormParam("param1") int cid,@FormParam("param2") int tid)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        List<Subject> tlist=session.createQuery("from Subject s where s.CSClass.course.id=:id and s.teacher.id=:id1").setParameter("id",cid).setParameter("id1",tid).list();
        List list=new ArrayList();
        for(Iterator iterator=tlist.iterator();iterator.hasNext();)
        {
            Subject subject= (Subject) iterator.next();
            List list1=new ArrayList();
            list1.add(subject.getId());
            list1.add(subject.getName());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }

    @POST
    @Path("getClassWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List getClassWise(@FormParam("param1") int cid)
    {
        Session session = Global.getSession();
        Transaction t = session.beginTransaction();
        try {

            List<Subject> tlist = session.createQuery("from Subject s where s.CSClass.id=:id").setParameter("id", cid).list();
            List list = new ArrayList();
            for (Iterator iterator = tlist.iterator(); iterator.hasNext(); ) {
                Subject subject = (Subject) iterator.next();
                List list1 = new ArrayList();
                list1.add(subject.getId());
                list1.add(subject.getName()+", "+subject.getTeacher().getName());
                list.add(list1);
            }
            t.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            t.commit();
            session.close();
            return (List) e;
        }
    }

    @POST
    @Path("getTeacherWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List getTeacherWise(@FormParam("param1") int cid)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        List<Subject> tlist=session.createQuery("from Subject s where s.teacher.id=:id").setParameter("id",cid).list();
        List list=new ArrayList();
        for(Iterator iterator=tlist.iterator();iterator.hasNext();)
        {
            Subject subject= (Subject) iterator.next();
            List list1=new ArrayList();
            list1.add(subject.getId());
            list1.add(subject.getName());
            list.add(list1);
        }
        t.commit();
        session.close();
        return list;
    }
    @POST
    @Path("getsname")
    @Produces(MediaType.TEXT_PLAIN)
    public String getSName(@FormParam("param1") String cid)
    {
        Session session = Global.getSession();
        Transaction t = session.beginTransaction();
        try {

            Subject subject = session.load(Subject.class, cid);
            String s=subject.getName();
            t.commit();
            session.close();
            return s;
        }
        catch (Exception e)
        {
            t.commit();
            session.close();
            return "E";
        }
    }
    @POST
    @Path("getcousname")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCouSName(@FormParam("param1") String cid)
    {
        Session session = Global.getSession();
        Transaction t = session.beginTransaction();
        try {

            Subject subject = session.load(Subject.class, cid);
            String s=subject.getCSClass().getCourse().getName()+", Class: "+subject.getCSClass().getName()+", Subject: "+  subject.getName();
            t.commit();
            session.close();
            return s;
        }
        catch (Exception e)
        {
            t.commit();
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("delete")
    public String delete(@FormParam("param1") String tid)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        Subject subject= (Subject) session.createQuery("from Subject s where s.id=:id").setParameter("id",tid).uniqueResult();
        if (subject==null) {

            return "0";
        }
        else
        {
//            List<SubjectAttendance> list=session.createQuery("from SubjectAttendance s where s.subject.id=:id").setParameter("id",tid).list();
//            for(Iterator iterator = list.iterator(); iterator.hasNext();)
//            {
//                SubjectAttendance obj= (SubjectAttendance) iterator.next();
//                session.delete(obj);
//            }
//            List<SubjectTimetable> list1=session.createQuery("from SubjectTimetable s where s.subject.id=:id").setParameter("id",tid).list();
//            for(Iterator iterator = list1.iterator(); iterator.hasNext();)
//            {
//                SubjectTimetable obj= (SubjectTimetable) iterator.next();
//                session.delete(obj);
//            }


            Query query=session.createQuery("delete SubjectAttendance s where s.subject.id=:id").setParameter("id",tid);
            Query query1=session.createQuery("delete SubjectTimetable s where s.subject.id=:id").setParameter("id",tid);
            query.executeUpdate();
            query1.executeUpdate();
//            session.delete(subject);

            Query query2=session.createQuery("delete Subject s where s.id=:id").setParameter("id",tid);
            query2.executeUpdate();

            t.commit();
            session.close();
            return "1";
        }

    }
}