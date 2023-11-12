package model.attributes.reminders;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class GenericReminder extends Reminder {
    private String description;
    
    public GenericReminder(LocalDate date, String description){
        super(date);
        this.description = description;
    }

    @Override
    int calculateRemainingDays() {
        LocalDate today = LocalDate.now();
        int remainingDays = (int) ChronoUnit.DAYS.between(
                today, 
                date);
        return remainingDays;
    }
    
    
}
