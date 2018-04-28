package DB;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Anil on 26/04/2018
 */
public class Lleave implements Serializable{
    private LabTimetable labTimetable;
    private Leave leave;
    private Teacher teacher;
    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public LabTimetable getLabTimetable() {
        return labTimetable;
    }

    public void setLabTimetable(LabTimetable labTimetable) {
        this.labTimetable = labTimetable;
    }

    public Leave getLeave() {
        return leave;
    }

    public void setLeave(Leave leave) {
        this.leave = leave;
    }
}
