package DB;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Created by Anil on 19/03/2018
 */
public class SubjectTimetable {
    private int slotno;
    private int day;
    private LocalTime stime;
    private LocalTime etime;
    private Subject subject;
    private Division division;


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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }
}
