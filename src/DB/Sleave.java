package DB;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Anil on 26/04/2018
 */
public class Sleave implements Serializable{
    private SubjectTimetable subjectTimetable;
    private Teacher teacher;
    private LocalDate date;
    private Leave leave;

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

    public SubjectTimetable getSubjectTimetable() {
        return subjectTimetable;
    }

    public void setSubjectTimetable(SubjectTimetable subjectTimetable) {
        this.subjectTimetable = subjectTimetable;
    }

    public Leave getLeave() {
        return leave;
    }

    public void setLeave(Leave leave) {
        this.leave = leave;
    }
}
