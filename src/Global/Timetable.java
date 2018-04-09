package Global;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Anil on 04/04/2018
 */
public class Timetable {
    private int slotno, day, clid, flag, flag1, subtname1, labtname1;
    private String sname, lname, dname, clname, subjid, subtname, labtname, extname;
    private LocalTime stime, etime;

    public int getSlotno() {
        return slotno;
    }

    public void setSlotno(int slotno) {
        this.slotno = slotno;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getClid() {
        return clid;
    }

    public void setClid(int clid) {
        this.clid = clid;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getClname() {
        return clname;
    }

    public void setClname(String clname) {
        this.clname = clname;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public LocalTime getStime() {
        return stime;
    }

    public void setStime(LocalTime stime) {
        this.stime = stime;
    }

    public LocalTime getEtime() {
        return etime;
    }

    public void setEtime(LocalTime etime) {
        this.etime = etime;
    }

    public String getSubjid() {
        return subjid;
    }

    public void setSubjid(String subjid) {
        this.subjid = subjid;
    }

    public int getFlag1() {
        return flag1;
    }

    public void setFlag1(int flag1) {
        this.flag1 = flag1;
    }

    public String getSubtname() {
        return subtname;
    }

    public void setSubtname(String subtname) {
        this.subtname = subtname;
    }

    public String getLabtname() {
        return labtname;
    }

    public void setLabtname(String labtname) {
        this.labtname = labtname;
    }

    public int getSubtname1() {
        return subtname1;
    }

    public void setSubtname1(int subtname1) {
        this.subtname1 = subtname1;
    }

    public int getLabtname1() {
        return labtname1;
    }

    public void setLabtname1(int labtname1) {
        this.labtname1 = labtname1;
    }

    public String getExtname() {
        return extname;
    }

    public void setExtname(String extname) {
        this.extname = extname;
    }
}
class Sortbytime implements Comparator<Timetable>
{
    public SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    public int compare(Timetable a, Timetable b)
    {
        try {
            Date d1 = sdf.parse(String.valueOf(a.getStime()));
            Date d2 = sdf.parse(String.valueOf(b.getStime()));
            return (int) (d1.getTime()-d2.getTime());
        }
        catch (Exception e)
        {
            return 0;
        }
    }
}