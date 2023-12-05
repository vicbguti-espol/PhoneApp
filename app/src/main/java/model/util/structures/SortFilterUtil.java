package model.util.structures;

import collections.CustomLinkedList;
import collections.CustomList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.attributes.Attribute;
import model.attributes.names.CompanyName;
import model.attributes.names.PersonName;
import model.util.comparator.ComparatorPorCantidadAtributos;
import model.util.comparator.ComparatorPorCompania;
import model.util.comparator.ComparatorPorNombreApellido;
import model.contacts.Contact;
import model.user.MobilePhone;

public class SortFilterUtil {
    
    
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
     
     public static CustomList<Contact> sortByAttributesListSize(CustomList<Contact> listaContactos) {
        // Verifica si la lista de contactos es nula
        if (listaContactos == null) {
            return listaContactos; // o puedes devolver null si es más apropiado
        }

        // Copia la lista para no modificar la original
        CustomList<Contact> copiaContactos = new CustomLinkedList<>(listaContactos);

        // Utiliza el comparador para ordenar la lista
        Collections.sort(copiaContactos, new ComparatorPorCantidadAtributos());

        return copiaContactos;
    }
     
    public static CustomList<Contact> sortByPersonName(CustomList<Contact> contacts) {
        CustomList<Contact> contactListCopy = new CustomLinkedList<>(contacts);
        Collections.sort(contactListCopy, new ComparatorPorNombreApellido());
        return contactListCopy;
     }
     
     
    public static List<Attribute> ordenarPorCompania() {    
    CustomList<Attribute> atributo =new CustomLinkedList<>();
    CustomList<Contact> pn= filterByContactType("Compania");
    if (pn== null || pn.isEmpty()) {
        System.out.println("La lista de contactos de personas es nula o vacía.");
        return new ArrayList<>(); 
    }
  for (Contact contacto : pn) {
            CompanyName name = new CompanyName("");
            CustomList<Attribute> at=contacto.findByAttribute(name);
            atributo.addAll(at);
        }
    CustomList<Attribute> copiaContactos = new CustomLinkedList<>(atributo);
    Collections.sort(copiaContactos, new ComparatorPorCompania());
    return copiaContactos;
  }
}
