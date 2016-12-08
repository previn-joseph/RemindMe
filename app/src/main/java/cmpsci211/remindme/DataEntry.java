package cmpsci211.remindme;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Collin on 11/13/2016.
 */

public class DataEntry {
    private String name;
    private String description;
    private Calendar dateCreated;
    private Calendar dateToRemind;
    private boolean isAM;

    public DataEntry(String name, String description, Calendar dateCreated, Calendar dateToRemind, boolean isAM) {
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dateToRemind = dateToRemind;
        this.isAM = isAM;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAM(){
        return isAM;
    }

    public void setAM(boolean b){
        isAM = b;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Calendar getDateToRemind() {
        return dateToRemind;
    }

    public void setDateToRemind(Calendar dateToRemind) {
        this.dateToRemind = dateToRemind;
    }

    @Override
    public String toString() {

        SimpleDateFormat s = new SimpleDateFormat("MM/dd/yyyy hh:mm ");
        String formatCreated = s.format(dateCreated.getTime());
        String formatRemind = s.format(dateToRemind.getTime());

        /*return "Event{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateCreated=" + formatCreated +
                ", dateToRemind=" + formatRemind +
                '}';*/

        return name + "\n" + description + "\n" + formatRemind + ((isAM) ? "AM" : "PM");
    }
}
