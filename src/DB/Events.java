package DB;

import java.time.LocalDate;

/**
 * Created by Anil on 10/04/2018
 */
public class Events {
    private String name;
    private LocalDate sdate;
    private LocalDate edate;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
