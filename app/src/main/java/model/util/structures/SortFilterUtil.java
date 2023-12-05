package model.util.structures;

import collections.CustomLinkedList;
import collections.CustomList;
import java.util.List;
import model.attributes.names.PersonName;
import model.contacts.Contact;
import model.user.MobilePhone;

public class SortFilterUtil {
    private static CustomList<Contact> contactList = MobilePhone.getContactList();
    
    public static CustomList<Contact> filterByContactType(String contactType) {
        CustomList<Contact> contactList = MobilePhone.getContactList();
        CustomList<Contact> filteredContacts = new CustomLinkedList<>();

        for (Contact c : contactList) {
            if (String.valueOf(contactType.charAt(0)).equals(String.valueOf(c.getUID().charAt(0)))) {
                filteredContacts.add(c);
            }
        }
        return filteredContacts;
    }
    
    public static CustomList<Contact> filtrarPorTipoContacto(String tipo,CustomList<Contact> listaContactos) {
        
        CustomList<Contact> contactosFiltrados = new CustomLinkedList<>();

        for (Contact contacto : listaContactos) {
            if (String.valueOf(tipo.charAt(0)).equals(String.valueOf(contacto.getUID().charAt(0)))) {
                contactosFiltrados.add(contacto);
            }
        }

        if (contactosFiltrados.isEmpty()) {
            System.out.println("No se encontraron contactos del tipo especificado.");
        }
        return contactosFiltrados;
    }
     public static CustomList<Contact> filtrarPorCAtributos(int numero) {
        CustomList<Contact> listaContactos = MobilePhone.getContactList();
        CustomList<Contact> contactosFiltrados = new CustomLinkedList<>();

        if (numero < 0) {
            System.out.println("El número de atributos debe ser no negativo.");
            return contactosFiltrados;
        }

        for (Contact contacto : listaContactos) {
            if (contacto.attributes.size() == numero) {
                contactosFiltrados.add(contacto);
            }
        }

        if (contactosFiltrados.isEmpty()) {
            System.out.println("No se encontraron contactos con la cantidad de atributos especificada.");
        }

        return contactosFiltrados;
    }
    
     public static CustomList<Contact> filterByPersonName(String firstName, String lastName, List<Contact> personList) {
        CustomList<Contact> filteredContacts = new CustomLinkedList<>();

        for (Contact c : personList) {
            PersonName personName= 
                    (PersonName) c.findByAttribute(new PersonName("","")).getFirst();
            if (personName.getFirstName().equalsIgnoreCase(firstName)
                    && personName.getLastName().equalsIgnoreCase(lastName)){
                filteredContacts.add(c);
            }
        }
        return filteredContacts;
     }
}
