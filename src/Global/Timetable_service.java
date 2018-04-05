package Global;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.jws.WebService;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Created by Anil on 03/04/2018
 */
@Path("/timetable_services")
@WebService
public class Timetable_service {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewTeacherDayWise")
    public List add(@FormParam("param1")int day, @FormParam("param2") int tid, @FormParam("param3")String dt)
    {
        Session session = DB.Global.getSession();
        Transaction transaction=session.beginTransaction();
        try {

            LocalDate localDate = LocalDate.parse(dt);
            java.util.List<DB.SubjectTimetable> tlist=session.createQuery("from SubjectTimetable s where s.day=:id and s.subject.teacher.id=:id1 order by s.stime asc ").setParameter("id",day).setParameter("id1",tid).list();
            List<LabTimetable> tlist1=session.createQuery("from LabTimetable s where s.day=:id and s.labInstructor.teacher.id=:id1 order by s.stime asc ").setParameter("id",day).setParameter("id1",tid).list();

//            List list=new ArrayList();
            ArrayList<Timetable> list=new ArrayList<Timetable>();
            for( Iterator iterator = tlist.iterator(); iterator.hasNext();)
            {
                DB.SubjectTimetable st= (SubjectTimetable) iterator.next();
                Query query=session.createQuery("select count(*) from SubjectAttendance s where s.subjectTimetable.slotno=:id and s.subjectTimetable.day=:id1 and s.subjectTimetable.subject.id=:id2 and s.subjectTimetable.division.name=:id3 and s.subjectTimetable.division.csClass.id=:id4 and s.date=:id5").setParameter("id",st.getSlotno()).setParameter("id1",st.getDay()).setParameter("id2",st.getSubject().getId()).setParameter("id3",st.getDivision().getName()).setParameter("id4",st.getDivision().getCsClass().getId()).setParameter("id5",localDate);
                Long count = (Long)query.uniqueResult();
                Timetable t = new Timetable();
                t.setSlotno(st.getSlotno());
                t.setDay(st.getDay());
                t.setSubjid(st.getSubject().getId());
                t.setClid(st.getDivision().getCsClass().getId());
                t.setFlag(1);
                t.setSname(st.getSubject().getName());
                t.setLname("-");
                t.setDname(st.getDivision().getName());
                t.setClname(st.getDivision().getCsClass().getName());
                t.setStime(st.getStime());
                t.setEtime(st.getEtime());
                if(count==0) {
                    t.setFlag1(0);
                }
                else
                {
                    t.setFlag1(1);
                }
                list.add(t);
            }
            for( Iterator iterator = tlist1.iterator(); iterator.hasNext();)
            {

                DB.LabTimetable st= (LabTimetable) iterator.next();
                Query query=session.createQuery("select count(*) from LabAttendance s where s.labTimetable.day=:id and s.labTimetable.labInstructor.teacher.id=:id1 and s.labTimetable.labInstructor.labBatch.name=:id2 and s.labTimetable.labInstructor.labBatch.csClass.id=:id3 and s.date=:id4").setParameter("id4",localDate).setParameter("id3",st.getLabInstructor().getLabBatch().getCsClass().getId()).setParameter("id2",st.getLabInstructor().getLabBatch().getName()).setParameter("id",st.getDay()).setParameter("id1",st.getLabInstructor().getTeacher().getId());
                Long count = (Long)query.uniqueResult();
                Timetable t=new Timetable();
                t.setSlotno(0);
                t.setDay(st.getDay());
                t.setSubjid("-");
                t.setClid(st.getLabInstructor().getLabBatch().getCsClass().getId());
                t.setFlag(0);
                t.setSname("-");
                t.setLname(st.getLabInstructor().getLabBatch().getName());
                t.setDname("-");
                t.setClname(st.getLabInstructor().getLabBatch().getCsClass().getName());
                t.setStime(st.getStime());
                t.setEtime(st.getEtime());
                if(count==0) {
                    t.setFlag1(0);
                }
                else
                {
                    t.setFlag1(1);
                }
                list.add(t);
            }
            transaction.commit();
            session.close();
            Collections.sort(list,new Sortbytime());
            return list;
        }
        catch (Exception e)
        {
//            transaction.commit();
            session.close();
//            List list=new ArrayList();
//            list.add("Hi");
            return null;
        }


//        return "1";
    }
}
