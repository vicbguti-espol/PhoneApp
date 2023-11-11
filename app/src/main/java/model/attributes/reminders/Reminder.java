package model.attributes.reminders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import model.attributes.Attribute;

public class Reminder extends Attribute {
   LocalDate date;
   private String descripcion;

    public Reminder(LocalDate date) {
        this.date = date;
    }

    public Reminder(LocalDate date, String descripcion) {
        this.date = date;
        this.descripcion = descripcion;
    }
   
    public static int calculateRemainingDays(LocalDate date) throws ParseException{
      LocalDate fechaNacimiento = date;
      LocalDate fechaActual = LocalDate.now();
      fechaNacimiento = fechaNacimiento.withYear(fechaActual.getYear());
      if (fechaNacimiento.isBefore(fechaActual)) {
            fechaNacimiento = fechaNacimiento.plusYears(1);
        }  
        long diasHastaCumpleaños = ChronoUnit.DAYS.between(fechaActual, fechaNacimiento);
        return (int) diasHastaCumpleaños;
    }

}
