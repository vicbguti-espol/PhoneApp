/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package model.user;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.attributes.Email;
import model.attributes.location.CompanyLocation;


import model.attributes.names.PersonName;
import model.attributes.phone.PersonPhone;
import model.attributes.phone.PhoneNumber;
import model.contacts.Contact;
import model.enums.SourceType;
import model.contacts.Contact;
import model.attributes.phone.CompanyPhone;
import model.attributes.Attribute;
import model.attributes.location.Location;
import model.attributes.names.CompanyName;
import model.attributes.phone.CompanyPhone;
import model.comparator.ComparatorUtil;
import model.serialization.SerializationUtil;
import model.user.MobilePhone;

/**;
 *
 * @author naomi
 */
public class prueba {

    /**
     * @param args the comm.nd line arguments
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
       
//     List<Contact> contactos=MobilePhone.getContactList(); // c1,c2
//     List<Contact> modificar=new ArrayList<>();//c1
//        System.out.println(contactos.size()); //2
//        System.out.println(contactos.get(0));
//        modificar.add(contactos.get(0));
//        MobilePhone.removeContact(contactos.get(0));//contact1
//        System.out.println(contactos.size());
//        System.out.println(modificar.size());
//       List<Attribute>  lista=modificar.get(0).attributes; 
//       
//        for(Attribute atributos:lista){
//          if(atributos.getAttributeName().equals("CompanyLocation")){
//              Location location = (Location) atributos;
//              System.out.println(location.getDetails());
//              System.out.println(location.getMapsURL());
//              location.setDetails("qaaz");
//              location.setMapsURL("agua");
//              System.out.println(location.getDetails());
//              System.out.println(location.getMapsURL());
//              MobilePhone.addContact(modificar.get(0));
//          }}
        
        List<Contact> contactos=MobilePhone.getContactList();
              List<Attribute>  lista=contactos.get(1).attributes; 
              System.out.println(lista);
//          }
          //     for(Contact con:c){
//         System.out.println(con.attributes.element());//Attribute Name: CompanyPhone
//         System.out.println(con.attributes.get(2));//Attribute Name: CompanyPhone si contiene mas atributos
//         Attribute a=con.attributes.get(0);
       
//     List ob=(List) SerializationUtil.deserialize("ser/contactList.ser");
//        System.out.println(ob.get(0));//contact1
//    }
//        System.out.println(lista.size());//cantidad de atributos 6 de ce1
//        System.out.println(lista.get(0));
//       // System.out.println(lista.get(2));//CompanyLocation
//        
//       
//        System.out.println("//////////////////////////////");
////         List partes =c.get(0).findAttributes(ComparatorUtil.cmpByAttribute, (Attribute) lista.get(2));
////                      if(partes.size()==0){
                          //PersonName.class.cast(c).setFirstName("hola");
//                          CompanyLocation.class.cast(c).setDetails("locacion");
//                          CompanyLocation.class.cast(c).setDetails("detalles");
//       }
            
        
//            
//         CompanyName nuevo;
//         nuevo.getCompanyName();
//          System.out.println(con.attributes);
          //[Attribute Name: CompanyPhone, Attribute Name: CompanyName, Attribute Name: CompanyLocation, Attribute Name: Image, Attribute Name: CompanyDescription, Attribute Name: CompanyWebPage]
     }
    }

    

