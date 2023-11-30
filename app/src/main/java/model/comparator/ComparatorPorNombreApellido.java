/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.comparator;

import java.util.Comparator;
import model.attributes.Attribute;
import model.attributes.names.PersonName;

/**
 *
 * @author naomi
 */
public class ComparatorPorNombreApellido implements Comparator<Attribute>{
   @Override
    public int compare(Attribute attr1, Attribute attr2) {
        if (attr1 instanceof PersonName && attr2 instanceof PersonName) {
            PersonName person1 = (PersonName) attr1;
            PersonName person2 = (PersonName) attr2;

            int resultadoNombre = person1.getLastName().compareTo(person2.getLastName());

            if (resultadoNombre == 0) {
                return person1.getFirstName().compareTo(person2.getFirstName());
            }

            return resultadoNombre;
        }

        // Manejar otros tipos de atributos aquí si es necesario
        return 0; // Cambia esto según tus necesidades
    }
}
