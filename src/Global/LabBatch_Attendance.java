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

/**
 * Created by Anil on 05/04/2018
 */
@Path("/labbatch_att_services")
@WebService
public class LabBatch_Attendance {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1")String slist, @FormParam("param3") int day, @FormParam("param5")String lname, @FormParam("param6")int clid, @FormParam("param7")int tid, @FormParam("param8")String dt)
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
            DB.LabTimetable l= (LabTimetable) session.createQuery("from LabTimetable s where s.day=:id and s.labInstructor.labBatch.name=:id1 and s.labInstructor.teacher.id=:id2 and s.labInstructor.labBatch.csClass.id=:id3").setParameter("id3",clid).setParameter("id2",tid).setParameter("id1",lname).setParameter("id",day).uniqueResult();
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
}
