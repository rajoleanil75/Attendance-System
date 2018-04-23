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
 * Created by Anil on 18/04/2018
 */
@Path("/student_report_services")
@WebService
public class Student_Report {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewStudentSubWise")
    public List viewStudentSubWise(@FormParam("param1")int roll, @FormParam("param2") int cid, @FormParam("param3")String dname, @FormParam("param4")String sdate,@FormParam("param5")String edate)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        try
        {
            LocalDate sd=LocalDate.parse(sdate);
            LocalDate ed=LocalDate.parse(edate);
            Student student= (Student) session.createQuery("from Student s where s.roll=:id and s.division.name=:id1 and s.division.csClass.id=:id2").setParameter("id",roll).setParameter("id1",dname).setParameter("id2",cid).uniqueResult();
            if(student==null)
            {
                return null;
            }
            List<Subject> slist=session.createQuery("from Subject s where s.CSClass.id=:id").setParameter("id",cid).list();

//            List<LabBatch> llist=session.createQuery("from LabBatch s where s.csClass.id=:id and s.from<=:id1 and s.to>=:id2").setParameter("id2",roll).setParameter("id1",roll).setParameter("id",cid).list();
//
//            List<LabInstructor> llist1=session.createQuery("from LabInstructor s where s.labBatch.csClass.id=:id and s.labBatch.from<=:id1 and s.labBatch.to>=:id2").setParameter("id2",roll).setParameter("id1",roll).setParameter("id",cid).list();

            List list2=new ArrayList();
            for(Iterator iterator = slist.iterator(); iterator.hasNext();)
            {
                Subject s= (Subject) iterator.next();
                List list1=new ArrayList();
                list1.add(s.getId());
                list1.add(s.getName());

                Query query=session.createQuery("select count(*) from SubjectAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.subject.id=:id3 and s.date>=:id4 and s.date<=:id5").setParameter("id3",s.getId()).setParameter("id5",ed).setParameter("id4",sd).setParameter("id2",cid).setParameter("id1",dname).setParameter("id",roll);
                Long ttl = (Long)query.uniqueResult();
                Query query1=session.createQuery("select count(*) from SubjectAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.subject.id=:id3 and s.date>=:id4 and s.date<=:id5 and s.flag=:id6").setParameter("id3",s.getId()).setParameter("id6",1).setParameter("id5",ed).setParameter("id4",sd).setParameter("id2",cid).setParameter("id1",dname).setParameter("id",roll);
                Long ttl1 = (Long)query1.uniqueResult();
                double avg;
                if(ttl!=0) {
                    int avg1 = (int) (ttl1 * 100);
                    int avg2 = (int) (ttl * 1);
                    avg=(double) avg1/avg2;
                }
                else
                    avg= 0;
                String sum1 = String.format(("%.2f"), avg);
                list1.add(ttl);
                list1.add(ttl1);
                list1.add(sum1);
                list1.add(s.getTeacher().getName());
                list2.add(list1);
            }
            return list2;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewStudentLabWise")
    public List viewStudentLabWise(@FormParam("param1")int roll, @FormParam("param2") int cid, @FormParam("param3")String dname, @FormParam("param4")String sdate,@FormParam("param5")String edate)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        try
        {
            LocalDate sd=LocalDate.parse(sdate);
            LocalDate ed=LocalDate.parse(edate);
            Student student= (Student) session.createQuery("from Student s where s.roll=:id and s.division.name=:id1 and s.division.csClass.id=:id2").setParameter("id",roll).setParameter("id1",dname).setParameter("id2",cid).uniqueResult();
            if(student==null)
            {
                return null;
            }
//            List<Subject> slist=session.createQuery("from Subject s where s.CSClass.id=:id").setParameter("id",cid).list();

            List<LabBatch> llist=session.createQuery("from LabBatch s where s.csClass.id=:id and s.from<=:id1 and s.to>=:id2").setParameter("id2",roll).setParameter("id1",roll).setParameter("id",cid).list();

            List<LabInstructor> llist1=session.createQuery("from LabInstructor s where s.labBatch.csClass.id=:id and s.labBatch.from<=:id1 and s.labBatch.to>=:id2").setParameter("id2",roll).setParameter("id1",roll).setParameter("id",cid).list();

            List list2=new ArrayList();
            for(Iterator iterator = llist1.iterator(); iterator.hasNext();)
            {
                LabInstructor s= (LabInstructor) iterator.next();
                List list1=new ArrayList();
                list1.add(s.getLabBatch().getName());
                list1.add(s.getTeacher().getName());

                Query query= session.createQuery("select count (*) from LabAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.labTimetable.labInstructor.teacher.id=:id3 and s.labTimetable.labInstructor.labBatch.name=:id4 and s.labTimetable.labInstructor.labBatch.csClass.id=:id5 and s.date>=:id6 and s.date<=:id7").setParameter("id",roll).setParameter("id1",dname).setParameter("id2",cid).setParameter("id3",s.getTeacher().getId()).setParameter("id4",s.getLabBatch().getName()).setParameter("id5",cid).setParameter("id6",sd).setParameter("id7",ed);
                Long ttl = (Long)query.uniqueResult();
                Query query1= session.createQuery("select count (*) from LabAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.labTimetable.labInstructor.teacher.id=:id3 and s.labTimetable.labInstructor.labBatch.name=:id4 and s.labTimetable.labInstructor.labBatch.csClass.id=:id5 and s.date>=:id6 and s.date<=:id7 and s.flag=:id8").setParameter("id8",1).setParameter("id",roll).setParameter("id1",dname).setParameter("id2",cid).setParameter("id3",s.getTeacher().getId()).setParameter("id4",s.getLabBatch().getName()).setParameter("id5",cid).setParameter("id6",sd).setParameter("id7",ed);
                Long ttl1 = (Long)query1.uniqueResult();

//                Query query=session.createQuery("select count(*) from SubjectAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.subject.id=:id3 and s.date>=:id4 and s.date<=:id5").setParameter("id3",s.getId()).setParameter("id5",ed).setParameter("id4",sd).setParameter("id2",cid).setParameter("id1",dname).setParameter("id",roll);
//                Long ttl = (Long)query.uniqueResult();
//                Query query1=session.createQuery("select count(*) from SubjectAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.subject.id=:id3 and s.date>=:id4 and s.date<=:id5 and s.flag=:id6").setParameter("id3",s.getId()).setParameter("id6",1).setParameter("id5",ed).setParameter("id4",sd).setParameter("id2",cid).setParameter("id1",dname).setParameter("id",roll);
//                Long ttl1 = (Long)query1.uniqueResult();
                double avg;
                if(ttl!=0) {
                    int avg1 = (int) (ttl1 * 100);
                    int avg2 = (int) (ttl * 1);
                    avg=(double) avg1/avg2;
                }
                else
                    avg= 0;
                String sum1 = String.format(("%.2f"), avg);
                list1.add(ttl);
                list1.add(ttl1);
                list1.add(sum1);

                list2.add(list1);
            }
            return list2;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("viewStudentSubLabLbl")
    public String viewStudentSubLabLbl(@FormParam("param1")int roll, @FormParam("param2") int cid, @FormParam("param3")String dname, @FormParam("param4")String sdate,@FormParam("param5")String edate)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        String msg="";
        try
        {
            LocalDate sd=LocalDate.parse(sdate);
            LocalDate ed=LocalDate.parse(edate);
            Student s= (Student) session.createQuery("from Student s where s.roll=:id and s.division.name=:id1 and s.division.csClass.id=:id2").setParameter("id",roll).setParameter("id1",dname).setParameter("id2",cid).uniqueResult();
            if(s==null)
            {
                return "";
            }
            msg="Name: "+s.getName()+"<br>Course: "+s.getDivision().getCsClass().getCourse().getName()+"<br>Class: "+s.getDivision().getCsClass().getName()+", Division: "+s.getDivision().getName()+"<br>Roll No.: "+s.getRoll()+"<br>Start Date: "+sd+", End Date: "+ed+"";
            return msg;
        }
        catch (Exception e)
        {
            return "";
        }
    }
}