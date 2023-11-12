package model.attributes.reminders;

import java.time.LocalDate;
import model.attributes.Attribute;

public class Reminder extends Attribute {
    private static final long serialVersionUID = 3888474865472388773L;
   LocalDate date;

    public Reminder(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reminder{" + "date=" + date + '}';
    }
    
    
}
