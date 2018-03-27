package Global;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalTime;

/**
 * Created by Anil on 26/03/2018
 */
@Path("/lab_time_services")
@WebService
public class Lab_Timetable {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add(@FormParam("param1") int day, @FormParam("param2")String lid, @FormParam("param3") int tid, @FormParam("param4")String did,@FormParam("param5")int clid,@FormParam("param6")String t1, @FormParam("param7")String t2)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            LabInstructor labInstructor= (LabInstructor) session.createQuery("from LabInstructor s where s.teacher.id=:id and s.labBatch.name=:id1 and s.labBatch.division.name=:id2 and s.labBatch.division.csClass.id=:id3").setParameter("id",tid).setParameter("id1",lid).setParameter("id2",did).setParameter("id3",clid).uniqueResult();
            LabTimetable labTimetable=new LabTimetable();
            labTimetable.setLabInstructor(labInstructor);
            labTimetable.setDay(day);

            LocalTime l1=LocalTime.parse(t1);
            LocalTime l2=LocalTime.parse(t2);
            labTimetable.setStime(l1);
            labTimetable.setEtime(l2);

            session.persist(labTimetable);
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
}
