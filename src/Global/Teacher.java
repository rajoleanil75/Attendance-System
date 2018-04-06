package Global;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.management.Query;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anil on 25/03/2018
 */
@Path("/teacher_services")
@WebService
public class Teacher {

    static final String JDBC_DRIVER = "org.postgresql.Driver";
//    static final String DB_URL = "jdbc:postgresql://localhost:5432/";
    static final String USER = "postgres";
    static final String PASS = "phd";

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("add")
    public String add( @FormParam("param1")String tname,@FormParam("param2")String tsname, @FormParam("param3")String special)
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        try
        {
            DB.Teacher teacher=new DB.Teacher();
            teacher.setName(tname);
            teacher.setSf(tsname);
            teacher.setSpecialsub(special);
            session.save(teacher);
            t.commit();
            session.close();
            return "1";
        }
        catch (Exception e)
        {
//            t.commit();
            session.close();
            return "E";
        }
    }
    @POST
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll()
    {
        Session session= DB.Global.getSession();
        Transaction t=session.beginTransaction();
        java.util.List<DB.Teacher> tlist=session.createQuery("from DB.Teacher order by id asc ").list();
        t.commit();
        session.close();
        return tlist;
    }
    @POST
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public List  search(@FormParam("param1") String tname)
    {
        Session session = DB.Global.getSession();
        Transaction transaction=session.beginTransaction();
        try {

            java.util.List<DB.Teacher> tlist=session.createQuery("from DB.Teacher s where s.name like :id order by s.id asc ").setParameter("id","%"+tname+"%").list();
            List list=new ArrayList();
            for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
            {
                DB.Teacher teacher= (DB.Teacher) iterator.next();
                List list1=new ArrayList();
                list1.add(teacher.getId());
                list1.add(teacher.getName());
                list1.add(teacher.getSf());
                list1.add(teacher.getSpecialsub());
                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
//            transaction.commit();
            session.close();
            return null;
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("delete")
    public String delete(@FormParam("param1") int tid) {
        Session session = DB.Global.getSession();
        Transaction t = session.beginTransaction();
        DB.Teacher teacher = (DB.Teacher) session.createQuery("from Teacher s where s.id=:id").setParameter("id",tid).uniqueResult();
        if (teacher== null){
            t.commit();
            session.close();
            return "0";
        }
        else {

            List<TNotification> list=session.createQuery("from TNotification s where s.teacher.id=:id").setParameter("id",tid).list();
            for(Iterator iterator = list.iterator(); iterator.hasNext();)
            {
                TNotification obj= (TNotification) iterator.next();
                session.delete(obj);
            }
            List<LabAttendance> list1=session.createQuery("from LabAttendance s where s.teacher.id=:id").setParameter("id",tid).list();
            for(Iterator iterator = list1.iterator(); iterator.hasNext();)
            {
                LabAttendance obj= (LabAttendance) iterator.next();
                session.delete(obj);
            }
            List<LabTimetable> list2=session.createQuery("from LabTimetable s where s.labInstructor.teacher.id=:id").setParameter("id",tid).list();
            for(Iterator iterator = list2.iterator(); iterator.hasNext();)
            {
                LabTimetable obj= (LabTimetable) iterator.next();
                session.delete(obj);
            }
            org.hibernate.query.Query query3=session.createQuery("delete LabInstructor s where s.teacher.id=:id").setParameter("id",tid);
            query3.executeUpdate();
//            List<LabInstructor> list3=session.createQuery("from LabInstructor s where s.teacher.id=:id").setParameter("id",tid).list();
//            for(Iterator iterator = list3.iterator(); iterator.hasNext();)
//            {
//                LabInstructor obj= (LabInstructor) iterator.next();
//                session.delete(obj);
//            }
//            List<SubjectAttendance> list4=session.createQuery("from SubjectAttendance s where s.teacher.id=:id").setParameter("id",tid).list();
//            for(Iterator iterator = list4.iterator(); iterator.hasNext();)
//            {
//                SubjectAttendance obj= (SubjectAttendance) iterator.next();
//                session.delete(obj);
//            }
//            List<Subject> list6=session.createQuery("from Subject s where s.teacher.id=:id").setParameter("id",tid).list();
//            for(Iterator iterator = list6.iterator(); iterator.hasNext();)
//            {
//                Subject obj= (Subject) iterator.next();
//                session.delete(obj);
//            }
            org.hibernate.query.Query query4=session.createQuery("delete SubjectAttendance s where s.teacher.id=:id").setParameter("id",tid);
            query4.executeUpdate();
//            org.hibernate.query.Query query5=session.createQuery("delete SubjectTimetable s where s.subject.teacher.id=:id").setParameter("id",tid);
//            query5.executeUpdate();
            List<SubjectTimetable> list5=session.createQuery("from SubjectTimetable s where s.subject.teacher.id=:id").setParameter("id",tid).list();


            for(Iterator iterator = list5.iterator(); iterator.hasNext();)
            {
                SubjectTimetable obj= (SubjectTimetable) iterator.next();
                session.delete(obj);
            }
            t.commit();
            t.begin();
            org.hibernate.query.Query query=session.createQuery("delete Subject s where s.teacher.id=:id").setParameter("id",tid);
            query.executeUpdate();

//            org.hibernate.query.Query query=session.createQuery("delete from TNotification s where s.teacher.id=:id").setParameter("id",tid);
//            query.executeUpdate();
//            org.hibernate.query.Query query1=session.createQuery("delete from LabAttendance s where s.teacher.id=:id").setParameter("id",tid);
//            query1.executeUpdate();
//            org.hibernate.query.Query query2=session.createQuery("delete from LabTimetable s where s.labInstructor.teacher.id=:id").setParameter("id",tid);
//            query2.executeUpdate();
//            org.hibernate.query.Query query3=session.createQuery("delete from LabInstructor s where s.teacher.id=:id").setParameter("id",tid);
//            query3.executeUpdate();
//            org.hibernate.query.Query query4=session.createQuery("delete from SubjectAttendance s where s.teacher.id=:id").setParameter("id",tid);
//            query4.executeUpdate();
//            org.hibernate.query.Query query5=session.createQuery("delete from SubjectTimetable s where s.subject.teacher.id=:id").setParameter("id",tid);
//            query5.executeUpdate();
//            org.hibernate.query.Query query6=session.createQuery("delete from Subject s where s.teacher.id=:id").setParameter("id",tid);
//            query6.executeUpdate();
//            session.delete(teacher);

            org.hibernate.query.Query query1=session.createQuery("delete Teacher s where s.id=:id").setParameter("id",tid);
            query1.executeUpdate();



            t.commit();
            session.close();
//
//            Session session2=Global.getSession1();
//            Transaction transaction1=session2.beginTransaction();
//            Backup backup= (Backup) session2.createQuery("from Backup s where s.cur=:id").setParameter("id",1).uniqueResult();
//            String dbname=backup.getDname();
//            transaction1.commit();
//            session2.close();
//
//            Connection conn = null;
//            Statement stmt = null;
//            String DB_URL = "jdbc:postgresql://localhost:5432/"+dbname+"";
//            try{
//                Class.forName("org.postgresql.Driver");
//                conn = DriverManager.getConnection(DB_URL, USER, PASS);
//                stmt = conn.createStatement();
//                String sql = "delete from teacher s where s.id="+tid+" ";
//                stmt.executeUpdate(sql);
//            }catch(SQLException se){
//                se.printStackTrace();
//                return String.valueOf(se);
//            }catch(Exception e){
//                e.printStackTrace();
//                return String.valueOf(e);
//            }finally{
//                try{
//                    if(stmt!=null)
//                        stmt.close();
//                }catch(SQLException se2){
//                }
//                try{
//                    if(conn!=null)
//                        conn.close();
//                }catch(SQLException se){
//                    se.printStackTrace();
//                    return String.valueOf(se);
//                }
//            }

            return "1";

        }
//        t.commit();
//        session.close();
//        return "0";
    }
}
