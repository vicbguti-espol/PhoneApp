/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package model.user;

import java.io.IOException;
import java.time.LocalDate;
import model.attributes.Email;


import model.attributes.names.PersonName;
import model.attributes.phone.PersonPhone;
import model.attributes.phone.PhoneNumber;
import model.contacts.Contact;
import model.enums.SourceType;

/**
 *
 * @author naomi
 */
public class prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        //Location lo= new Location(SourceType.PERSONAL, "casa", "wwww");
        Email em=new Email(SourceType.PERSONAL, "correo");
        PersonName n=new PersonName("A","B");
        PersonPhone ph =new PersonPhone("0123456789", SourceType.PERSONAL);
        
        //LocalDate ld= new LocalDate();
//        Contact c1= new Contact(PhoneNumber());
//        MobilePhone.addContact(c1);
    }
    
}
