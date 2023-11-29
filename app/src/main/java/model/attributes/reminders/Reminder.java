package model.attributes.reminders;

import java.time.LocalDate;
import model.attributes.Attribute;

public abstract class Reminder extends Attribute {
    protected LocalDate date;
    
    public Reminder() {}
    
    public Reminder(LocalDate date) {
        this.date = date;
    }

    public Reminder(LocalDate date, String descripcion) {
        this.date = date;
    }
    
    abstract int calculateRemainingDays();

    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date){
        this.date = date;
    }
}
