/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package model.user;

import collections.CustomLinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.attributes.Attribute;
import model.attributes.names.PersonName;
import model.attributes.phone.PersonPhone;
import model.comparator.ComparatorPorCantidadAtributos;
import model.comparator.ComparatorUtil;

import model.contacts.Contact;

/**
 *
 * @author naomi
 */

public class prueba {

private List<Contact> contactos;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//      
//        System.out.println(filtrarPorTipoContacto("Persona").size());
//       List<Contact> agregado=filtrarPorTipoContacto("Persona");
//       System.out.println(agregado);
       // List<Contact> agregado=filtrarPorCAtributos(7);
       // System.out.println(agregado.get(1).attributes);
        //System.out.println(agregado.get(0).attributes);
       List<Contact> o=filtrarPorNombreApellido("a","a");
         System.out.println(o);
//   List<Contact> listaContactos = MobilePhone.getContactList();  
//   List<Contact> a=ordenarPorCAtributos(listaContactos);
//        System.out.println(a);
    }
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
    
  public static List<Contact> filtrarPorNombreApellido(String nombre, String apellido) {
        List<Contact> listaContactos = MobilePhone.getContactList();
        List<Contact> contactosFiltrados = new ArrayList<>();

        for (Contact contacto : listaContactos) {
            PersonName name = new PersonName("","");
            List<Attribute> at=contacto.findByAttribute(name);   
            PersonName p1=(PersonName)at.get(0);
            if (p1!= null && 
                p1.getFirstName()!= null && p1.getFirstName().equalsIgnoreCase(nombre) &&
                p1.getLastName() != null && p1.getLastName().equalsIgnoreCase(apellido)) {
                contactosFiltrados.add(contacto);
            }
            }
        

        if (contactosFiltrados.isEmpty()) {
            System.out.println("No se encontraron contactos con el nombre y apellido especificados.");
        }

        return contactosFiltrados;
    }

  public static List<Contact> ordenarPorCAtributos(List<Contact> listaContactos) {
        // Verifica si la lista de contactos es nula
        if (listaContactos == null) {
            System.out.println("La lista de contactos es nula.");
            return new ArrayList<>(); // o puedes devolver null si es más apropiado
        }

        // Copia la lista para no modificar la original
        List<Contact> copiaContactos = new ArrayList<>(listaContactos);

        // Utiliza el comparador para ordenar la lista
        Collections.sort(copiaContactos, new ComparatorPorCantidadAtributos());

        return copiaContactos;
    }
  
  
 
  
//    [Attribute Name: PersonPhone, a a, 
//    Attribute Name: ContactImage,
//    Attribute Name: Birthday, 
//    Attribute Name: PersonLocation,
//    Attribute Name: aaa, 
//    Attribute Name: a, ]
    
    
//    [Attribute Name: PersonPhone, diego alay,
//    Attribute Name: PersonLocation,
//    Attribute Name: ContactImage,
//    Attribute Name: Birthday, 
//    Attribute Name: SocialMedia, ]
    
//    [Attribute Name: PersonPhone,
//    deigo alay, 
//    Attribute Name: PersonLocation,
//            Attribute Name: ContactImage,
//    Attribute Name: Birthday, ]
}
