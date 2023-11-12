/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany;

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
import model.contacts.Contact;
import model.user.MobilePhone;

/**
 * FXML Controller class
 *
 * @author arauj
 */
public class AddPresetAtributeController implements Initializable {

    @FXML
    private Button btnAdd;
    @FXML
    private Label mensaje;
    @FXML
    private ComboBox<Attribute> combo;
    @FXML
    private TextField caja;
private Boolean confirma=false;
    private List<Contact> Tcontactos;
    private List<Contact> modificar;
    private List<Attribute>  Alista;
    private String editar="";
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAdd.setOnAction(e -> {
            App.setRoot("primary");
        });
        
        Tcontactos=MobilePhone.getContactList();
         modificar=new ArrayList<>();
         Contact usu=Tcontactos.get(0);//el usuario seleccionado de la lista C1
         modificar.add(usu);
         Alista=modificar.get(0).attributes; 
         combo.getItems().addAll(Alista);  
         if(confirma){
         MobilePhone.removeContact(usu);
         }
    }    

    @FXML
    private void Agregar(MouseEvent event) {
        Boolean confirma=true;
       mensaje.setText("Cambio realizado");
    }

    @FXML
    private void seleccion(ActionEvent event) {
        String dato= caja.getText();
        ComboBox<Attribute> cb = (ComboBox) event.getSource();
        Attribute tipo = cb.getValue();
        editar=tipo.getAttributeName();
    }
    
}
