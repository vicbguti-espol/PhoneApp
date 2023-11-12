package model.attributes.reminders;

import java.time.LocalDate;
import model.attributes.Attribute;

public abstract class Reminder extends Attribute {
    protected LocalDate date;

    public Reminder(LocalDate date) {
        this.date = date;
    }

    public Reminder(LocalDate date, String descripcion) {
        this.date = date;
    }
    
    abstract int calculateRemainingDays();
}
