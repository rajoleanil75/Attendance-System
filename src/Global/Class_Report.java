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
 * Created by Anil on 23/04/2018
 */
@Path("/class_report_services")
@WebService
public class Class_Report {
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewClassDivWise")
    public List viewStudentSubWise( @FormParam("param2") int cid, @FormParam("param3")String dname, @FormParam("param4")String sdate, @FormParam("param5")String edate)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        try
        {
            LocalDate sd=LocalDate.parse(sdate);
            LocalDate ed=LocalDate.parse(edate);
            List list=new ArrayList();
            Query query=session.createQuery("select count(*) from Student s where s.division.name=:id and s.division.csClass.id=:id1").setParameter("id1",cid).setParameter("id",dname);
            Long totalstud = (Long)query.uniqueResult();
            Query query5=session.createQuery("select count(*) from Subject s where s.CSClass.id=:id1").setParameter("id1",cid);
            Long totalstud5 = (Long)query5.uniqueResult();
            if(totalstud==0||totalstud5==0)
            {
                return null;
            }
            else {
                List list1=new ArrayList();
                list1.add("Roll No");
                list1.add("Student Name");
                int roll=0;
                List<Student> slist=session.createQuery("from Student s where s.division.name=:id and s.division.csClass.id=:id1").setParameter("id",dname).setParameter("id1",cid).list();
                for(Iterator iterator = slist.iterator(); iterator.hasNext();)
                {
                    Student s= (Student) iterator.next();
                    roll=s.getRoll();
                    break;
                }
                int ttl=0;
                List<Subject> sublist=session.createQuery("from Subject s where s.CSClass.id=:id").setParameter("id",cid).list();
                for(Iterator iterator = sublist.iterator(); iterator.hasNext();)
                {
                    Subject s= (Subject) iterator.next();
                    Query query1=session.createQuery("select count(*) from SubjectAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.subject.id=:id3 and s.date>=:id4 and s.date<=:id5").setParameter("id4",sd).setParameter("id5",ed).setParameter("id3",s.getId()).setParameter("id2",cid).setParameter("id1",dname).setParameter("id",roll);
                    Long totallec = (Long)query1.uniqueResult();
                    list1.add(s.getName()+" ("+totallec+")");
                    ttl=(int) (ttl+totallec);
                }
                list1.add("Attend Lecture ("+ttl+")");
                list1.add("Percentage");
                list.add(list1);
                for(Iterator iterator = slist.iterator(); iterator.hasNext();)
                {
                    Student s= (Student) iterator.next();
                    List list2=new ArrayList();
                    list2.add(s.getRoll());
                    list2.add(s.getName());
                    int roll1=s.getRoll();
                    int ttl1=0;
                    for(Iterator iterator1 = sublist.iterator(); iterator1.hasNext();)
                    {
                        Subject su= (Subject) iterator1.next();
                        Query query1=session.createQuery("select count(*) from SubjectAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.subject.id=:id3 and s.flag=:id4 and s.date>=:id5 and s.date<=:id6").setParameter("id5",sd).setParameter("id6",ed).setParameter("id4",1).setParameter("id3",su.getId()).setParameter("id2",cid).setParameter("id1",dname).setParameter("id",roll1);
                        Long totallec = (Long)query1.uniqueResult();
                        list2.add(totallec);
                        if(totallec==0)
                            continue;
                        ttl1=(int) (ttl1+totallec);
                    }
                    list2.add(ttl1);
                    if(ttl!=0) {
                        double sum = (double) (ttl1 * 100) / ttl;
                        String sum1 = String.format(("%.2f"), sum);
                        list2.add(sum1);
                    }
                    else
                        list2.add(0);

                    list.add(list2);
                }

            }
            return list;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("viewClassLbl")
    public String viewStudentSubLabLbl( @FormParam("param2") int cid, @FormParam("param3")String dname, @FormParam("param4")String sdate,@FormParam("param5")String edate)
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        String msg="";
        try
        {
            LocalDate sd=LocalDate.parse(sdate);
            LocalDate ed=LocalDate.parse(edate);
            Division s= (Division) session.createQuery("from Division s where s.name=:id and s.csClass.id=:id1").setParameter("id",dname).setParameter("id1",cid).uniqueResult();
            Query query=session.createQuery("select count(*) from Student s where s.division.name=:id and s.division.csClass.id=:id1").setParameter("id1",cid).setParameter("id",dname);
            Long totalstud = (Long)query.uniqueResult();
            if(totalstud==0)
            {
                return "";
            }
            msg="Course: "+s.getCsClass().getCourse().getName()+"<br>Class: "+s.getCsClass().getName()+", Division: "+s.getName()+"<br>Start Date: "+sd+", End Date: "+ed+"";
            return msg;
        }
        catch (Exception e)
        {
            return "";
        }
    }
}
