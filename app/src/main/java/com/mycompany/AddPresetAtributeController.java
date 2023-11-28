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
import model.attributes.AssociatedContact;
import model.attributes.Attribute;
import model.attributes.Email;
import model.attributes.SocialMedia;
import model.attributes.location.CompanyLocation;
import model.attributes.location.Location;
import model.attributes.phone.CompanyPhone;
import model.attributes.phone.PhoneNumber;
import model.attributes.reminders.GenericReminder;
import model.contacts.Contact;
import model.contacts.Person;
import model.enums.SocialMediaType;
import model.enums.SourceType;
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
    private ComboBox<String> combo;
    @FXML
    private TextField caja;   
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

         
       String d1= new String("Numero telefonico");
       String d2= new String("Direccion");
       String d3= new String("Redes Sociales");
       String d4= new String("Correo Electronico");
       String d5= new String("Contactos Asociados");
       String d6= new String("Recodatorio");
       combo.getItems().addAll(d1,d2,d3,d4,d5,d6);
    }    

    @FXML
    private void Agregar(MouseEvent event) {
       String dato= caja.getText();//informarcion agregar
       
                
         Tcontactos=MobilePhone.getContactList();
         modificar=new ArrayList<>();
         Contact usu=Tcontactos.get(0);//el usuario seleccionado de la lista C1
         modificar.add(usu);
         Alista=modificar.get(0).attributes; 
          
         
         //MobilePhone.removeContact(usu);
         
//        for(Attribute atributos:Alista){
//        if(editar.equals("Numero telefonico")){
//            PhoneNumber ph = (PhoneNumber) atributos;
//            ph.agregarMasN(new CompanyPhone(dato));          
//        }else if(editar.equals("Direccion")){
//            Location loc = (Location) atributos;
//            loc.agregarMasL(new CompanyLocation(dato,dato));
//        }else if(editar.equals("Redes Sociales")){
//            Alista.add(new SocialMedia(dato, SocialMediaType.FACEBOOK ));           
//        }else if(editar.equals("Correo Electronico")){
//           Alista.add( new Email(SourceType.PERSONAL, dato));
//        }else if(editar.equals("Contactos Asociados")){
//           Alista.add(new AssociatedContact(new Person(new CompanyPhone("094458710")), dato));
//        }else if(editar.equals("Recodatorio")){
//            Alista.add(new GenericReminder(dato));
//        }
//        }
        //MobilePhone.addContact(modificar.get(0));
        mensaje.setText("Cambio realizado");
        }
       
       
    

    @FXML
    private void seleccion(ActionEvent event) {
        ComboBox<String> cb = (ComboBox) event.getSource(); 
        String tipo = cb.getValue(); ///lo que se quiere agregar       
        editar=tipo;
    }
}
