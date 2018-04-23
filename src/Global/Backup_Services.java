package Global;

import DB.Backup;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.jws.WebService;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Anil on 21/03/2018
 */
@Path("/backup_services")
@WebService
public class Backup_Services {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://localhost:5432/";
    static final String USER = "postgres";
    static final String PASS = "phd";

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("viewAll")
    public List viewAll()
    {
        Session session = DB.Global.getSession1();
        Transaction t = session.beginTransaction();
        try {

            java.util.List<Backup> blist = session.createQuery("from Backup s order by s.id desc ").list();
            List list = new ArrayList();
            for (Iterator iterator = blist.iterator(); iterator.hasNext(); ) {
                Backup backup = (Backup) iterator.next();
                List list1 = new ArrayList();
                list1.add(backup.getBname());
                list1.add(backup.getDate());
                list1.add(backup.getCur());
                list1.add(backup.getId());
                list.add(list1);
            }
            t.commit();
            session.close();
            return list;
        }
        catch (Exception e)
        {
//            t.commit();
            session.close();
            return null;
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("backup")
    public String backup()
    {
        Session session= DB.Global.getSession1();
        Transaction transaction = session.beginTransaction();
        try
        {
            Backup backup = (Backup) session.createQuery("from Backup s where s.cur=:id").setParameter("id",1).uniqueResult();
            backup.setDate(LocalDate.now());
            session.persist(backup);
            transaction.commit();
            session.close();
            return "1";
        }
        catch (Exception e){
        //    transaction.commit();
            session.close();
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("restore")
    public String restore(@FormParam("param1")int bid)
    {
        Session session= DB.Global.getSession1();
        Transaction transaction = session.beginTransaction();
        Backup backup=session.load(Backup.class,bid);
        String dbname=backup.getDname();
        transaction.commit();
        session.close();
        BufferedWriter out = null;
        try
        {
//            FileWriter fstream = new FileWriter("/opt/Feedback_System_war_exploded/WEB-INF/classes/hibernate.cfg.xml", false); //true tells to append data.
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
            DB.Global.closeFactory();
            DB.Global.reload();
            return "1";
        }
        catch (IOException e)
        {
            return "E";
        }
    }
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("refresh")
    public String refresh(@FormParam("param1")String bname)
    {
        Connection conn = null;
        Statement stmt = null;
        String  result = bname.replaceAll("[^\\w]","_");
        String dbname="attendance_"+result;
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "CREATE DATABASE "+dbname;
            stmt.executeUpdate(sql);
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
                return String.valueOf(se);
            }
        }

        //////////////////////////////////////refresh
        BufferedWriter out = null;
        try
        {

            FileWriter fstream = new FileWriter("/opt/attendance_war_exploded/WEB-INF/classes/hibernate.cfg.xml", false); //true tells to append data.
//            FileWriter fstream = new FileWriter("F:\\IdeaProjects\\REST\\attendance\\out\\artifacts\\attendance_war_exploded\\WEB-INF\\classes\\hibernate.cfg.xml", false);
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
            DB.Global.closeFactory();
            DB.Global.reload();
        }
        catch (IOException e)
        {
            System.err.println("Error: " + e.getMessage());
        }
        /////////////////////////////////////

        String oldbackup="";
        try
        {
            Session session= DB.Global.getSession1();
            Transaction transaction = session.beginTransaction();
            Backup backup1 = (Backup) session.createQuery("from Backup s where s.cur=:id").setParameter("id",1).uniqueResult();
            oldbackup=backup1.getDname();
            backup1.setCur(0);
            session.persist(backup1);
            Backup backup =new Backup();
            backup.setBname(bname);
            backup.setDname(dbname);
            backup.setCur(1);
            backup.setDate(LocalDate.now());
            session.persist(backup);
            transaction.commit();
            session.close();
//            return "1";
        }
        catch (Exception e){

        }
        ////////////////////pg_backup teacher, course, class, division table for windows/////////////////////////

        try
        {
            //////////////////for centos
            Process p;
            ProcessBuilder pb;
            pb = new ProcessBuilder(
                    "pg_dump",
                    "-a",
                    "-t",
                    "course",
                    "-t",
                    "csclass",
                    "-t",
                    "division",
                    "-t",
                    "teacher",
                    "-U",
                    "postgres",
                    "-d",
                    ""+oldbackup+"",
                    "-f",
                    "qz.sql");
            pb.redirectErrorStream(true);
            p = pb.start();
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String ll;
            while ((ll = br.readLine()) != null) {
                System.out.println(ll);
            }

            ////////////////////////////////// centos end

/// Note:- Remove -d option in centos

            ///////////////////////////////////For win 10//////////////////////////
//            Process p;
//            ProcessBuilder pb;
//            pb = new ProcessBuilder(
//                    "C:\\Program Files (x86)\\PostgreSQL\\9.3\\bin\\pg_dump.exe",
//                    "-a",
//                    "-t",
//                    "course",
//                    "-t",
//                    "csclass",
//                    "-t",
//                    "division",
//                    "-t",
//                    "teacher",
//                    "-U",
//                    "postgres",
//                    "-d",
//                    ""+oldbackup+"",
//                    "-f",
//                    "attendance.sql");
//            pb.redirectErrorStream(true);
//            p = pb.start();
//            InputStream is = p.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);
//            BufferedReader br = new BufferedReader(isr);
//            String ll;
//            while ((ll = br.readLine()) != null) {
//                System.out.println(ll);
//            }
        }
        catch (IOException e)
        {
            System.out.print(e);
            e.printStackTrace();
        }
        //////////////////////////////////end for win10////////////////////////
        ///////////////////////////////////////////////////////////////////////////////
////////////////pg_restore for ubuntu/////////////////

        try
        {
            ////////////for centos start
            Process p;
            ProcessBuilder pb;
            pb = new ProcessBuilder(
                    "psql",
                    "-U",
                    "postgres",
                    "-d",
                    ""+dbname+"",
                    "-1",
                    "-f",
                    "qz.sql");
            pb.redirectErrorStream(true);
            p = pb.start();
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String ll;
            while ((ll = br.readLine()) != null) {
                System.out.println(ll);
            }
        //////////////////for centos end
//        ////////////////for win 10 start
//            Runtime r = Runtime.getRuntime();
//            Process p;
//            ProcessBuilder pb;
//            r = Runtime.getRuntime();
//            pb = new ProcessBuilder(
//                    "C:\\Program Files (x86)\\PostgreSQL\\9.3\\bin\\psql.exe",
//                    "-U",
//                    "postgres",
//                    "-d",
//                    ""+dbname+"",
//                    "-l",
//                    "-f",
//                    "attendance.sql");
//            pb.redirectErrorStream(true);
//            p = pb.start();
//            InputStream is = p.getInputStream();
//            InputStreamReader isr = new InputStreamReader(is);
//            BufferedReader br = new BufferedReader(isr);
//            String ll;
//            while ((ll = br.readLine()) != null) {
//                System.out.println(ll);
//            }
//            ///////////////for win 10 end
        }
        catch (Exception e)
        {
            System.out.print(e);
        }

        //////////////////////////////////////////////////////
        return "1";
    }
}
