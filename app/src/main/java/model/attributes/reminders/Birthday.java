package model.attributes.reminders;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Birthday extends Reminder {

    public Birthday(LocalDate birthDay) {
        super(birthDay);
    }
    
   
    public int calculateRemainingDays(){
        LocalDate today = LocalDate.now();
        LocalDate dateWithCurrentYear = date.withYear(today.getYear());
        if (date.isBefore(today)) {
            dateWithCurrentYear = dateWithCurrentYear.plusYears(1);
        }  
        int daysUntilBirthday = (int) ChronoUnit.DAYS.between(
                today, 
                dateWithCurrentYear);
        return daysUntilBirthday;
    }


    
}
