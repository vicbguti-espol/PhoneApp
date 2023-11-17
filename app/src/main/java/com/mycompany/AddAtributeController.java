/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
<<<<<<< HEAD
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.attributes.Attribute;
import model.contacts.Contact;
import model.user.MobilePhone;
import model.attributes.GenericAttribute;
=======
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
>>>>>>> 2566038b85a0e54c1345947c768409895a17ef3c

/**
 * FXML Controller class
 *
 * @author arauj
 */
public class AddAtributeController implements Initializable {

<<<<<<< HEAD
    @FXML
    private Button btnAdd;
    @FXML
    private Button btn_A;
    @FXML
    private ComboBox<Attribute> Cbx_G;
    @FXML
    private TextField decrip;
    @FXML
    private TextField Dato;
private Boolean confirma=false;
    private List<Contact> Tcontactos;
    private List<Contact> modificar;
    private List<Attribute>  Alista;
    private Attribute at;
=======
    
    @FXML
    private Button btnReturn;
>>>>>>> 2566038b85a0e54c1345947c768409895a17ef3c
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
<<<<<<< HEAD
        btnAdd.setOnAction(e -> {
            App.setRoot("primary");
        });
        
        String descripcion= decrip.getText();
        String dato=Dato.getText();
        
        Tcontactos=MobilePhone.getContactList();
        modificar=new ArrayList<>();
        Contact usu=Tcontactos.get(0);//el usuario seleccionado de la lista C1
        modificar.add(usu);
        Alista=modificar.get(0).attributes;
        if(confirma){
           MobilePhone.removeContact(usu);
           GenericAttribute nuevo= (GenericAttribute)at;
           nuevo.setDescripcion(descripcion);
           nuevo.setValue(dato);
           Alista.add(nuevo);
           MobilePhone.addContact(modificar.get(0));
        }
    }    

    @FXML
    private void selecion(ActionEvent event) {
        
    }

    @FXML
    private void Guardar(MouseEvent event) {
        Boolean confirma=true;
    }
=======
        
    }
    
    @FXML
    public void goContactListPage(){
        App.setRoot("contactList");
    }
    
>>>>>>> 2566038b85a0e54c1345947c768409895a17ef3c
    
}
