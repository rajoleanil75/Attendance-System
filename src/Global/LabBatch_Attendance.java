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
@Path("/labbatch_att_services")
@WebService
public class LabBatch_Attendance {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1")String slist, @FormParam("param3") int day, @FormParam("param5")String lname, @FormParam("param6")int clid, @FormParam("param7")int tid, @FormParam("param8")String dt,@FormParam("param9")int tid1)
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
            DB.LabTimetable l= (LabTimetable) session.createQuery("from LabTimetable s where s.day=:id and s.labInstructor.labBatch.name=:id1 and s.labInstructor.teacher.id=:id2 and s.labInstructor.labBatch.csClass.id=:id3").setParameter("id3",clid).setParameter("id2",tid1).setParameter("id1",lname).setParameter("id",day).uniqueResult();
            DB.Teacher teacher= (DB.Teacher) session.createQuery("from Teacher s where s.id=:id").setParameter("id",tid).uniqueResult();

            for (int i = 0; i < arrayObj.size(); i++)
            {
                JSONObject jsonObj = (JSONObject) arrayObj.get(i);
                if(jsonObj!=null)
                {
                    int roll= Integer.parseInt(String.valueOf(jsonObj.get("roll")));
                    int flag= Integer.parseInt(String.valueOf(jsonObj.get("flag")));
                    String dname= (String) jsonObj.get("dname");
                    Student student= (Student) session.createQuery("from Student s where s.roll=:id and s.division.name=:id1 and s.division.csClass.id=:id2").setParameter("id2",clid).setParameter("id1",dname).setParameter("id",roll).uniqueResult();
                    LabAttendance s =new LabAttendance();
                    s.setStudent(student);
                    s.setDate(date);
                    s.setLabTimetable(l);
                    s.setTeacher(teacher);
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
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewDateWise")
    public List viewDateWise(@FormParam("param1")int tid1, @FormParam("param3") int day, @FormParam("param5")String lname, @FormParam("param6")int clid, @FormParam("param7")int tid, @FormParam("param8")String dt)
    {
        Session session= Global.getSession();
        Transaction transaction=session.beginTransaction();
        try
        {
            LocalDate date=LocalDate.parse(dt);
            List<LabAttendance> list=session.createQuery("from LabAttendance s where s.labTimetable.day=:id and s.labTimetable.labInstructor.labBatch.name=:id1 and s.labTimetable.labInstructor.labBatch.csClass.id=:id2 and s.teacher.id=:id3 and s.date=:id4 and s.labTimetable.labInstructor.teacher.id=:id5 order by s.student.roll asc ").setParameter("id",day).setParameter("id1",lname).setParameter("id2",clid).setParameter("id4",date).setParameter("id3",tid).setParameter("id5",tid1).list();
            List list2=new ArrayList();
            for(Iterator iterator = list.iterator(); iterator.hasNext();)
            {
                LabAttendance s= (LabAttendance) iterator.next();
                List list1=new ArrayList();
                list1.add(s.getStudent().getRoll());
                list1.add(s.getStudent().getName());
                list1.add(s.getStudent().getRoll());
                list1.add(s.getStudent().getDivision().getName());
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
    public String update(@FormParam("param1")String slist,@FormParam("param2")int tid1, @FormParam("param3") int day, @FormParam("param5")String lname, @FormParam("param6")int clid, @FormParam("param7")int tid, @FormParam("param8")String dt)
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
                    String dname= (String) jsonObj.get("dname");
                    LabAttendance s= (LabAttendance) session.createQuery("from LabAttendance s where s.labTimetable.day=:id and s.labTimetable.labInstructor.labBatch.name=:id1 and s.labTimetable.labInstructor.labBatch.csClass.id=:id2 and s.labTimetable.labInstructor.teacher.id=:id3 and s.date=:id4 and s.teacher.id=:id5 and s.student.roll=:id6 and s.student.division.name=:id7 and s.student.division.csClass.id=:id8").setParameter("id",day).setParameter("id1",lname).setParameter("id2",clid).setParameter("id3",tid1).setParameter("id4",date).setParameter("id5",tid).setParameter("id6",roll).setParameter("id7",dname).setParameter("id8",clid).uniqueResult();
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
    public String findTeacher( @FormParam("param3") int day, @FormParam("param5")String lname, @FormParam("param6")int clid, @FormParam("param7")int tid, @FormParam("param8")String dt)
    {
        Session session= Global.getSession();
        Transaction transaction=session.beginTransaction();
        try
        {
            LocalDate date=LocalDate.parse(dt);
            List<LabAttendance> list=session.createQuery("from LabAttendance s where s.labTimetable.day=:id and s.labTimetable.labInstructor.labBatch.name=:id1 and s.labTimetable.labInstructor.labBatch.csClass.id=:id2  and s.date=:id4 and s.labTimetable.labInstructor.teacher.id=:id5").setParameter("id",day).setParameter("id1",lname).setParameter("id2",clid).setParameter("id4",date).setParameter("id5",tid).list();
            String msg="";
            for(Iterator iterator = list.iterator(); iterator.hasNext();)
            {
                LabAttendance s= (LabAttendance) iterator.next();
                msg=s.getTeacher().getName();
                break;
            }
            transaction.commit();
            session.close();
            return msg;
        }
        catch (Exception e)
        {
//            transaction.commit();
            session.close();
            return null;
        }
    }
}
