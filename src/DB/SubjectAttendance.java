package DB;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by Anil on 19/03/2018
 */
public class SubjectAttendance implements Serializable {
    private SubjectTimetable subjectTimetable;
    private Student student;
    private int flag;
    private Teacher teacher;
    private LocalDate date;
    private Subject subject;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

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

    public SubjectTimetable getSubjectTimetable() {
        return subjectTimetable;
    }

    public void setSubjectTimetable(SubjectTimetable subjectTimetable) {
        this.subjectTimetable = subjectTimetable;
    }
}
