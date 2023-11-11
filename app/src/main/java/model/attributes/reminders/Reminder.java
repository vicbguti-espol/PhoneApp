package model.attributes.reminders;

import java.time.LocalDate;
import model.attributes.Attribute;

public class Reminder extends Attribute {
   LocalDate date;

    public Reminder(LocalDate date) {
        this.date = date;
    }
   
   
}
