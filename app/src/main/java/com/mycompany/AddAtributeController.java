package com.mycompany;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.attributes.Attribute;
import model.attributes.GenericAttribute;
import model.contacts.Contact;


public class AddAtributeController extends Controller implements Initializable {

    @FXML
    private Button btnReturn;
    @FXML
    private Label Mensaje;
    @FXML
    private Button btn_A;
    @FXML
    private ComboBox<?> cbx_Generico;
    @FXML
    private TextField box_Descripcion;
    @FXML
    private TextField box_dato;
    
    private Contact contact;
    
    public AddAtributeController(Contact contact) {
        this.contact = contact;
    }

    @FXML
    private void goContactListPage(ActionEvent event) {
        App.setRoot("contactList");
    }

    @FXML
    private void Guardar(MouseEvent event) {
        Attribute a = new GenericAttribute();
        contact.getAttributes().add(a);
    }

    @FXML
    private void selecion(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnReturn.setOnAction(e -> {
            try {
                returnContactPage();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    private void returnContactPage() throws IOException{
        App.setRoot("contact", new ContactController(contact));
    }
    
    
}
