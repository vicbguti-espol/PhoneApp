package model.attributes.reminders;

import model.attributes.reminders.Reminder;
import java.time.LocalDate;

public class GenericReminder extends Reminder {
    String description;
    
    public GenericReminder(LocalDate date, String description){
        super(date);
        this.description = description;
    }
}
