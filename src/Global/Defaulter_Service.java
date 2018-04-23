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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anil on 13/04/2018
 */
@Path("/defaulter_services")
@WebService
public class Defaulter_Service {
    @POST
    @Path("viewTeachWise")
    @Produces(MediaType.APPLICATION_JSON)
    public List getClassWise(@FormParam("param1") int tid)
    {
        List list=new ArrayList();
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        try
        {
            List<Subject> slist=session.createQuery("from Subject s where s.teacher.id=:id").setParameter("id",tid).list();
            for(Iterator iterator = slist.iterator(); iterator.hasNext();)
            {
                Subject subject= (Subject) iterator.next();
                int clid=subject.getCSClass().getId();
                List<Division> dlist=session.createQuery("from Division s where s.csClass.id=:id").setParameter("id",clid).list();
                for (Iterator iterator1=dlist.iterator();iterator1.hasNext();)
                {
                    Long total=null;

                    Division division= (Division) iterator1.next();
                    List<Student> stlist=session.createQuery("from Student s where s.division.name=:id and s.division.csClass.id=:id1").setParameter("id1",division.getCsClass().getId()).setParameter("id",division.getName()).list();
                    for (Iterator iterator2=stlist.iterator();iterator2.hasNext();)
                    {
                        Student s= (Student) iterator2.next();
                        Query query=session.createQuery("select count(*) from SubjectAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.subject.id=:id3 and s.teacher.id=:id4").setParameter("id4",tid).setParameter("id3",subject.getId()).setParameter("id2",subject.getCSClass().getId()).setParameter("id1",division.getName()).setParameter("id",s.getRoll());
                        total = (Long)query.uniqueResult();
                        break;
                    }
                    int d = (int) (total * 0.75);
                    for (Iterator iterator3=stlist.iterator();iterator3.hasNext();)
                    {
                        Student stud= (Student) iterator3.next();
                        Query query=session.createQuery("select count(*) from SubjectAttendance s where s.student.roll=:id and s.student.division.name=:id1 and s.student.division.csClass.id=:id2 and s.subject.id=:id3 and s.teacher.id=:id4 and s.flag=:id5").setParameter("id5",1).setParameter("id4",tid).setParameter("id3",subject.getId()).setParameter("id2",subject.getCSClass().getId()).setParameter("id1",division.getName()).setParameter("id",stud.getRoll());
                        Long present = (Long)query.uniqueResult();
                        if(d>present) {
                            List list1=new ArrayList();
                            list1.add(stud.getRoll());
                            list1.add(stud.getName());
                            list1.add(subject.getName());
                            list1.add(subject.getCSClass().getName()+" ("+division.getName()+")");
                            list1.add(total);
                            list1.add(present);
                            String temp1=total+"";
                            String temp2=present+"";
                            double sum=(double) (Double.parseDouble(temp2)/Double.parseDouble(temp1))*100;
                            String sum1 = String.format(("%.2f"), sum);
                            list1.add(sum1);
                            list.add(list1);
                        }
                    }
                }
            }

            List<LabInstructor> llist=session.createQuery("from LabInstructor s where s.teacher.id=:id").setParameter("id",tid).list();

            t.commit();
            session.close();
            return list;
//            return "1";
        }
        catch (Exception e)
        {
            session.close();
            return null;
        }
    }
}
