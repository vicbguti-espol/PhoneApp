/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.attributes.Attribute;
import model.attributes.PhoneNumber;
import model.contacts.Contact;
import model.contacts.Company;
import model.contacts.Person;

/**
 * FXML Controller class
 *
 * @author arauj
 */
public class ContactController implements Initializable {

    @FXML
    private Button btnReturn;
    @FXML
    private CheckBox cboxFavorite;
    @FXML
    private Button btnDeleteContact;
    @FXML
    private VBox vbContent;
    @FXML
    private Label lblName11;
    @FXML
    private Label lblName;
    @FXML
    private Label lblName1;
    @FXML
    private ImageView imgvPicture;
    @FXML
    private Button btnViewImages;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Contact contact = new Contact();
        createHeader(contact);
        ArrayList<Attribute> attributes = contact.getAttributes();
        /*for (Attribute attribute: attributes){
            Label title = new Label(attribute.getAttributeName());
            if (attribute instanceof ArrayList<PhoneNumber  >){
                
            }
        }*/
        
    }
    
    @FXML
    private void returnContactListPage() throws IOException{
        App.setRoot("contactList");
    }
    
    @FXML
    private void goContactImagesPage() throws IOException {
        App.setRoot("contactImages");
    }

    private void createHeader(Contact c) {
        /*if (c instanceof Company){
            Label tipo = new Label("Empresa");
        }
        Label nombre = new Label(c.getUserName());
        String path = c.getAttributes().getImageProfile();
        Image imageProfile = new Image(path);
        ImageView imageView = new ImageView(imageProfile);*/
    }
    
}
