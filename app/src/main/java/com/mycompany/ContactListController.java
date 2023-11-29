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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import model.contacts.Contact;
import model.user.MobilePhone;

public class ContactListController implements Initializable {

    @FXML
    private Button btnAdd;
    @FXML
    private ListView<Contact> contactListView;
    @FXML
    private BorderPane root;
    
    
     /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Contact> contactList = MobilePhone.getContactList();
        ObservableList<Contact> contactObservableList = FXCollections.observableList(contactList);
        contactListView = new ListView<>(contactObservableList);
        root.setCenter(contactListView);
        contactListView.setOnMouseClicked(eh -> {
            Contact selectedContact = contactListView.getSelectionModel().getSelectedItem();
            if (selectedContact != null){
                try {
                    goContactPage(selectedContact);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
    
    /*private void setFilters() {
        HBox filters = new HBox();
        for (int i = 0; i < 3; i++){
            ComboBox
        }
        filters
        
    }*/

    private void goContactPage(Contact selectedContact) throws IOException {
        Controller contactController = new ContactController(selectedContact);
        App.setRoot("contact",contactController);
    }
    
    @FXML
    private void goChooseContactTypePage(){
        App.setRoot("chooseContactType");
    }
}

