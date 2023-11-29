/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.comparator;

import java.util.Comparator;
import model.contacts.Contact;

/**
 *
 * @author naomi
 */
public class ComparatorPorCantidadAtributos implements Comparator<Contact> {
    @Override
    public int compare(Contact contacto1, Contact contacto2) {
        return Integer.compare(contacto1.attributes.size(), contacto2.attributes.size());
    }
}
