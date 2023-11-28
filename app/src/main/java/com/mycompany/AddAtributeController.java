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
import static model.user.MobilePhone.updateContactList;


public class AddAtributeController extends Controller implements Initializable {

    
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
    private Attribute attribute;
    
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
        
    }
    
    private void returnContactPage() throws IOException{
        App.setRoot("contact", new ContactController(contact));
    }

    @FXML
    private void Agregar(MouseEvent event) {
        String descripcion= box_Descripcion.getText();
        String dato= box_dato.getText();
        if(dato==""||descripcion==""){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("No dejar cajas vacias");

        alert.showAndWait();
        }else{
            Mensaje.setText("Atributo agregado");
            Attribute attribute= new GenericAttribute(descripcion,dato);
            contact.attributes.add(attribute);
            updateContactList();
        }
    }
    
}
