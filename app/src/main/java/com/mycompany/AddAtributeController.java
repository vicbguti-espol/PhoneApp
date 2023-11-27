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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.attributes.Attribute;
import model.contacts.Contact;
import model.user.MobilePhone;
import model.attributes.GenericAttribute;

public class AddAtributeController extends Controller implements Initializable {
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
    @FXML
    private Button btnReturn;
    
    private Boolean confirma=false;
    private List<Contact> Tcontactos;
    private List<Contact> modificar;
    private List<Attribute>  Alista;
    private Attribute at;
    
    private Contact contact;
    
    public AddAtributeController(Contact selectedContact) {
        contact = selectedContact;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn_A.setOnAction(e -> {
            App.setRoot("primary");
        });
        
        btnReturn.setOnAction(e -> {
            try {
                returnContactPage();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
        Boolean confirma = true;
    }
    
    @FXML
    public void goContactListPage(){
        App.setRoot("contactList");
    }
    
    private void returnContactPage() throws IOException{
        App.setRoot("contact", new ContactController(contact));
    }
    
    
}
