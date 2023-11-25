package com.mycompany;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.contacts.Contact;
import model.user.MobilePhone;

public class ContactListController implements Initializable {

    @FXML
    private Button btnAdd;
    @FXML
    private ListView<Contact> ListView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Crea un ObservableList a partir de la lista de contactos
        ObservableList<Contact> observableContactList = FXCollections.
                observableArrayList(MobilePhone.getContactList());

        // Asigna el ObservableList al ListView
        ListView.setItems(observableContactList);           
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
