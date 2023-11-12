/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.attributes.Attribute;
import model.attributes.*;
import model.attributes.names.PersonName;
import model.contacts.Contact;

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
    private Label lblName;
    @FXML
    private ImageView imgvPicture;
    @FXML
    private Button btnViewImages;
    @FXML
    private BorderPane root;
    
    private Contact contact;
    @FXML
    private VBox vbPhoneNumbers;
    @FXML
    private VBox vbLocations;
    
    public ContactController(){}

    public ContactController(Contact selectedContact) {
        contact = selectedContact;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createHeader();
        btnViewImages.setOnAction(r -> {
            try {
                goContactImagesPage(contact);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        btnReturn.setOnAction(r -> {
            try {
                returnContactListPage();
            } catch (IOException ex) {
                System.out.println("PEPE");
                ex.printStackTrace();
            }
        });
        System.out.println("Se logro");
        System.out.println(contact);
        
    }
        
    @FXML
    private void returnContactListPage() throws IOException{
        App.setRoot("contactList");
    }
    
    private void goContactImagesPage(Contact contact) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("contactImages.fxml"));
        ContactImagesController contactImagesController = new ContactImagesController(contact);
        loader.setController(contactImagesController);
        
        BorderPane root = (BorderPane) loader.load();
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();//(Stage) contactListView.getScene().getWindow();
        stage.setScene(scene);;
    }

    private void createHeader() {
        Comparator<Attribute> cmpByClass = new Comparator<>(){
            @Override
            public int compare(Attribute t, Attribute t1) {
                if (t.getAttributeName().equals(t1.getAttributeName())) return 0;
                else return 20;
            }
        };
        
        Attribute name = contact.find(cmpByClass, new PersonName("Jose","Jose"));
        model.attributes.Image imgProfile = (model.attributes.Image) contact.find(cmpByClass, new model.attributes.Image());
        
        //lblName.setText(name.getAttributeName());
        lblName.setText("Pepe");
        
        String path = "/" + imgProfile.getPath();
        System.out.println(path);
        try {
            javafx.scene.image.Image image = new javafx.scene.image.Image(path);
            ImageView imgView = new ImageView(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //vbContent.getChildren().add(lblName);
        
        //root.setCenter(content);
    }
    
    /*private void makeList(){
        contact.find
    }*/

   
    
}
