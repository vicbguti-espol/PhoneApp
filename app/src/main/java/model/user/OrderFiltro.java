/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.user;

import collections.CustomLinkedList;
import collections.CustomList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.attributes.Attribute;
import model.attributes.names.CompanyName;
import model.attributes.names.PersonName;
import model.comparator.ComparatorPorCantidadAtributos;
import model.comparator.ComparatorPorCompania;
import model.comparator.ComparatorPorNombreApellido;
import model.contacts.Contact;
import static model.user.prueba.filtrarPorTipoContacto;

/**
 *
 * @author naomi
 */
public class OrderFiltro {
    
    
    public static List<Contact> filtrarPorTipoContacto(String tipo) {
        List<Contact> listaContactos = MobilePhone.getContactList();
        List<Contact> contactosFiltrados = new ArrayList<>();

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
    public static List<Contact> filtrarPorTipoContacto(String tipo,List<Contact> listaContactos) {
        
        List<Contact> contactosFiltrados = new ArrayList<>();

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
     public static List<Contact> filtrarPorCAtributos(int numero) {
        List<Contact> listaContactos = MobilePhone.getContactList();
        List<Contact> contactosFiltrados = new ArrayList<>();

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
                    (PersonName) c.findByAttribute(new PersonName()).getFirst();
            if (personName.getFirstName().equalsIgnoreCase(firstName)
                    && personName.getLastName().equalsIgnoreCase(lastName)){
                filteredContacts.add(c);
            }
//            if (personName!= null && 
//                personName.getFirstName()!= null && personName.getFirstName().equalsIgnoreCase(firstName) &&
//                personName.getLastName() != null && personName.getLastName().equalsIgnoreCase(lastName)) {
//                filteredContacts.add(c);
//            }
//        }
        }
        return filteredContacts;
     }
     
     public static List<Contact> sortByAttributesListSize(List<Contact> listaContactos) {
        // Verifica si la lista de contactos es nula
        if (listaContactos == null) {
            return listaContactos; // o puedes devolver null si es más apropiado
        }

        // Copia la lista para no modificar la original
        List<Contact> copiaContactos = new ArrayList<>(listaContactos);

        // Utiliza el comparador para ordenar la lista
        Collections.sort(copiaContactos, new ComparatorPorCantidadAtributos());

        return copiaContactos;
    }
     
    public static List<Contact> ordenarPorNombreApellido(List<Contact> pn) {    
    
    //List<Contact> pn= filtrarPorTipoContacto("Persona");
    if (pn== null || pn.isEmpty()) {
        System.out.println("La lista de contactos de personas es nula o vacía.");
        return new ArrayList<>(); 
    }

    List<Contact> copiaContactos = new ArrayList<>(pn);
    Collections.sort(copiaContactos, new ComparatorPorNombreApellido());
    return copiaContactos;
  }
     
     public static List<Attribute> ordenarPorCompania() {    
    List<Attribute> atributo =new ArrayList<>();
    List<Contact> pn= filtrarPorTipoContacto("Compania");
    if (pn== null || pn.isEmpty()) {
        System.out.println("La lista de contactos de personas es nula o vacía.");
        return new ArrayList<>(); 
    }
  for (Contact contacto : pn) {
            CompanyName name = new CompanyName("");
            List<Attribute> at=contacto.findByAttribute(name);
            atributo.addAll(at);
        }
    List<Attribute> copiaContactos = new ArrayList<>(atributo);
    Collections.sort(copiaContactos, new ComparatorPorCompania());
    return copiaContactos;
  }
}
