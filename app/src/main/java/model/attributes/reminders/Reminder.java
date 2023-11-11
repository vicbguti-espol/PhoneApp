package model.attributes.reminders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
   public static void calculateRemainingDays(String fechaNacimiento) throws ParseException{
      SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaNac = formatoFecha.parse(fechaNacimiento);
        Date fechaActual = new Date();
        fechaNac.setYear(fechaActual.getYear());
        if (fechaNac.before(fechaActual)) {
            fechaNac.setYear(fechaActual.getYear() + 1);
        }
        long diferenciaMillis = fechaNac.getTime() - fechaActual.getTime();
        int diasHastaCumpleaños = (int) (diferenciaMillis / (24 * 60 * 60 * 1000));

        System.out.println("Faltan "+ diasHastaCumpleaños+" dias para su cumpelaños");
    }
   
}
