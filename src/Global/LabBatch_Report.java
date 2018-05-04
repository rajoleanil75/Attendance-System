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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anil on 12/04/2018
 */
@Path("/labbatch_report_services")
@WebService
public class LabBatch_Report {
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("viewTeachLabDivWiseDetails")
    public String viewTeachSubDivWiseDetails(@FormParam("param1") int tid, @FormParam("param2") String lid, @FormParam("param3")int clid,@FormParam("param4")String sdate,@FormParam("param5")String edate)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Long total=null;
            String msg="";
            int ttl=0;
            LocalDate sd=LocalDate.parse(sdate);
            LocalDate ed=LocalDate.parse(edate);
            LabInstructor l= (LabInstructor) session.createQuery("from LabInstructor s where s.teacher.id=:id and s.labBatch.name=:id1 and s.labBatch.csClass.id=:id2").setParameter("id2",clid).setParameter("id1",lid).setParameter("id",tid).uniqueResult();
            String clname=l.getLabBatch().getCsClass().getName();
            String lname=l.getLabBatch().getName();
            String cname=l.getLabBatch().getCsClass().getCourse().getName();

            List<Student> slist=session.createQuery("from Student s where s.division.csClass.id=:id1 and s.roll>=:id2 and s.roll<=:id3 order by roll asc ").setParameter("id3",l.getLabBatch().getTo()).setParameter("id2",l.getLabBatch().getFrom()).setParameter("id1",clid).list();
            for(Iterator iterator = slist.iterator(); iterator.hasNext();) {
                Student s= (Student) iterator.next();
                Query query=session.createQuery("select count(*) from LabAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.teacher.id=:id4 and s.flag=:id5 and s.labTimetable.labInstructor.labBatch.name=:id6 and s.labTimetable.labInstructor.labBatch.csClass.id=:id7 and s.date>=:id8 and s.date<=:id9").setParameter("id9",ed).setParameter("id8",sd).setParameter("id7",clid).setParameter("id6",lid).setParameter("id5",1).setParameter("id4",tid).setParameter("id2",clid).setParameter("id1",s.getDivision().getName()).setParameter("id",s.getRoll());
                Long present = (Long)query.uniqueResult();
                Query query1=session.createQuery("select count(*) from LabAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.teacher.id=:id4 and s.flag=:id5 and s.labTimetable.labInstructor.labBatch.name=:id6 and s.labTimetable.labInstructor.labBatch.csClass.id=:id7 and s.date>=:id8 and s.date<=:id9").setParameter("id9",ed).setParameter("id8",sd).setParameter("id7",clid).setParameter("id6",lid).setParameter("id5",0).setParameter("id4",tid).setParameter("id2",clid).setParameter("id1",s.getDivision().getName()).setParameter("id",s.getRoll());
                Long absent = (Long)query1.uniqueResult();
                if(present<=0 && absent <=0) {
                    ttl = 1;
                    break;
                }
                total=present+absent;
                break;
            }
            if(ttl==1)
            {
                return "-1";
            }
            else {
                msg = "Lab Batch Attendance Report<br>Teacher Name :Course: " + cname + ", Class: " + clname + "" +
                        "<br>Lab Batch Name: " + lname + "<br>Total Practical: " + total;
            }
            transaction.commit();
            session.close();
            return msg;
        }
        catch (Exception e)
        {
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("viewTeachLabDivWiseDetails1")
    public String viewTeachSubDivWiseDetails1(@FormParam("param1") int tid, @FormParam("param2") String lid, @FormParam("param3")int clid,@FormParam("param4")String sdate,@FormParam("param5")String edate)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Long total=null;
            String msg="";
            int ttl=0;
            LocalDate sd=LocalDate.parse(sdate);
            LocalDate ed=LocalDate.parse(edate);
            LabInstructor l= (LabInstructor) session.createQuery("from LabInstructor s where s.teacher.id=:id and s.labBatch.name=:id1 and s.labBatch.csClass.id=:id2").setParameter("id2",clid).setParameter("id1",lid).setParameter("id",tid).uniqueResult();
            String clname=l.getLabBatch().getCsClass().getName();
            String lname=l.getLabBatch().getName();
            String cname=l.getLabBatch().getCsClass().getCourse().getName();

            List<Student> slist=session.createQuery("from Student s where s.division.csClass.id=:id1 and s.roll>=:id2 and s.roll<=:id3 order by roll asc ").setParameter("id3",l.getLabBatch().getTo()).setParameter("id2",l.getLabBatch().getFrom()).setParameter("id1",clid).list();
            for(Iterator iterator = slist.iterator(); iterator.hasNext();) {
                Student s= (Student) iterator.next();
                Query query=session.createQuery("select count(*) from LabAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.teacher.id=:id4 and s.flag=:id5 and s.labTimetable.labInstructor.labBatch.name=:id6 and s.labTimetable.labInstructor.labBatch.csClass.id=:id7 and s.date>=:id8 and s.date<=:id9").setParameter("id9",ed).setParameter("id8",sd).setParameter("id7",clid).setParameter("id6",lid).setParameter("id5",1).setParameter("id4",tid).setParameter("id2",clid).setParameter("id1",s.getDivision().getName()).setParameter("id",s.getRoll());
                Long present = (Long)query.uniqueResult();
                Query query1=session.createQuery("select count(*) from LabAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.teacher.id=:id4 and s.flag=:id5 and s.labTimetable.labInstructor.labBatch.name=:id6 and s.labTimetable.labInstructor.labBatch.csClass.id=:id7 and s.date>=:id8 and s.date<=:id9").setParameter("id9",ed).setParameter("id8",sd).setParameter("id7",clid).setParameter("id6",lid).setParameter("id5",0).setParameter("id4",tid).setParameter("id2",clid).setParameter("id1",s.getDivision().getName()).setParameter("id",s.getRoll());
                Long absent = (Long)query1.uniqueResult();
                if(present<=0 && absent <=0) {
                    ttl = 1;
                    break;
                }
                total=present+absent;
                break;
            }
            if(ttl==1)
            {
                return "-1";
            }
            else {
                msg = "Lab Batch Attendance Report<br>Teacher Name :"+l.getTeacher().getName()+"<br>Course: " + cname + ", Class: " + clname + "" +
                        "<br>Lab Batch Name: " + lname + "<br>Total Practical: " + total;
            }
            transaction.commit();
            session.close();
            return msg;
        }
        catch (Exception e)
        {
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewTeachLabDivWise")
    public List viewTeachSubDivWise(@FormParam("param1") int tid, @FormParam("param2") String lid, @FormParam("param3")int clid,@FormParam("param4")String sdate,@FormParam("param5")String edate)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Long total=null;
            String msg="";
            int ttl=0;
            LocalDate sd=LocalDate.parse(sdate);
            LocalDate ed=LocalDate.parse(edate);
            LabInstructor l= (LabInstructor) session.createQuery("from LabInstructor s where s.teacher.id=:id and s.labBatch.name=:id1 and s.labBatch.csClass.id=:id2").setParameter("id2",clid).setParameter("id1",lid).setParameter("id",tid).uniqueResult();
            String clname=l.getLabBatch().getCsClass().getName();
            String lname=l.getLabBatch().getName();
            String cname=l.getLabBatch().getCsClass().getCourse().getName();
            List list=new ArrayList();
            List<Student> slist=session.createQuery("from Student s where s.division.csClass.id=:id1 and s.roll>=:id2 and s.roll<=:id3 order by roll asc ").setParameter("id3",l.getLabBatch().getTo()).setParameter("id2",l.getLabBatch().getFrom()).setParameter("id1",clid).list();
            for(Iterator iterator = slist.iterator(); iterator.hasNext();) {
                Student s= (Student) iterator.next();
                Query query=session.createQuery("select count(*) from LabAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.teacher.id=:id4 and s.flag=:id5 and s.labTimetable.labInstructor.labBatch.name=:id6 and s.labTimetable.labInstructor.labBatch.csClass.id=:id7 and s.date>=:id8 and s.date<=:id9").setParameter("id9",ed).setParameter("id8",sd).setParameter("id7",clid).setParameter("id6",lid).setParameter("id5",1).setParameter("id4",tid).setParameter("id2",clid).setParameter("id1",s.getDivision().getName()).setParameter("id",s.getRoll());
                Long present = (Long)query.uniqueResult();
                Query query1=session.createQuery("select count(*) from LabAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.teacher.id=:id4 and s.flag=:id5 and s.labTimetable.labInstructor.labBatch.name=:id6 and s.labTimetable.labInstructor.labBatch.csClass.id=:id7 and s.date>=:id8 and s.date<=:id9").setParameter("id9",ed).setParameter("id8",sd).setParameter("id7",clid).setParameter("id6",lid).setParameter("id5",0).setParameter("id4",tid).setParameter("id2",clid).setParameter("id1",s.getDivision().getName()).setParameter("id",s.getRoll());
                Long absent = (Long)query1.uniqueResult();
                if(present<=0 && absent <=0) {
                    return null;
                }
                List list1=new ArrayList();
                list1.add(s.getRoll());
                list1.add(s.getName());
                list1.add(present);
                total=present+absent;
                double sum=(double) present/total;
                sum*=100;
                String sum1 = String.format(("%.2f"), sum);
                list1.add(sum1);
                list.add(list1);

            }
            transaction.commit();
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
    @Path("viewTeachAllLabWise")
    public List viewTeachAllLabWise(@FormParam("param1") int tid, @FormParam("param2")String sdate,@FormParam("param3")String edate)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            List list=new ArrayList();
            LocalDate sd=LocalDate.parse(sdate);
            LocalDate ed=LocalDate.parse(edate);
            List<LabInstructor> l=session.createQuery("from LabInstructor s where s.teacher.id=:id").setParameter("id",tid).list();
            for(Iterator iterator = l.iterator(); iterator.hasNext();) {
                Long totalpract = null;
                List list1 = new ArrayList();
                LabInstructor li = (LabInstructor) iterator.next();
                List<Student> slist = session.createQuery("from Student s where s.division.csClass.id=:id and s.roll>=:id1 and s.roll<=:id2").setParameter("id2", li.getLabBatch().getTo()).setParameter("id1", li.getLabBatch().getFrom()).setParameter("id", li.getLabBatch().getCsClass().getId()).list();
                for (Iterator iterator1 = slist.iterator(); iterator1.hasNext(); ) {
                    Student s = (Student) iterator1.next();
                    Query query = session.createQuery("select count(*) from LabAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.teacher.id=:id4 and s.labTimetable.labInstructor.labBatch.name=:id6 and s.labTimetable.labInstructor.labBatch.csClass.id=:id7 ").setParameter("id7", s.getDivision().getCsClass().getId()).setParameter("id6", li.getLabBatch().getName()).setParameter("id4", tid).setParameter("id2", s.getDivision().getCsClass().getId()).setParameter("id1", s.getDivision().getName()).setParameter("id", s.getRoll());
                    totalpract = (Long) query.uniqueResult();
                    if (totalpract <= 0) {
//                        return null;
                    }
                    break;
                }
                Query query = session.createQuery("select count(*) from Student s where s.division.csClass.id=:id and s.roll>=:id1 and s.roll<=:id2").setParameter("id2", li.getLabBatch().getTo()).setParameter("id1", li.getLabBatch().getFrom()).setParameter("id", li.getLabBatch().getCsClass().getId());
                Long totalstud = (Long) query.uniqueResult();
                list1.add(li.getLabBatch().getName());
                list1.add(li.getLabBatch().getCsClass().getName());
                if (totalpract == 0) {
                    list1.add(0);
                    list1.add(totalstud);
                    list1.add(0);
                    list1.add(0);
                    list1.add(0);
                } else
                    {
                        list1.add(totalpract);
                        list1.add(totalstud);
                        int d = (int) (totalpract * 0.75);
                        Query query1=session.createQuery("select count(*) from LabAttendance s where s.teacher.id=:id and s.labTimetable.labInstructor.labBatch.name=:id1 and s.labTimetable.labInstructor.labBatch.csClass.id=:id2 and s.flag=:id3 group by s.student.roll").setParameter("id3",1).setParameter("id2",li.getLabBatch().getCsClass().getId()).setParameter("id1",li.getLabBatch().getName()).setParameter("id",tid);
                        ArrayList list2 = (ArrayList) query1.getResultList();
                        int fl = 0;
                        for(Object i:list2)
                        {
                            int j=Integer.parseInt(String.valueOf(i));
                            if(j<d)
                                fl++;
                        }
                        Query query2=session.createQuery("select count(*) from LabAttendance s where s.teacher.id=:id and s.labTimetable.labInstructor.labBatch.name=:id1 and s.labTimetable.labInstructor.labBatch.csClass.id=:id2 and s.flag=:id3 ").setParameter("id3",1).setParameter("id2",li.getLabBatch().getCsClass().getId()).setParameter("id1",li.getLabBatch().getName()).setParameter("id",tid);
                        Long p = (Long)query2.uniqueResult();
                        int avg= (int) (p/totalpract);
                        double sum=(double) (avg*100)/totalstud;
                        String sum1 = String.format(("%.2f"), sum);
                        list1.add(fl);
                        list1.add(avg);
                        list1.add(sum1);
                }
                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
//            return "1";
        }
        catch (Exception e)
        {
            session.close();
            return null;
//            return String.valueOf(e);
        }
    }
}
