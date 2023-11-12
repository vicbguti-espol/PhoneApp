/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.contacts.Contact;
import model.user.MobilePhone;

/**
 * FXML Controller class
 *
 * @author arauj
 */
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
        List<Contact> contactList;
        try {
            contactList = MobilePhone.getContactList();
            ObservableList<Contact> contactObservableList = FXCollections.observableList(contactList);
            contactListView = new ListView<>(contactObservableList);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
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
    
    private void goContactPage(Contact selectedContact) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("contact.fxml"));
        ContactController contactController = new ContactController(selectedContact);
        loader.setController(contactController);
        
        BorderPane root = (BorderPane) loader.load();
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) contactListView.getScene().getWindow();
        stage.setScene(scene);
    }
    
    
    
}
