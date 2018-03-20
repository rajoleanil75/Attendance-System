package DB;

import java.time.LocalTime;

/**
 * Created by Anil on 19/03/2018
 */
public class LabTimetable {
    private LabInstructor labInstructor;
    private int day;
    private LocalTime stime;
    private LocalTime etime;

    public LocalTime getEtime() {
        return etime;
    }

    public void setEtime(LocalTime etime) {
        this.etime = etime;
    }

    public LocalTime getStime() {
        return stime;
    }

    public void setStime(LocalTime stime) {
        this.stime = stime;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public LabInstructor getLabInstructor() {
        return labInstructor;
    }

    public void setLabInstructor(LabInstructor labInstructor) {
        this.labInstructor = labInstructor;
    }
}
