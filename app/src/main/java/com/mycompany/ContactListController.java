/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.serialization.SerializationUtil;
import model.contacts.Contact;

public class ContactListController implements Initializable {

    @FXML
    private Button btnAdd;
    @FXML
    private ListView<Contact> ListView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
   try {
            // Deserializa tu objeto desde el archivo
            Object ob = SerializationUtil.deserialize("ser/contactList.ser");

            // Verifica si el objeto es una lista de contactos
            if (ob instanceof List<?>) {
                List<Contact> contactList = (List<Contact>) ob;

                // Crea un ObservableList a partir de la lista de contactos
                ObservableList<Contact> observableContactList = FXCollections.observableArrayList(contactList);
           
                // Asigna el ObservableList al ListView
                ListView.setItems(observableContactList);
                
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private void goContactPage(Contact selectedContact) throws IOException {
        Controller contactController = new ContactController(selectedContact);
        App.setRoot("contact",contactController);
    }
    
    @FXML
    private void goPrimaryPage(){
        App.setRoot("primary");
    }
}
