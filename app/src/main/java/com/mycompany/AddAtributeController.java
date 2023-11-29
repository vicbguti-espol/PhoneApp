package com.mycompany;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.attributes.Attribute;
import model.attributes.GenericAttribute;
import model.contacts.Contact;
import model.user.MobilePhone;


public class AddAtributeController extends DataEntryController implements Initializable {

    
    @FXML
    private Label Mensaje;
    @FXML
    private TextField box_Descripcion;
    @FXML
    private TextField box_dato;
    
    @FXML
    private Button btn_Return;
    @FXML
    private Button btnAdd;
    private Contact contact; 
    
    AddAtributeController(Contact contact) {
        this.contact = contact;
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn_Return.setOnAction(e -> {
             try {
                 returnContactPage();
             } catch (IOException ex) {
                 ex.printStackTrace();
             }
        });
        btnAdd.setOnAction(e -> {
            try {
                agregar();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
    }
    
    private void returnContactPage() throws IOException{
        App.setRoot("contact", new ContactController(contact));
    }

    private void agregar() throws IOException {
        String descripcion= box_Descripcion.getText();
        String dato= box_dato.getText();
        if(dato==""||descripcion==""){
            super.noDataAlert();
        }else{
            Attribute attribute= new GenericAttribute(descripcion,dato);
            contact.attributes.add(attribute);
            MobilePhone.updateContactList();
            super.sucessDialog();
            super.returnContactPage(contact);
        }
    }
    
}
