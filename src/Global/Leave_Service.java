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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anil on 26/04/2018
 */
@Path("/leave_services")
@WebService
public class Leave_Service {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("addLeave")
    public String addLeave(@FormParam("param1") int tid, @FormParam("param2") String sdate, @FormParam("param3") String ltype, @FormParam("param4") String reason)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        try
        {
            LocalDate sd=LocalDate.parse(sdate);
            DB.Teacher teacher=session.load(Teacher.class,tid);
            DB.Leave leave=new Leave();
            leave.setTeacher(teacher);
            leave.setSdate(sd);
            leave.setLtype(ltype);
            leave.setFlag(0);
            leave.setReason(reason);
            LocalDate ddt=LocalDate.now();
            LocalTime ddt1=LocalTime.now();
            leave.setAdate(ddt);
            leave.setAtime(ddt1);
            session.save(leave);
            t.commit();
            t.begin();
            Leave leave1= (Leave) session.createQuery("from Leave s where s.teacher.id=:id and s.sdate=:id1 and s.ltype=:id2 and s.reason=:id3 and s.adate=:id4 and s.atime=:id5").setParameter("id",tid).setParameter("id1",sd).setParameter("id2",ltype).setParameter("id3",reason).setParameter("id4",ddt).setParameter("id5",ddt1).uniqueResult();
            int lid=leave1.getId();
            t.commit();
            session.close();
            return ""+lid+"";
        }
        catch (Exception e)
        {
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("updateLeave")
    public String updateLeave(@FormParam("param1") int tid)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        try
        {
            DB.Leave leave=session.get(Leave.class,tid);
            leave.setAdate(LocalDate.now());
            leave.setAtime(LocalTime.now());
            session.persist(leave);
            t.commit();
            session.close();
            return ""+tid+"";
        }
        catch (Exception e)
        {
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("accept")
    public String accept(@FormParam("param1") int tid)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        try
        {
            DB.Leave leave=session.get(Leave.class,tid);
            leave.setFlag(1);
            session.persist(leave);
            t.commit();
            session.close();
            return ""+tid+"";
        }
        catch (Exception e)
        {
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("reject")
    public String reject(@FormParam("param1") int tid)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        try
        {
            DB.Leave leave=session.get(Leave.class,tid);
            leave.setFlag(-1);
            session.persist(leave);
            t.commit();
            session.close();
            return ""+tid+"";
        }
        catch (Exception e)
        {
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("addLeave1")
    public String addLeave1(@FormParam("param1") int tid, @FormParam("param2") String sdate, @FormParam("param3") String ltype, @FormParam("param4") String reason,@FormParam("param5")String edate)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        try
        {
            LocalDate sd=LocalDate.parse(sdate);
            LocalDate ed=LocalDate.parse(edate);
            DB.Teacher teacher=session.load(Teacher.class,tid);
            DB.Leave leave=new Leave();
            leave.setTeacher(teacher);
            leave.setSdate(sd);
            leave.setEdate(ed);
            leave.setLtype(ltype);
            leave.setReason(reason);
            LocalDate ddt=LocalDate.now();
            LocalTime ddt1=LocalTime.now();
            leave.setAdate(ddt);
            leave.setAtime(ddt1);
            session.save(leave);
            t.commit();
            t.begin();
            Leave leave1= (Leave) session.createQuery("from Leave s where s.teacher.id=:id and s.sdate=:id1 and s.ltype=:id2 and s.reason=:id3 and s.adate=:id4 and s.atime=:id5").setParameter("id",tid).setParameter("id1",sd).setParameter("id2",ltype).setParameter("id3",reason).setParameter("id4",ddt).setParameter("id5",ddt1).uniqueResult();
            int lid=leave1.getId();
            t.commit();
            session.close();
            return ""+lid+"";
        }
        catch (Exception e)
        {
            session.close();
            return "E";
        }
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("addSubjectSlot")
    public String addSubjectSlot(@FormParam("param1") int slotno, @FormParam("param2") int day, @FormParam("param3") String subjid, @FormParam("param4") String dname, @FormParam("param5") int clid, @FormParam("param6") int tid, @FormParam("param7") String dt,@FormParam("param8")int leave_id)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        try
        {
            LocalDate sd=LocalDate.parse(dt);
            DB.Leave leave=session.load(DB.Leave.class,leave_id);
            SubjectTimetable subject_timetable = (SubjectTimetable) session.createQuery("from SubjectTimetable s where s.slotno=:id and s.day=:id1 and s.subject.id=:id2 and s.division.name=:id3 and s.division.csClass.id=:id4").setParameter("id4", clid).setParameter("id3", dname).setParameter("id2", subjid).setParameter("id1", day).setParameter("id", slotno).uniqueResult();
            DB.Teacher teacher = (DB.Teacher) session.createQuery("from Teacher s where s.id=:id").setParameter("id", tid).uniqueResult();
            DB.Sleave sleave=new Sleave();
            sleave.setSubjectTimetable(subject_timetable);
            sleave.setLeave(leave);
            if(tid!=-1)
                sleave.setTeacher(teacher);
            sleave.setDate(sd);
            session.save(sleave);
            t.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("updateSubjectSlot")
    public String updateSubjectSlot(@FormParam("param1") int slotno, @FormParam("param2") int day, @FormParam("param3") String subjid, @FormParam("param4") String dname, @FormParam("param5") int clid, @FormParam("param6") int tid, @FormParam("param7") String dt,@FormParam("param8")int leave_id)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        try
        {
            LocalDate sd=LocalDate.parse(dt);
            DB.Teacher teacher = (DB.Teacher) session.createQuery("from Teacher s where s.id=:id").setParameter("id", tid).uniqueResult();
            DB.Sleave sleave= (Sleave) session.createQuery("from Sleave s where s.subjectTimetable.slotno=:id and s.subjectTimetable.day=:id1 and s.subjectTimetable.subject.id=:id2 and s.subjectTimetable.division.name=:id3 and s.subjectTimetable.division.csClass.id=:id4 and s.leave.id=:id5 and s.date=:id6").setParameter("id",slotno).setParameter("id1",day).setParameter("id2",subjid).setParameter("id3",dname).setParameter("id4",clid).setParameter("id5",leave_id).setParameter("id6",sd).uniqueResult();
            if(tid!=-1) {
                sleave.setTeacher(teacher);
                session.persist(sleave);
            }
            t.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            session.close();
            return "E";
        }
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("addLabBatchSlot")
    public String addLabBatchSlot(@FormParam("param1") int day, @FormParam("param2") int atid, @FormParam("param3") String lname, @FormParam("param4") int clid, @FormParam("param5") int tid, @FormParam("param6") String dt,@FormParam("param7")int leave_id)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        try
        {
            LocalDate sd=LocalDate.parse(dt);
            DB.Leave leave=session.load(DB.Leave.class,leave_id);
            DB.LabTimetable l= (LabTimetable) session.createQuery("from LabTimetable s where s.day=:id and s.labInstructor.labBatch.name=:id1 and s.labInstructor.teacher.id=:id2 and s.labInstructor.labBatch.csClass.id=:id3").setParameter("id3",clid).setParameter("id2",atid).setParameter("id1",lname).setParameter("id",day).uniqueResult();
            DB.Teacher teacher= (DB.Teacher) session.createQuery("from Teacher s where s.id=:id").setParameter("id",tid).uniqueResult();
            DB.Lleave lleave=new Lleave();
            lleave.setLabTimetable(l);
            lleave.setLeave(leave);
            if(tid!=-1)
                lleave.setTeacher(teacher);
            lleave.setDate(sd);
            session.save(lleave);
            t.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("updateLabBatchSlot")
    public String updateLabBatchSlot(@FormParam("param1") int day, @FormParam("param2") int atid, @FormParam("param3") String lname, @FormParam("param4") int clid, @FormParam("param5") int tid, @FormParam("param6") String dt,@FormParam("param7")int leave_id)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        try
        {
            LocalDate sd=LocalDate.parse(dt);
//            DB.Leave leave=session.load(DB.Leave.class,leave_id);
//            DB.LabTimetable l= (LabTimetable) session.createQuery("from LabTimetable s where s.day=:id and s.labInstructor.labBatch.name=:id1 and s.labInstructor.teacher.id=:id2 and s.labInstructor.labBatch.csClass.id=:id3").setParameter("id3",clid).setParameter("id2",atid).setParameter("id1",lname).setParameter("id",day).uniqueResult();
//            DB.Teacher teacher= (DB.Teacher) session.createQuery("from Teacher s where s.id=:id").setParameter("id",tid).uniqueResult();
//            DB.Lleave lleave=new Lleave();
//            lleave.setLabTimetable(l);
//            lleave.setLeave(leave);
//            if(tid!=-1)
//                lleave.setTeacher(teacher);
//            lleave.setDate(sd);
//            session.save(lleave);
//            DB.Lleave lleave=session.createQuery("from Lleave s where s.labTimetable.day=:id ")
            t.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewTeacherWise")
    public List viewTeacherWise(@FormParam("param1")int tid)
    {
        Session session = DB.Global.getSession();
        Transaction t = session.beginTransaction();
        try {

            java.util.List<DB.Leave> tlist = session.createQuery("from Leave s where s.teacher.id=:id order by s.adate desc ,s.atime desc ").setParameter("id", tid).setMaxResults(25).list();
            List list = new ArrayList();
            for (Iterator iterator = tlist.iterator(); iterator.hasNext(); ) {
                DB.Leave s = (DB.Leave) iterator.next();
                List list1 = new ArrayList();
                list1.add(s.getAdate());
                list1.add(s.getLtype());
                list1.add(s.getSdate());
                list1.add(s.getEdate());
                list1.add(s.getReason());
                list1.add(s.getFlag());
                list1.add(s.getId());
                list.add(list1);
            }
            t.commit();
            session.close();
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
    @Path("viewTeacherReport")
    public List viewTeacherReport(@FormParam("param1")int tid,@FormParam("param2")String sd,@FormParam("param3")String ed)
    {
        Session session = DB.Global.getSession();
        Transaction t = session.beginTransaction();
        try {
            LocalDate sdate=LocalDate.parse(sd);
            LocalDate edate=LocalDate.parse(ed);

            java.util.List<DB.Leave> tlist = session.createQuery("from Leave s where s.teacher.id=:id and s.sdate>=:id1 and s.sdate<=:id2").setParameter("id2",edate).setParameter("id1",sdate).setParameter("id", tid).list();
            List list = new ArrayList();
            int ml=0;
            int cl=0;
            int el=0;
            int wpl=0;
            for (Iterator iterator = tlist.iterator(); iterator.hasNext(); ) {
                DB.Leave s = (DB.Leave) iterator.next();
                LocalDate sdd=s.getSdate();
                LocalDate edd=s.getEdate();
                int ttl;
                if(edd==null)
                    ttl=1;
                else
                {
                    Period intervalPeriod = Period.between(sdd, edd);
                    ttl=intervalPeriod.getDays();
                }

                if(s.getLtype().equals("Medical leave"))
                    ml+=ttl;
                else if(s.getLtype().equals("Casual leave"))
                    cl+=ttl;
                else if(s.getLtype().equals("Earn Leave"))
                    el+=ttl;
                else if(s.getLtype().equals("Without Pay"))
                    wpl+=ttl;
                else
                    continue;
            }
            list.add(ml);
            list.add(cl);
            list.add(wpl);
            list.add(el);
            list.add(ml+el+wpl+cl);
            t.commit();
            session.close();
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
    @Path("viewTeacherWiseLimit")
    public List viewTeacherWiseLimit(@FormParam("param1")int tid,@FormParam("param2")String sd,@FormParam("param3")String ed)
    {
        Session session = DB.Global.getSession();
        Transaction t = session.beginTransaction();
        try {
            LocalDate sdate=LocalDate.parse(sd);
            LocalDate edate=LocalDate.parse(ed);
            java.util.List<DB.Leave> tlist = session.createQuery("from Leave s where s.teacher.id=:id and s.sdate>=:id1 and s.sdate<=:id2 order by s.adate desc ,s.atime desc ").setParameter("id2",edate).setParameter("id1",sdate).setParameter("id", tid).setMaxResults(25).list();
            List list = new ArrayList();
            for (Iterator iterator = tlist.iterator(); iterator.hasNext(); ) {
                DB.Leave s = (DB.Leave) iterator.next();
                List list1 = new ArrayList();
                list1.add(s.getAdate());
                list1.add(s.getLtype());
                list1.add(s.getSdate());
                list1.add(s.getEdate());
                list1.add(s.getReason());
                list1.add(s.getFlag());
                list1.add(s.getId());
                list.add(list1);
            }
            t.commit();
            session.close();
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
    @Path("viewAll")
    public List viewAll()
    {
        Session session = DB.Global.getSession();
        Transaction t = session.beginTransaction();
        try {

            java.util.List<DB.Leave> tlist = session.createQuery("from Leave s where s.flag=:id order by s.adate desc ,s.atime desc ").setParameter("id",0).list();
            List list = new ArrayList();
            for (Iterator iterator = tlist.iterator(); iterator.hasNext(); ) {
                DB.Leave s = (DB.Leave) iterator.next();
                List list1 = new ArrayList();
                list1.add(s.getAdate());
                list1.add(s.getLtype());
                list1.add(s.getSdate());
                list1.add(s.getEdate());
                list1.add(s.getReason());
                list1.add(s.getFlag());
                list1.add(s.getId());
                list1.add(s.getTeacher().getId());
                list1.add(s.getTeacher().getName());
                list.add(list1);
            }
            t.commit();
            session.close();
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
    @Path("getSubTname")
    public String getSubTname(@FormParam("param1") int slotno, @FormParam("param2") int day, @FormParam("param3") String subjid, @FormParam("param4") String dname, @FormParam("param5") int clid, @FormParam("param6") int leave_id)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        try
        {
            DB.Sleave sleave= (Sleave) session.createQuery("from Sleave s where s.subjectTimetable.slotno=:id and s.subjectTimetable.day=:id1 and s.subjectTimetable.subject.id=:id2 and s.subjectTimetable.division.name=:id3 and s.subjectTimetable.division.csClass.id=:id4 and s.leave.id=:id5").setParameter("id",slotno).setParameter("id1",day).setParameter("id2",subjid).setParameter("id3",dname).setParameter("id4",clid).setParameter("id5",leave_id).uniqueResult();
            String tname=sleave.getTeacher().getName();
            t.commit();
            session.close();
            return tname;
        }
        catch (Exception e)
        {
            session.close();
            return "";
        }
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewTeacherDayWise")
    public List viewTeacherDayWise(@FormParam("param1")int day, @FormParam("param2") int tid, @FormParam("param3")String dt,@FormParam("param4")int leave_id)
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
                DB.Sleave sleave= (Sleave) session.createQuery("from Sleave s where s.subjectTimetable.slotno=:id and s.subjectTimetable.day=:id1 and s.subjectTimetable.subject.id=:id2 and s.subjectTimetable.division.name=:id3 and s.subjectTimetable.division.csClass.id=:id4 and s.leave.id=:id5").setParameter("id",st.getSlotno()).setParameter("id1",day).setParameter("id2",st.getSubject().getId()).setParameter("id3",st.getDivision().getName()).setParameter("id4",st.getDivision().getCsClass().getId()).setParameter("id5",leave_id).uniqueResult();
                try {
                    t.setLeave_tname("" + sleave.getTeacher().getName());
                }
                catch (NullPointerException e)
                {
                    t.setLeave_tname("");
                }
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

                DB.Lleave lleave= (Lleave) session.createQuery("from Lleave s where s.labTimetable.day=:id and s.teacher.id=:id1 and s.labTimetable.labInstructor.labBatch.name=:id2 and s.labTimetable.labInstructor.labBatch.csClass.id=:id3 and s.leave.id=:id4").setParameter("id",st.getDay()).setParameter("id1",st.getLabInstructor().getTeacher().getId()).setParameter("id2",st.getLabInstructor().getLabBatch().getName()).setParameter("id3",st.getLabInstructor().getLabBatch().getCsClass().getId()).setParameter("id4",leave_id).uniqueResult();
                try {
                    t.setLeave_tname("" + lleave.getTeacher().getName());
                }
                catch (NullPointerException e)
                {
                    t.setLeave_tname("");
                }
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
//            return "Done";
        }
        catch (Exception e)
        {
            session.close();
            return null;
        }
    }
}
