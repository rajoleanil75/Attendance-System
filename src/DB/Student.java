package DB;

import java.io.Serializable;

/**
 * Created by Anil on 19/03/2018
 */
public class Student implements Serializable {
    private int roll;
    private String name;
    private DB.Division division;

    public int getRoll() {
        return roll;
    }

    public void setRoll(int roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }
}
