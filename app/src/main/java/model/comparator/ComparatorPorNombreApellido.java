/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.comparator;

import collections.CustomList;
import java.util.Comparator;
import model.attributes.Attribute;
import model.attributes.names.PersonName;
import model.contacts.Contact;

/**
 *
 * @author naomi
 */
public class ComparatorPorNombreApellido implements Comparator<Contact>{
   @Override
    public int compare(Contact o1, Contact o2) {
        CustomList c1= (CustomList) o1.findByAttribute(new PersonName("",""));
        CustomList c2= (CustomList)o2.findByAttribute(new PersonName("",""));
         PersonName p1= (PersonName)c1.getFirst();
         PersonName p2= (PersonName)c1.getFirst();
         int resultadoNombre = p1.getLastName().compareTo(p2.getLastName());

            if (resultadoNombre == 0) {
                return p1.getFirstName().compareTo(p2.getFirstName());
            }

            return resultadoNombre;
       }
}
