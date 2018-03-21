package DB;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Anil on 19/03/2018
 */
public class LabAttendance implements Serializable {
    private LabTimetable labTimetable;
    private Student student;
    private int flag;
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

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LabTimetable getLabTimetable() {
        return labTimetable;
    }

    public void setLabTimetable(LabTimetable labTimetable) {
        this.labTimetable = labTimetable;
    }
}
