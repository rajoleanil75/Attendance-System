package DB;

import java.io.Serializable;

/**
 * Created by Anil on 19/03/2018
 */
public class LabInstructor implements Serializable {
    private LabBatch labBatch;
    private Teacher teacher;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public LabBatch getLabBatch() {
        return labBatch;
    }

    public void setLabBatch(LabBatch labBatch) {
        this.labBatch = labBatch;
    }
}
