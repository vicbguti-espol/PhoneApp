/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.contacts.Contact;

/**
 * FXML Controller class
 *
 * @author arauj
 */
public class ContactImagesController extends Controller implements Initializable {

    @FXML
    private Button btnReturn;
    @FXML
    private Label lblTitle;
    
    private Contact contact;
    
    public ContactImagesController(){}

    public ContactImagesController(Contact contact) {
        this.contact = contact;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnReturn.setOnAction(r -> {
            try {
                returnContactPage();
            } catch (IOException ex) {
                System.out.println("PEPE");
                ex.printStackTrace();
            }
        });
    }    
    
    @FXML
    private void returnContactPage() throws IOException{
        App.setRoot("contact", new ContactController(contact));
    }
}
