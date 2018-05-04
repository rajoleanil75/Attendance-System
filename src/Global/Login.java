package Global;

import DB.Backup;
import DB.Global;
import DB.Teacher;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import DB.User;
import javax.jws.WebService;
import javax.persistence.NoResultException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ANIL on 17/01/2018.
 */
@Path("/login_services")
@WebService
public class Login {

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("admin_login")
    public String admin_login(@FormParam("param1")String uname,@FormParam("param2")String pass,@FormParam("param3")int dbid)
    {
        Session session = Global.getSession1();
        Transaction t = session.beginTransaction();
        try
        {
            User user = (User) session.createQuery("from User l where l.name='" + uname + "' and role=4 and password='"+pass+"'").uniqueResult();
            t.commit();
            session.close();
            if (user == null)
                return "0";
            else
            {
                String message;
                LocalTime lt = user.getTime();
                Instant instant = lt.atDate(user.getDate()).atZone(ZoneId.systemDefault()).toInstant();
                Date time = Date.from(instant);

                JSONObject json = new JSONObject();
                json.put("uname", user.getName());
                json.put("role", user.getRole());
                json.put("llogin", time.toString());
                message = json.toString();
                Session session1 = Global.getSession1();
                Transaction transaction = session1.beginTransaction();
                User user1 = session1.load(User.class, user.getName());
                user1.setDate(LocalDate.now());
                user1.setTime(LocalTime.now());
                session1.persist(user1);
                transaction.commit();
                session1.close();

                Session session2=Global.getSession1();
                Transaction transaction1=session2.beginTransaction();
                Backup backup= (Backup) session2.createQuery("from Backup s where s.id=:id").setParameter("id",dbid).uniqueResult();
                String dbname=backup.getDname();
                transaction1.commit();
                session2.close();
                BufferedWriter out = null;
                try
                {
//                    FileWriter fstream = new FileWriter("/opt/attendance_war_exploded/WEB-INF/classes/hibernate.cfg.xml", false); //true tells to append data.
                    FileWriter fstream = new FileWriter("F:\\IdeaProjects\\REST\\attendance\\out\\artifacts\\attendance_war_exploded\\WEB-INF\\classes\\hibernate.cfg.xml", false);
                    out = new BufferedWriter(fstream);
                    out.write("<?xml version='1.0' encoding='utf-8'?>\n" +
                            "<!DOCTYPE hibernate-configuration PUBLIC\n" +
                            "    \"-//Hibernate/Hibernate Configuration DTD//EN\"\n" +
                            "    \"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd\">\n" +
                            "<hibernate-configuration>\n" +
                            "  <session-factory>\n" +
                            "    <property name=\"connection.url\">jdbc:postgresql://localhost:5432/"+dbname+"</property>\n" +
                            "    <property name=\"connection.driver_class\">org.postgresql.Driver</property>\n" +
                            "    <property name=\"connection.username\">postgres</property>\n" +
                            "    <property name=\"connection.password\">phd</property>\n" +
                            "    <property name=\"hibernate.dialect\">org.hibernate.dialect.PostgreSQL93Dialect</property>\n" +
                            "    <property name=\"show_sql\">true</property>\n" +
                            "    <property name=\"connection.pool_size\">100000</property>\n" +
                            "    <!--<property name=\"hibernate.temp.use_jdbc_metadata_defaults\">false</property>-->\n" +
                            "    <property name=\"hbm2ddl.auto\">update</property>\n" +
                            "\n" +
                            "    <mapping resource=\"DB/sql.xml\"/>\n" +
                            "\n" +
                            "    <!-- DB schema will be updated if needed -->\n" +
                            "    <!-- <property name=\"hbm2ddl.auto\">update</property> -->  </session-factory>\n" +
                            "</hibernate-configuration>");
                    out.close();
                    Global.closeFactory();
                    Global.reload();

                }
                catch (IOException e)
                {
                    return "E";
                }
                return message;
            }
        }
        catch (NoResultException e)
        {
//            t.commit();
            session.close();
            return "0";
        }
        catch (NonUniqueResultException e)
        {
//            t.commit();
            session.close();
            return "0";
        }
        catch (Exception e)
        {
//            t.commit();
            session.close();
            return "0";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("hod_login")
    public String hod_login(@FormParam("param1")String uname,@FormParam("param2")String pass,@FormParam("param3")int dbid)
    {
        Session session = Global.getSession1();
        Transaction t = session.beginTransaction();
        try
        {
            User user = (User) session.createQuery("from User l where l.name='" + uname + "' and role=3 and password='"+pass+"'").uniqueResult();
            t.commit();
            session.close();
            if (user == null)
                return "0";
            else
            {
                String message;
                LocalTime lt = user.getTime();
                Instant instant = lt.atDate(user.getDate()).atZone(ZoneId.systemDefault()).toInstant();
                Date time = Date.from(instant);

                JSONObject json = new JSONObject();
                json.put("uname", user.getName());
                json.put("role", user.getRole());
                json.put("llogin", time.toString());
                message = json.toString();
                Session session1 = Global.getSession1();
                Transaction transaction = session1.beginTransaction();
                User user1 = session1.load(User.class, user.getName());
                user1.setDate(LocalDate.now());
                user1.setTime(LocalTime.now());
                session1.persist(user1);
                transaction.commit();
                session1.close();

                Session session2=Global.getSession1();
                Transaction transaction1=session2.beginTransaction();
                Backup backup= (Backup) session2.createQuery("from Backup s where s.id=:id").setParameter("id",dbid).uniqueResult();
                String dbname=backup.getDname();
                transaction1.commit();
                session2.close();
                BufferedWriter out = null;
                try
                {
//                    FileWriter fstream = new FileWriter("/opt/attendance_war_exploded/WEB-INF/classes/hibernate.cfg.xml", false); //true tells to append data.
                    FileWriter fstream = new FileWriter("F:\\IdeaProjects\\REST\\attendance\\out\\artifacts\\attendance_war_exploded\\WEB-INF\\classes\\hibernate.cfg.xml", false);
                    out = new BufferedWriter(fstream);
                    out.write("<?xml version='1.0' encoding='utf-8'?>\n" +
                            "<!DOCTYPE hibernate-configuration PUBLIC\n" +
                            "    \"-//Hibernate/Hibernate Configuration DTD//EN\"\n" +
                            "    \"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd\">\n" +
                            "<hibernate-configuration>\n" +
                            "  <session-factory>\n" +
                            "    <property name=\"connection.url\">jdbc:postgresql://localhost:5432/"+dbname+"</property>\n" +
                            "    <property name=\"connection.driver_class\">org.postgresql.Driver</property>\n" +
                            "    <property name=\"connection.username\">postgres</property>\n" +
                            "    <property name=\"connection.password\">phd</property>\n" +
                            "    <property name=\"hibernate.dialect\">org.hibernate.dialect.PostgreSQL93Dialect</property>\n" +
                            "    <property name=\"show_sql\">true</property>\n" +
                            "    <property name=\"connection.pool_size\">100000</property>\n" +
                            "    <!--<property name=\"hibernate.temp.use_jdbc_metadata_defaults\">false</property>-->\n" +
                            "    <property name=\"hbm2ddl.auto\">update</property>\n" +
                            "\n" +
                            "    <mapping resource=\"DB/sql.xml\"/>\n" +
                            "\n" +
                            "    <!-- DB schema will be updated if needed -->\n" +
                            "    <!-- <property name=\"hbm2ddl.auto\">update</property> -->  </session-factory>\n" +
                            "</hibernate-configuration>");
                    out.close();
                    Global.closeFactory();
                    Global.reload();

                }
                catch (IOException e)
                {
                    return "E";
                }
                return message;
            }
        }
        catch (NoResultException e)
        {
//            t.commit();
            session.close();
            return "0";
        }
        catch (NonUniqueResultException e)
        {
//            t.commit();
            session.close();
            return "0";
        }
        catch (Exception e)
        {
//            t.commit();
            session.close();
            return "0";
        }
    }
    @POST
    @Path("viewTeacherLogin")
    @Produces(MediaType.APPLICATION_JSON)
    public List  viewTeacherLogin()
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
//            FileWriter fstream = new FileWriter("/opt/attendance_war_exploded/WEB-INF/classes/hibernate.cfg.xml", false); //true tells to append data.
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
                    "        <property name=\"hibernate.dialect\">org.hibernate.dialect.PostgreSQL95Dialect</property>\n" +
                    "        <property name=\"show_sql\">true</property>\n" +
                    "        <property name=\"connection.pool_size\">1000000</property>\n" +
                    "        <property name=\"hbm2ddl.auto\">update</property>\n" +
                    "\n" +
                    "        <mapping resource=\"DB/sql.xml\"/>\n" +
                    "    </session-factory>\n" +
                    "</hibernate-configuration>");
            out.close();
            Global.closeFactory();
            Global.reload();
        }
        catch (IOException e)
        {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
        try {
            Session session = Global.getSession();
            Transaction transaction=session.beginTransaction();
            java.util.List<DB.Teacher> tlist=session.createQuery("from Teacher ").list();
            List list=new ArrayList();
            for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
            {
                DB.Teacher teacher= (DB.Teacher) iterator.next();
                if(teacher.getPass()==null)
                    continue;
                List list1=new ArrayList();
                list1.add(teacher.getId());
                list1.add(teacher.getName());
                list.add(list1);
            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    @POST
    @Path("viewTeacherReg")
    @Produces(MediaType.APPLICATION_JSON)
    public List  viewTeacherReg()
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
//            FileWriter fstream = new FileWriter("/opt/attendance_war_exploded/WEB-INF/classes/hibernate.cfg.xml", false); //true tells to append data.
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
                    "        <property name=\"hibernate.dialect\">org.hibernate.dialect.PostgreSQL95Dialect</property>\n" +
                    "        <property name=\"show_sql\">true</property>\n" +
                    "        <property name=\"connection.pool_size\">1000000</property>\n" +
                    "        <property name=\"hbm2ddl.auto\">update</property>\n" +
                    "\n" +
                    "        <mapping resource=\"DB/sql.xml\"/>\n" +
                    "    </session-factory>\n" +
                    "</hibernate-configuration>");
            out.close();
            Global.closeFactory();
            Global.reload();
        }
        catch (IOException e)
        {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
        try {
            Session session = Global.getSession();
            Transaction transaction=session.beginTransaction();
            java.util.List<DB.Teacher> tlist=session.createQuery("from Teacher ").list();
            List list=new ArrayList();
            for(Iterator iterator = tlist.iterator(); iterator.hasNext();)
            {
                DB.Teacher teacher= (DB.Teacher) iterator.next();
                if(teacher.getPass()==null)
                {
                    List list1=new ArrayList();
                    list1.add(teacher.getId());
                    list1.add(teacher.getName());
                    list.add(list1);
                }

            }
            transaction.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("teacher_login")
    public String teacher_login(@FormParam("param1")int tid,@FormParam("param2")String pass)
    {
        Session session2=Global.getSession1();
        Transaction transaction1=session2.beginTransaction();
        Backup backup= (Backup) session2.createQuery("from Backup s where s.cur=:id").setParameter("id",1).uniqueResult();
        String dbname=backup.getDname();
        transaction1.commit();
        session2.close();
        BufferedWriter out = null;
        try
        {
//                    FileWriter fstream = new FileWriter("/opt/attendance_war_exploded/WEB-INF/classes/hibernate.cfg.xml", false); //true tells to append data.
            FileWriter fstream = new FileWriter("F:\\IdeaProjects\\REST\\attendance\\out\\artifacts\\attendance_war_exploded\\WEB-INF\\classes\\hibernate.cfg.xml", false);
            out = new BufferedWriter(fstream);
            out.write("<?xml version='1.0' encoding='utf-8'?>\n" +
                    "<!DOCTYPE hibernate-configuration PUBLIC\n" +
                    "    \"-//Hibernate/Hibernate Configuration DTD//EN\"\n" +
                    "    \"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd\">\n" +
                    "<hibernate-configuration>\n" +
                    "  <session-factory>\n" +
                    "    <property name=\"connection.url\">jdbc:postgresql://localhost:5432/"+dbname+"</property>\n" +
                    "    <property name=\"connection.driver_class\">org.postgresql.Driver</property>\n" +
                    "    <property name=\"connection.username\">postgres</property>\n" +
                    "    <property name=\"connection.password\">phd</property>\n" +
                    "    <property name=\"hibernate.dialect\">org.hibernate.dialect.PostgreSQL93Dialect</property>\n" +
                    "    <property name=\"show_sql\">true</property>\n" +
                    "    <property name=\"connection.pool_size\">100000</property>\n" +
                    "    <!--<property name=\"hibernate.temp.use_jdbc_metadata_defaults\">false</property>-->\n" +
                    "    <property name=\"hbm2ddl.auto\">update</property>\n" +
                    "\n" +
                    "    <mapping resource=\"DB/sql.xml\"/>\n" +
                    "\n" +
                    "    <!-- DB schema will be updated if needed -->\n" +
                    "    <!-- <property name=\"hbm2ddl.auto\">update</property> -->  </session-factory>\n" +
                    "</hibernate-configuration>");
            out.close();
            Global.closeFactory();
            Global.reload();
        }
        catch (IOException e)
        {
            return "E";
        }
        Session session = DB.Global.getSession();
        Transaction t = session.beginTransaction();
        try {
            DB.Teacher user = (Teacher) session.createQuery("from Teacher s where s.id=:id and s.pass=:id1").setParameter("id", tid).setParameter("id1", pass).uniqueResult();
            if(user==null)
                return "0";
            else {
                String message;
                LocalTime lt = user.getTime();
                Instant instant = lt.atDate(user.getDate()).atZone(ZoneId.systemDefault()).toInstant();
                Date time = Date.from(instant);

                JSONObject json = new JSONObject();
                json.put("uname", user.getName());
                json.put("id", user.getId());
                json.put("llogin", time.toString());
                message = json.toString();
                user.setDate(LocalDate.now());
                user.setTime(LocalTime.now());
                t.commit();
                session.close();
                return message;
            }
        }
        catch (NoResultException e)
        {
//            t.commit();
            session.close();
            return "0";
        }
        catch (NonUniqueResultException e)
        {
//            t.commit();
            session.close();
            return "E";
        }
        catch (Exception e)
        {
//            t.commit();
            session.close();
            return String.valueOf(e);
        }
    }
}