package Global;

import DB.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Anil on 07/02/2018
 */
@Path("/course_services")
@WebService
public class Course_service {

    @GET
    @Path("viewAll1")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll1()
    {
        Session session7=Global.getSession1();
        Transaction transaction7=session7.beginTransaction();
        Backup backup= (Backup) session7.createQuery("from Backup s where s.cur=:id").setParameter("id",1).uniqueResult();
        String dbname=backup.getDname();
        transaction7.commit();
        session7.close();
        BufferedWriter out = null;
        try
        {
//            FileWriter fstream = new FileWriter("/opt/Feedback_System_war_exploded/WEB-INF/classes/hibernate.cfg.xml", false); //true tells to append data.
            FileWriter fstream = new FileWriter("F:\\IdeaProjects\\REST\\attendance\\out\\artifacts\\attendance_war_exploded\\WEB-INF\\classes\\hibernate.cfg.xml", false);
            out = new BufferedWriter(fstream);
//                                String dbnme="temp1";
            out.write("<?xml version='1.0' encoding='utf-8'?>\n" +
                    "<!DOCTYPE hibernate-configuration PUBLIC\n" +
                    "        \"-//Hibernate/Hibernate Configuration DTD//EN\"\n" +
                    "        \"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd\">\n" +
                    "<hibernate-configuration>\n" +
                    "    <session-factory>\n" +
                    "        <property name=\"connection.url\">jdbc:postgresql://localhost:5432/"+dbname+"</property>\n" +
                    "        <property name=\"connection.driver_class\">org.postgresql.Driver</property>\n" +
                    "        <property name=\"connection.username\">postgres</property>\n" +
                    "        <property name=\"connection.password\">phd</property>\n" +
                    "        <property name=\"hibernate.dialect\">org.hibernate.dialect.PostgreSQL93Dialect</property>\n" +
                    "        <property name=\"show_sql\">true</property>\n" +
                    "        <property name=\"connection.pool_size\">100000</property>\n" +
                    "        <property name=\"hbm2ddl.auto\">update</property>\n" +
                    "\n" +
                    "        <mapping resource=\"DB/sql.xml\"/>\n" +
                    "    </session-factory>\n" +
                    "</hibernate-configuration>");
            out.close();
          //  Global.reload();
        }
        catch (IOException e)
        {
            System.err.println("Error: " + e.getMessage());
//            return "E";
        }
        Global.closeFactory();
        Global.reload();
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        List<Course> tlist=session.createQuery("from Course ").list();
        t.commit();
        session.close();
        return tlist;
    }
    @POST
    @Path("viewAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List viewAll()
    {
        Session session= Global.getSession();
        Transaction t=session.beginTransaction();
        List<Course> tlist=session.createQuery("from Course ").list();
        t.commit();
        session.close();
        return tlist;
    }

    @POST
    @Path("getCourse")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCourse(@FormParam("param1") int sid)
    {
        Session session1= Global.getSession();
        Transaction t1=session1.beginTransaction();
        Course r= (Course) session1.createQuery("from Course s where s.id=:sid").setParameter("sid",sid).uniqueResult();
        t1.commit();
        session1.close();
        if(r==null)
            return "0";
        return String.valueOf(r.getName());
    }


}
