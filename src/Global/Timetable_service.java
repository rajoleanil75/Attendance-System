package Global;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
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
    public List viewTeacherDayWise(@FormParam("param1")int day, @FormParam("param2") int tid, @FormParam("param3")String dt)
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
                t.setSubtname("");
                t.setLabtname("");
                t.setSubtname1(st.getSubject().getTeacher().getId());
                t.setLabtname1(-1);
                if(count==0) {
                    t.setExtname(st.getSubject().getName());
                    t.setFlag1(0);
                }
                else
                {
                    List<SubjectAttendance> list1=session.createQuery("from SubjectAttendance s where s.subjectTimetable.slotno=:id and s.subjectTimetable.day=:id1 and s.subjectTimetable.subject.id=:id2 and s.subjectTimetable.division.name=:id3 and s.subjectTimetable.division.csClass.id=:id4 and s.date=:id5").setParameter("id",st.getSlotno()).setParameter("id1",st.getDay()).setParameter("id2",st.getSubject().getId()).setParameter("id3",st.getDivision().getName()).setParameter("id4",st.getDivision().getCsClass().getId()).setParameter("id5",localDate).list();
                    String extname="";
                    int tn=0;
                    for( Iterator iterator1 = list1.iterator(); iterator1.hasNext();)
                    {
                        DB.SubjectAttendance s= (SubjectAttendance) iterator1.next();
                        extname=st.getSubject().getName()+" ("+s.getTeacher().getName()+")";
                        tn=s.getTeacher().getId();
                        break;
                    }
                    if(tn==st.getSubject().getTeacher().getId())
                        t.setExtname(st.getSubject().getName());
                    else
                        t.setExtname(extname);
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
                t.setSubtname("");
                t.setLabtname("");
                t.setSubtname1(-1);
                t.setLabtname1(st.getLabInstructor().getTeacher().getId());
                if(count==0) {
                    t.setExtname(st.getLabInstructor().getLabBatch().getName());
                    t.setFlag1(0);
                }
                else
                {
                    List<LabAttendance> list1=session.createQuery("from LabAttendance s where s.labTimetable.day=:id and s.labTimetable.labInstructor.teacher.id=:id1 and s.labTimetable.labInstructor.labBatch.name=:id2 and s.labTimetable.labInstructor.labBatch.csClass.id=:id3 and s.date=:id4").setParameter("id4",localDate).setParameter("id3",st.getLabInstructor().getLabBatch().getCsClass().getId()).setParameter("id2",st.getLabInstructor().getLabBatch().getName()).setParameter("id",st.getDay()).setParameter("id1",st.getLabInstructor().getTeacher().getId()).list();
                    String extname= "";
                    int tn=0;
                    for( Iterator iterator1 = list1.iterator(); iterator1.hasNext();)
                    {
                        DB.LabAttendance s= (LabAttendance) iterator1.next();
                        extname=st.getLabInstructor().getLabBatch().getName()+" ("+s.getTeacher().getName()+")";
                        tn=s.getTeacher().getId();
                        break;
                    }
                    if(tn==st.getLabInstructor().getTeacher().getId())
                        t.setExtname(st.getLabInstructor().getLabBatch().getName());
                    else
                        t.setExtname(extname);
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
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewClassDayWise")
    public List viewClassDayWise(@FormParam("param1")int day, @FormParam("param2") int clid, @FormParam("param3")String dt)
    {
        Session session = DB.Global.getSession();
        Transaction transaction=session.beginTransaction();
        try {

            LocalDate localDate = LocalDate.parse(dt);
            java.util.List<DB.SubjectTimetable> tlist=session.createQuery("from SubjectTimetable s where s.day=:id and s.subject.CSClass.id=:id1 order by s.stime asc ").setParameter("id",day).setParameter("id1",clid).list();
            List<LabTimetable> tlist1=session.createQuery("from LabTimetable s where s.day=:id and s.labInstructor.labBatch.csClass.id=:id1 order by s.stime asc ").setParameter("id",day).setParameter("id1",clid).list();

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
                t.setSubtname(st.getSubject().getName()+" ("+st.getSubject().getTeacher().getName()+")");
                t.setLabtname("");
                t.setSubtname1(st.getSubject().getTeacher().getId());
                t.setLabtname1(-1);
                if(count==0) {
                    t.setExtname("");
                    t.setFlag1(0);
                }
                else
                {
                    t.setExtname("");
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
                t.setSubtname("");
                t.setLabtname(st.getLabInstructor().getLabBatch().getName()+" ("+st.getLabInstructor().getTeacher().getName()+")");
                t.setSubtname1(-1);
                t.setLabtname1(st.getLabInstructor().getTeacher().getId());
                if(count==0) {
                    t.setExtname("");
                    t.setFlag1(0);
                }
                else
                {
                    t.setExtname("");
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
            session.close();
            return null;
        }
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewClassDayTeachWise")
    public List viewClassDayTeachWise(@FormParam("param1")int day, @FormParam("param2") int clid, @FormParam("param3")String dt,@FormParam("param4")int tid)
    {
        Session session = DB.Global.getSession();
        Transaction transaction=session.beginTransaction();
        try {

            LocalDate localDate = LocalDate.parse(dt);
            java.util.List<DB.SubjectTimetable> tlist=session.createQuery("from SubjectTimetable s where s.day=:id and s.subject.CSClass.id=:id1 order by s.stime asc ").setParameter("id",day).setParameter("id1",clid).list();
            List<LabTimetable> tlist1=session.createQuery("from LabTimetable s where s.day=:id and s.labInstructor.labBatch.csClass.id=:id1 order by s.stime asc ").setParameter("id",day).setParameter("id1",clid).list();

            ArrayList<Timetable> list=new ArrayList<Timetable>();
            for( Iterator iterator = tlist.iterator(); iterator.hasNext();)
            {
                DB.SubjectTimetable st= (SubjectTimetable) iterator.next();
                Query query=session.createQuery("select count(*) from SubjectAttendance s where s.subjectTimetable.slotno=:id and s.subjectTimetable.day=:id1 and s.subjectTimetable.subject.id=:id2 and s.subjectTimetable.division.name=:id3 and s.subjectTimetable.division.csClass.id=:id4 and s.date=:id5 and s.teacher.id=:id6").setParameter("id6",tid).setParameter("id",st.getSlotno()).setParameter("id1",st.getDay()).setParameter("id2",st.getSubject().getId()).setParameter("id3",st.getDivision().getName()).setParameter("id4",st.getDivision().getCsClass().getId()).setParameter("id5",localDate);
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
                t.setSubtname(st.getSubject().getName()+" ("+st.getSubject().getTeacher().getName()+")");
                t.setLabtname("");
                t.setSubtname1(st.getSubject().getTeacher().getId());
                t.setLabtname1(-1);
                if(count==0) {
                    t.setExtname("");
                    t.setFlag1(0);
                }
                else
                {
                    t.setExtname("");
                    t.setFlag1(1);
                }
                list.add(t);
            }
            for( Iterator iterator = tlist1.iterator(); iterator.hasNext();)
            {
                DB.LabTimetable st= (LabTimetable) iterator.next();
                Query query=session.createQuery("select count(*) from LabAttendance s where s.labTimetable.day=:id and s.labTimetable.labInstructor.teacher.id=:id1 and s.labTimetable.labInstructor.labBatch.name=:id2 and s.labTimetable.labInstructor.labBatch.csClass.id=:id3 and s.date=:id4 and s.teacher.id=:id5").setParameter("id5",tid).setParameter("id4",localDate).setParameter("id3",st.getLabInstructor().getLabBatch().getCsClass().getId()).setParameter("id2",st.getLabInstructor().getLabBatch().getName()).setParameter("id",st.getDay()).setParameter("id1",st.getLabInstructor().getTeacher().getId());
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
                t.setSubtname("");
                t.setLabtname(st.getLabInstructor().getLabBatch().getName()+" ("+st.getLabInstructor().getTeacher().getName()+")");
                t.setSubtname1(-1);
                t.setLabtname1(st.getLabInstructor().getTeacher().getId());
                if(count==0) {
                    t.setExtname("");
                    t.setFlag1(0);
                }
                else
                {
                    t.setExtname("");
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
            session.close();
            return null;
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("check_slots")
    public String checkSlots(@FormParam("param1") int slotno, @FormParam("param2") int day, @FormParam("param3")String dname, @FormParam("param4")int clid,@FormParam("param5")String dt)
    {
        Session session= Global.getSession();
        Transaction transaction=session.beginTransaction();
        try {
            LocalDate date=LocalDate.parse(dt);
            List<SubjectTimetable> list=session.createQuery("from SubjectTimetable s where s.slotno>=:id and s.day=:id1 and s.division.name=:id2 and s.division.csClass.id=:id3").setParameter("id",slotno).setParameter("id1",day).setParameter("id2",dname).setParameter("id3",clid).list();
            int cnt=0;
            for(Iterator iterator = list.iterator(); iterator.hasNext();)
            {
                SubjectTimetable st= (SubjectTimetable) iterator.next();
                Query query=session.createQuery("select count(*) from SubjectAttendance s where s.subjectTimetable.slotno=:id and s.subjectTimetable.day=:id1 and s.subjectTimetable.subject.id=:id2 and s.subjectTimetable.division.name=:id3 and s.subjectTimetable.division.csClass.id=:id4 and s.date=:id5 ").setParameter("id",st.getSlotno()).setParameter("id1",st.getDay()).setParameter("id2",st.getSubject().getId()).setParameter("id3",st.getDivision().getName()).setParameter("id4",st.getDivision().getCsClass().getId()).setParameter("id5",date);
                Long count = (Long)query.uniqueResult();
                if(count>0)
                {
                    break;
                }
                else if(count==0)
                {
                    cnt++;
                }
            }
            transaction.commit();
            session.close();
            return String.valueOf(cnt);

        }
        catch (Exception e)
        {
            session.close();
            return "0";
        }
    }
}
