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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anil on 11/04/2018
 */
@Path("/subject_report_services")
@WebService
public class Subject_Report {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewTeachSubDivWise")
    public List viewTeachSubDivWise(@FormParam("param1") int tid, @FormParam("param2") String sid, @FormParam("param3")String dname, @FormParam("param4")String sdate, @FormParam("param5")String edate)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Long total=null;
            Subject subject=session.load(Subject.class,sid);
            LocalDate sd=LocalDate.parse(sdate);
            LocalDate ed=LocalDate.parse(edate);
            int clid=subject.getCSClass().getId();
            Division division= (Division) session.createQuery("from Division s where s.name=:id and s.csClass.id=:id1").setParameter("id",dname).setParameter("id1",clid).uniqueResult();
            List<Student> slist=session.createQuery("from Student s where s.division.name=:id and s.division.csClass.id=:id1 order by roll asc ").setParameter("id",dname).setParameter("id1",clid).list();
            List list=new ArrayList();
            for(Iterator iterator = slist.iterator(); iterator.hasNext();) {
                Student s= (Student) iterator.next();
                Query query=session.createQuery("select count(*) from SubjectAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.subject.id=:id3 and s.teacher.id=:id4 and s.flag=:id5 and s.date>=:id6 and s.date<=:id7 ").setParameter("id7",ed).setParameter("id6",sd).setParameter("id5",1).setParameter("id4",tid).setParameter("id3",sid).setParameter("id2",clid).setParameter("id1",dname).setParameter("id",s.getRoll());
                Long present = (Long)query.uniqueResult();
                Query query1=session.createQuery("select count(*) from SubjectAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.subject.id=:id3 and s.teacher.id=:id4 and s.flag=:id5 and s.date>=:id6 and s.date<=:id7 ").setParameter("id7",ed).setParameter("id6",sd).setParameter("id5",0).setParameter("id4",tid).setParameter("id3",sid).setParameter("id2",clid).setParameter("id1",dname).setParameter("id",s.getRoll());
                Long absent = (Long)query1.uniqueResult();
                if(present<=0 && absent <=0)
                    return null;
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
    @Produces(MediaType.TEXT_PLAIN)
    @Path("viewTeachSubDivWiseDetails")
    public String viewTeachSubDivWiseDetails(@FormParam("param1") int tid, @FormParam("param2") String sid, @FormParam("param3")String dname,@FormParam("param4")String sdate,@FormParam("param5")String edate)
    {

        //and s.date>=:id6 and s.date<=:id7").setParameter("id7",ed).setParameter("id6",sd)

        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Long total=null;
            String msg="";
            int ttl=0;
            LocalDate sd=LocalDate.parse(sdate);
            LocalDate ed=LocalDate.parse(edate);
            Subject subject=session.load(Subject.class,sid);
            int clid=subject.getCSClass().getId();
            Division division= (Division) session.createQuery("from Division s where s.name=:id and s.csClass.id=:id1").setParameter("id",dname).setParameter("id1",clid).uniqueResult();
            List<Student> slist=session.createQuery("from Student s where s.division.name=:id and s.division.csClass.id=:id1 order by roll asc ").setParameter("id",dname).setParameter("id1",clid).list();
            for(Iterator iterator = slist.iterator(); iterator.hasNext();) {
                Student s= (Student) iterator.next();
                Query query=session.createQuery("select count(*) from SubjectAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.subject.id=:id3 and s.teacher.id=:id4 and s.flag=:id5 and s.date>=:id6 and s.date<=:id7").setParameter("id7",ed).setParameter("id6",sd).setParameter("id5",1).setParameter("id4",tid).setParameter("id3",sid).setParameter("id2",clid).setParameter("id1",dname).setParameter("id",s.getRoll());
                Long present = (Long)query.uniqueResult();
                Query query1=session.createQuery("select count(*) from SubjectAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.subject.id=:id3 and s.teacher.id=:id4 and s.flag=:id5 and s.date>=:id6 and s.date<=:id7").setParameter("id7",ed).setParameter("id6",sd).setParameter("id5",0).setParameter("id4",tid).setParameter("id3",sid).setParameter("id2",clid).setParameter("id1",dname).setParameter("id",s.getRoll());
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
                msg = "Subject Attendance Report<br>Course: " + division.getCsClass().getCourse().getName() + ", Class: " + division.getCsClass().getName() + "" +
                        ", Division: " + division.getName() + "<br>Subject Name: " + subject.getName() + "<br>Total Lecture: " + total;
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
    @Path("viewTeachSubDivWiseDetails1")
    public String viewTeachSubDivWiseDetails1(@FormParam("param1") int tid, @FormParam("param2") String sid, @FormParam("param3")String dname,@FormParam("param4")String sdate,@FormParam("param5")String edate)
    {

        //and s.date>=:id6 and s.date<=:id7").setParameter("id7",ed).setParameter("id6",sd)

        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Long total=null;
            String msg="";
            int ttl=0;
            LocalDate sd=LocalDate.parse(sdate);
            LocalDate ed=LocalDate.parse(edate);
            Subject subject=session.load(Subject.class,sid);
            int clid=subject.getCSClass().getId();
            Division division= (Division) session.createQuery("from Division s where s.name=:id and s.csClass.id=:id1").setParameter("id",dname).setParameter("id1",clid).uniqueResult();
            List<Student> slist=session.createQuery("from Student s where s.division.name=:id and s.division.csClass.id=:id1 order by roll asc ").setParameter("id",dname).setParameter("id1",clid).list();
            for(Iterator iterator = slist.iterator(); iterator.hasNext();) {
                Student s= (Student) iterator.next();
                Query query=session.createQuery("select count(*) from SubjectAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.subject.id=:id3 and s.teacher.id=:id4 and s.flag=:id5 and s.date>=:id6 and s.date<=:id7").setParameter("id7",ed).setParameter("id6",sd).setParameter("id5",1).setParameter("id4",tid).setParameter("id3",sid).setParameter("id2",clid).setParameter("id1",dname).setParameter("id",s.getRoll());
                Long present = (Long)query.uniqueResult();
                Query query1=session.createQuery("select count(*) from SubjectAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.subject.id=:id3 and s.teacher.id=:id4 and s.flag=:id5 and s.date>=:id6 and s.date<=:id7").setParameter("id7",ed).setParameter("id6",sd).setParameter("id5",0).setParameter("id4",tid).setParameter("id3",sid).setParameter("id2",clid).setParameter("id1",dname).setParameter("id",s.getRoll());
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
                msg = "Subject Attendance Report<br>Teacher Name: "+subject.getTeacher().getName()+"<br>Course: " + division.getCsClass().getCourse().getName() + ", Class: " + division.getCsClass().getName() + "" +
                        ", Division: " + division.getName() + "<br>Subject Name: " + subject.getName() + "<br>Total Lecture: " + total;
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
    @Path("viewTeachAllSubWise")
    public List viewTeachAllSubWise(@FormParam("param1") int tid, @FormParam("param2")String sdate,@FormParam("param3")String edate)
    {
        Session session = Global.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            List list=new ArrayList();
            LocalDate sd=LocalDate.parse(sdate);
            LocalDate ed=LocalDate.parse(edate);
            List<Subject> sublist=session.createQuery("from Subject s where s.teacher.id=:id").setParameter("id",tid).list();
            for(Iterator iterator = sublist.iterator(); iterator.hasNext();)
            {
                Subject subject= (Subject) iterator.next();
                List<Division> divlist=session.createQuery("from Division s where s.csClass.id=:id").setParameter("id",subject.getCSClass().getId()).list();
                for(Iterator iterator1 = divlist.iterator(); iterator1.hasNext();)
                {
                    Division division= (Division) iterator1.next();
                    List list1=new ArrayList();
                    list1.add(subject.getName());
                    list1.add(subject.getCSClass().getName());
                    list1.add(division.getName());
                    Long total=null,extra=null;
                    List<Student> slist=session.createQuery("from Student s where s.division.name=:id and s.division.csClass.id=:id1 order by roll asc ").setParameter("id",division.getName()).setParameter("id1",division.getCsClass().getId()).list();
                    for(Iterator iterator2 = slist.iterator(); iterator2.hasNext();)
                    {
                        Student s= (Student) iterator2.next();
                        Query query=session.createQuery("select count(*) from SubjectAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.subject.id=:id3 and s.teacher.id=:id4 and s.date>=:id5 and s.date<=:id6").setParameter("id6",ed).setParameter("id5",sd).setParameter("id4",tid).setParameter("id3",subject.getId()).setParameter("id2",subject.getCSClass().getId()).setParameter("id1",division.getName()).setParameter("id",s.getRoll());
                        total = (Long)query.uniqueResult();
                        Query query1=session.createQuery("select count(*) from SubjectAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.subject.id=:id3 and s.teacher.id=:id4 and s.subjectTimetable.subject.id!=:id5 and s.date>=:id6 and s.date<=:id7").setParameter("id7",ed).setParameter("id6",sd).setParameter("id5",subject.getId()).setParameter("id4",tid).setParameter("id3",subject.getId()).setParameter("id2",subject.getCSClass().getId()).setParameter("id1",division.getName()).setParameter("id",s.getRoll());
                        extra = (Long)query1.uniqueResult();
                        break;
                    }
                    Query query=session.createQuery("select count(*) from Student s where s.division.name=:id and s.division.csClass.id=:id1").setParameter("id1",division.getCsClass().getId()).setParameter("id",division.getName());
                    Long totalstud = (Long)query.uniqueResult();
                    if(total==0)
                    {
                        list1.add(0);
                        list1.add(0);
                        list1.add(totalstud);
                        list1.add(0);
                        list1.add(0);
                        list1.add(0);
                    }
                    else {
                        list1.add(total);
                        list1.add(extra);
                        list1.add(totalstud);
                        int d = (int) (total * 0.75);
                        Query query4 = session.createQuery("select count(*) from SubjectAttendance s where s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.subject.id=:id3 and s.teacher.id=:id4 and s.flag=:id and s.date>=:id5 and s.date<=:id6 group by s.student.roll").setParameter("id6",ed).setParameter("id5",sd).setParameter("id", 1).setParameter("id4", tid).setParameter("id3", subject.getId()).setParameter("id2", subject.getCSClass().getId()).setParameter("id1", division.getName());
                        ArrayList list2 = (ArrayList) query4.getResultList();
                        int fl = 0;
                        for(Object i:list2)
                        {
                            int j=Integer.parseInt(String.valueOf(i));
                            if(j<d)
                                fl++;
                        }
                        Query query3=session.createQuery("select count(*) from SubjectAttendance s where s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.subject.id=:id3 and s.teacher.id=:id4 and s.flag=:id and s.date>=:id5 and s.date<=:id6").setParameter("id6",ed).setParameter("id5",sd).setParameter("id",1).setParameter("id4",tid).setParameter("id3",subject.getId()).setParameter("id2",subject.getCSClass().getId()).setParameter("id1",division.getName());
                        Long p = (Long)query3.uniqueResult();
                        int avg= (int) (p/total);
                        double sum=(double) (avg*100)/totalstud;
                        String sum1 = String.format(("%.2f"), sum);
                        list1.add(fl);
                        list1.add(avg);
                        list1.add(sum1);
                    }
                    list.add(list1);
                }
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
}
