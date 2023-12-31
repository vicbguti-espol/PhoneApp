package model.attributes.reminders;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class GenericReminder extends Reminder {
    private String description;
    
    public GenericReminder() {}
    
    public GenericReminder(String description) {
         this.description = description;
    }
    
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
