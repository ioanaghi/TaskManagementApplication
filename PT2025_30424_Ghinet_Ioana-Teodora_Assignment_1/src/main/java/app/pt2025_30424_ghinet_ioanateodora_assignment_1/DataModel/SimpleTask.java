package app.pt2025_30424_ghinet_ioanateodora_assignment_1.DataModel;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import app.pt2025_30424_ghinet_ioanateodora_assignment_1.PersistanceLayer.SerializationOperations;
import javafx.beans.property.*;

public non-sealed class SimpleTask extends Task implements Serializable {

    private static final long serialVersionUID = 1L;

    private int startHour;
    private int endHour;

    public SimpleTask(int idTask, String statusTask, int startHour, int endHour) {
        super(idTask, statusTask);
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int estimateDuration() {
        return endHour - startHour;
    }

    @Override
    public String toString() {
        return "SimpleTask{" +
                "startHour=" + startHour +
                ", endHour=" + endHour +
                '}';
    }


}