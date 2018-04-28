package DB;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Anil on 27/04/2018
 */
public class Leave {
    private int id;
    private Teacher teacher;
    private LocalDate sdate;
    private LocalDate edate;
    private LocalDate adate;
    private LocalTime atime;
    private String ltype;
    private String reason;
    private int flag;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLtype() {
        return ltype;
    }

    public void setLtype(String ltype) {
        this.ltype = ltype;
    }

    public LocalDate getEdate() {
        return edate;
    }

    public void setEdate(LocalDate edate) {
        this.edate = edate;
    }

    public LocalDate getSdate() {
        return sdate;
    }

    public void setSdate(LocalDate sdate) {
        this.sdate = sdate;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getAdate() {
        return adate;
    }

    public void setAdate(LocalDate adate) {
        this.adate = adate;
    }

    public LocalTime getAtime() {
        return atime;
    }

    public void setAtime(LocalTime atime) {
        this.atime = atime;
    }
}
