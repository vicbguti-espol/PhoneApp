package com.mycompany;

import java.io.IOException;
import model.contacts.Contact;

public abstract class Controller {
    protected void returnHomePage(){
        App.setRoot(new ContactListController().getRoot());
        // App.setRoot("contactList");
    }
    
    protected void returnContactPage(Contact contact) throws IOException{
        App.setRoot("contact", new ContactController(contact));
    }
    

}
