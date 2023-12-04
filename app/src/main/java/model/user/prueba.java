/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
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
import model.attributes.phone.PersonPhone;
import model.util.comparator.ComparatorPorCantidadAtributos;
import model.util.comparator.ComparatorPorCompania;
import model.util.comparator.ComparatorPorNombreApellido;
import model.util.comparator.ComparatorUtil;

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
//       List<Contact> agregado=filtrarPorTipoContacto("Company");
//       System.out.println(agregado);
       // List<Contact> agregado=filtrarPorCAtributos(7);
       // System.out.println(agregado.get(1).attributes);
        //System.out.println(agregado.get(0).attributes);
//       List<Contact> o=filtrarPorNombreApellido("deigo","javier");
//         System.out.println(o.get(0).attributes);
//   List<Contact> listaContactos = MobilePhone.getContactList();  
//   List<Contact> a=ordenarPorCAtributos(listaContactos);
//        System.out.println(a);
         // List<Contact>v=ordenarPorNombreApellido();
         // System.out.println(v);
         List<Attribute>d= ordenarPorCompania();
         System.out.println(d);
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
       List<Contact> pn= filtrarPorTipoContacto("Persona");
      
        List<Contact> contactosFiltrados = new ArrayList<>();

        for (Contact contacto : pn) {
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
  
 public static List<Contact> ordenarPorNombreApellido(List<Contact> pn) {    
    
    //List<Contact> pn= filtrarPorTipoContacto("Persona");
    if (pn== null || pn.isEmpty()) {
        System.out.println("La lista de contactos de personas es nula o vacía.");
        return new ArrayList<>(); 
    }

    CustomList<Contact> copiaContactos = new CustomLinkedList<>(pn);
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
