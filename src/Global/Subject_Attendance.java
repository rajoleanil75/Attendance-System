package Global;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.jws.WebService;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anil on 05/04/2018
 */
@Path("/subject_att_services")
@WebService
public class Subject_Attendance {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1")String slist, @FormParam("param2") int slotno, @FormParam("param3") int day,@FormParam("param4")String subjid, @FormParam("param5")String dname, @FormParam("param6")int clid,@FormParam("param7")int tid,@FormParam("param8")String dt)
    {
        Session session= Global.getSession();
        Transaction transaction=session.beginTransaction();
        try
        {
            Object object=null;
            JSONArray arrayObj=null;
            JSONParser jsonParser=new JSONParser();
            object=jsonParser.parse(slist);
            arrayObj=(JSONArray) object;

            LocalDate date=LocalDate.parse(dt);
            SubjectTimetable subject_timetable= (SubjectTimetable) session.createQuery("from SubjectTimetable s where s.slotno=:id and s.day=:id1 and s.subject.id=:id2 and s.division.name=:id3 and s.division.csClass.id=:id4").setParameter("id4",clid).setParameter("id3",dname).setParameter("id2",subjid).setParameter("id1",day).setParameter("id",slotno).uniqueResult();
            DB.Teacher teacher= (DB.Teacher) session.createQuery("from Teacher s where s.id=:id").setParameter("id",tid).uniqueResult();
            Subject subject= (Subject) session.createQuery("from Subject s where s.id=:id").setParameter("id",subjid).uniqueResult();

            for (int i = 0; i < arrayObj.size(); i++)
            {
                JSONObject jsonObj = (JSONObject) arrayObj.get(i);
                if(jsonObj!=null)
                {
                    int roll= Integer.parseInt(String.valueOf(jsonObj.get("roll")));
                    int flag= Integer.parseInt(String.valueOf(jsonObj.get("flag")));
                    Student student= (Student) session.createQuery("from Student s where s.roll=:id and s.division.name=:id1 and s.division.csClass.id=:id2").setParameter("id2",clid).setParameter("id1",dname).setParameter("id",roll).uniqueResult();
                    SubjectAttendance s =new SubjectAttendance();
                    s.setSubjectTimetable(subject_timetable);
                    s.setTeacher(teacher);
                    s.setSubject(subject);
                    s.setDate(date);
                    s.setFlag(flag);
                    s.setStudent(student);
                    session.persist(s);
                }
            }
            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
//            transaction.commit();
            session.close();
            return ""+e+"";
        }
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewDateWise")
    public List viewDateWise(@FormParam("param2") int slotno, @FormParam("param3") int day,@FormParam("param4")String subjid, @FormParam("param5")String dname, @FormParam("param6")int clid,@FormParam("param7")int tid,@FormParam("param8")String dt)
    {
        Session session= Global.getSession();
        Transaction transaction=session.beginTransaction();
        try
        {
            LocalDate date=LocalDate.parse(dt);


//            SubjectTimetable subject_timetable= (SubjectTimetable) session.createQuery("from SubjectTimetable s where s.slotno=:id and s.day=:id1 and s.subject.id=:id2 and s.division.name=:id3 and s.division.csClass.id=:id4").setParameter("id4",clid).setParameter("id3",dname).setParameter("id2",subjid).setParameter("id1",day).setParameter("id",slotno).uniqueResult();
//            DB.Teacher teacher= (DB.Teacher) session.createQuery("from Teacher s where s.id=:id").setParameter("id",tid).uniqueResult();
//            Subject subject= (Subject) session.createQuery("from Subject s where s.id=:id").setParameter("id",subjid).uniqueResult();
            List<SubjectAttendance> list=session.createQuery("from SubjectAttendance s where s.subjectTimetable.slotno=:id and s.subjectTimetable.day=:id1 and s.subjectTimetable.subject.id=:id2 and s.subjectTimetable.division.name=:id3 and s.subjectTimetable.division.csClass.id=:id4 and s.teacher.id=:id5 and s.date=:id6 and s.subject.teacher.id=:id7").setParameter("id7",tid).setParameter("id6",date).setParameter("id5",tid).setParameter("id4",clid).setParameter("id3",dname).setParameter("id2",subjid).setParameter("id1",day).setParameter("id",slotno).list();
            List list2=new ArrayList();
            for(Iterator iterator = list.iterator(); iterator.hasNext();)
            {
                SubjectAttendance s= (SubjectAttendance) iterator.next();
                List list1=new ArrayList();
                list1.add(s.getStudent().getRoll());
                list1.add(s.getStudent().getName());
                list1.add(s.getStudent().getRoll());
                list1.add(s.getFlag());
                list2.add(list1);
            }
            transaction.commit();
            session.close();
            return list2;
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
    @Path("update")
    public String update(@FormParam("param1")String slist, @FormParam("param2") int slotno, @FormParam("param3") int day,@FormParam("param4")String subjid, @FormParam("param5")String dname, @FormParam("param6")int clid,@FormParam("param7")int tid,@FormParam("param8")String dt)
    {
        Session session= Global.getSession();
        Transaction transaction=session.beginTransaction();
        try
        {
            Object object=null;
            JSONArray arrayObj=null;
            JSONParser jsonParser=new JSONParser();
            object=jsonParser.parse(slist);
            arrayObj=(JSONArray) object;

            LocalDate date=LocalDate.parse(dt);

            for (int i = 0; i < arrayObj.size(); i++)
            {
                JSONObject jsonObj = (JSONObject) arrayObj.get(i);
                if(jsonObj!=null)
                {
                    int roll= Integer.parseInt(String.valueOf(jsonObj.get("roll")));
                    int flag= Integer.parseInt(String.valueOf(jsonObj.get("flag")));

                    SubjectAttendance s= (SubjectAttendance) session.createQuery("from SubjectAttendance s where s.subjectTimetable.slotno=:id and s.subjectTimetable.day=:id1 and s.subjectTimetable.subject.id=:id2 and s.subjectTimetable.division.name=:id3 and s.subjectTimetable.division.csClass.id=:id4 and s.teacher.id=:id5 and s.date=:id6 and s.student.roll=:id7 and s.student.division.name=:id8 and s.student.division.csClass.id=:id9").setParameter("id9",clid).setParameter("id8",dname).setParameter("id7",roll).setParameter("id6",date).setParameter("id5",tid).setParameter("id4",clid).setParameter("id3",dname).setParameter("id2",subjid).setParameter("id1",day).setParameter("id",slotno).uniqueResult();
                    s.setFlag(flag);
                    session.persist(s);
                }
            }
            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
//            transaction.commit();
            session.close();
            return ""+e+"";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("findteacher")
    public String findteacher(@FormParam("param2") int slotno, @FormParam("param3") int day,@FormParam("param4")String subjid, @FormParam("param5")String dname, @FormParam("param6")int clid,@FormParam("param7")int tid,@FormParam("param8")String dt)
    {
        Session session= Global.getSession();
        Transaction transaction=session.beginTransaction();
        try
        {
            LocalDate date=LocalDate.parse(dt);


//            SubjectTimetable subject_timetable= (SubjectTimetable) session.createQuery("from SubjectTimetable s where s.slotno=:id and s.day=:id1 and s.subject.id=:id2 and s.division.name=:id3 and s.division.csClass.id=:id4").setParameter("id4",clid).setParameter("id3",dname).setParameter("id2",subjid).setParameter("id1",day).setParameter("id",slotno).uniqueResult();
//            DB.Teacher teacher= (DB.Teacher) session.createQuery("from Teacher s where s.id=:id").setParameter("id",tid).uniqueResult();
//            Subject subject= (Subject) session.createQuery("from Subject s where s.id=:id").setParameter("id",subjid).uniqueResult();
            List<SubjectAttendance> list=session.createQuery("from SubjectAttendance s where s.subjectTimetable.slotno=:id and s.subjectTimetable.day=:id1 and s.subjectTimetable.subject.id=:id2 and s.subjectTimetable.division.name=:id3 and s.subjectTimetable.division.csClass.id=:id4 and s.subjectTimetable.subject.teacher.id=:id5 and s.date=:id6").setParameter("id6",date).setParameter("id5",tid).setParameter("id4",clid).setParameter("id3",dname).setParameter("id2",subjid).setParameter("id1",day).setParameter("id",slotno).list();
            String tname="";
            for(Iterator iterator = list.iterator(); iterator.hasNext();)
            {
                SubjectAttendance s= (SubjectAttendance) iterator.next();
                tname=s.getTeacher().getName();
                break;
            }
            transaction.commit();
            session.close();
            return tname;
        }
        catch (Exception e)
        {
//            transaction.commit();
            session.close();
            return null;
        }
    }
}
