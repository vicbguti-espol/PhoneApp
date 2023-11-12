/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.attributes.Attribute;
import model.attributes.reminders.Reminder;
import model.contacts.Contact;
import model.serialization.SerializationUtil;
import model.user.MobilePhone;
import java.util.Comparator;
import javafx.scene.control.ComboBox;
import model.attributes.AssociatedContact;
import model.attributes.Email;
import model.attributes.Image;
import model.attributes.company.CompanyDescription;
import model.attributes.company.CompanyWebPage;
import model.attributes.location.Location;
import model.attributes.location.PersonLocation;
import model.attributes.names.CompanyName;
import model.attributes.names.PersonName;
import model.attributes.phone.PersonPhone;
import model.attributes.phone.PhoneNumber;
import model.attributes.reminders.Birthday;
import model.comparator.ComparatorUtil;

/**
 * FXML Controller class
 *
 * @author arauj
 */
public class EditPresetAtributeController implements Initializable {

    private List<Contact> contactList;
    private Comparator<Attribute> cmp;
    private Location location;
    private PersonName personaname;
    private Reminder reminder;
    private AssociatedContact associatedContact;
    private Email email;
    private CompanyWebPage companyWebPage ;
    private CompanyDescription companyDescription;
    private CompanyName companyName;
    private PhoneNumber phoneNumber;
    ComparatorUtil cmd;
    
    @FXML
    private TextField box_dato;
    @FXML
    private ComboBox<Attribute> tiposA;
    private Boolean confirma=false;
    private List<Contact> Tcontactos;
    private List<Contact> modificar;
    private List<Attribute>  Alista;
    private String editar="";
    @FXML
    private Label mensaje;
    @FXML
    private Button btn_volver;
//    @Override
    public void initialize(URL url, ResourceBundle rb) {
         btn_volver.setOnAction(e -> {
            App.setRoot("primary");
        });
        
         Tcontactos=MobilePhone.getContactList();
         modificar=new ArrayList<>();
         String caja="";
         Contact usu=Tcontactos.get(0);//el usuario seleccionado de la lista C1
         modificar.add(usu);
         Alista=modificar.get(0).attributes; 
         tiposA.getItems().addAll(Alista);
         if(confirma){
         MobilePhone.removeContact(usu);//C1 
         for(Attribute atributos:Alista){
          if(atributos.getAttributeName().equals(editar)){
    //[Attribute Name: CompanyPhone, Attribute Name: CompanyName, Attribute Name: CompanyLocation,
    //Attribute Name: Image, Attribute Name: CompanyDescription, Attribute Name: CompanyWebPage]
          if(editar.equals("CompanyPhone")){
                PhoneNumber ph = (PhoneNumber) atributos;     
                ph.setPhoneNumber(editar);
          }else if(editar.equals("CompanyName")){
             CompanyName cn = (CompanyName) atributos;
             cn.setCompanyName(caja);
          }else if(editar.equals("CompanyLocation")){
             Location loc = (Location) atributos;
             loc.setDetails(caja);
             //loc.getMapsURL(caja);
          }else if(editar.equals("Image")){
            Image im = (Image) atributos;
            im.setPath(caja);
          }else if(editar.equals("CompanyDescription")){
             CompanyDescription cd = (CompanyDescription) atributos;
             cd.setDescription(caja);
          }else if(editar.equals("CompanyWebPage")){
             CompanyWebPage cw = (CompanyWebPage) atributos;
             cw.setWebPage(caja);
          }
          ///// Persona
//          [Attribute Name: PersonPhone, diego javier, Attribute Name: PersonLocation, 
//          Attribute Name: Image, Attribute Name: Birthday]
          else if(editar.equals("PersonPhone")){
             PersonPhone pp = (PersonPhone) atributos;
             pp.setPhoneNumber(caja);
          }
          else if(editar.equals("PersonLocation")){
             PersonLocation pl = (PersonLocation) atributos;
             pl.setDetails(caja);
          }
          
           
          MobilePhone.addContact(usu);
    }
   
    }
    } 
    }
    
    
    @FXML
    private void btn_confirmar(MouseEvent event) {
    Boolean confirma=true;
    mensaje.setText("Cambio realizado");
        }
    

    @FXML
    private void seleccion(ActionEvent event) {
        String dato= box_dato.getText();
        ComboBox<Attribute> cb = (ComboBox) event.getSource();
        Attribute tipo = cb.getValue();
        editar=tipo.getAttributeName();
}

    @FXML
    private void g(MouseEvent event) {
    }
    
}
