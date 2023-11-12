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
import model.attributes.company.CompanyDescription;
import model.attributes.company.CompanyWebPage;
import model.attributes.location.Location;
import model.attributes.names.CompanyName;
import model.attributes.names.PersonName;
import model.attributes.phone.PhoneNumber;
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
    private Boolean confir=false;
    @FXML
    private Button btnAdd;
    @FXML
    private Label nombredeatributo;
    @FXML
    private TextField box_dato;
    @FXML
    private ComboBox<Attribute> tiposA;
//    @Override
    public void initialize(URL url, ResourceBundle rb) {
         contactList=MobilePhone.getContactList();
         tiposA.getItems().addAll(encontrarA());
                        }
    
    
    @FXML
    private void btn_volver(MouseEvent event) throws IOException {
         App.setRoot("primary");
    }

    public List<Attribute> encontrarA(){
        List nueva= new ArrayList<Attribute>();
        for(Contact c:contactList){
            c.findAttributes(cmp, email);
            nueva= (List) c;
        }
        return nueva;
    }
    
    @FXML
    private void btn_confirmar(MouseEvent event) {
    Boolean confir=true;

        }
    

    @FXML
    private void seleccion(ActionEvent event) {
        String dato= box_dato.getText();
        ComboBox<Attribute> cb = (ComboBox) event.getSource();
        Attribute tipo = cb.getValue();
        if(confir){
            if(tipo.equals(location)){
            location.setMapsURL(dato); 
            location.setDetails(dato);
            }else if(tipo.equals(personaname)){
            personaname.setFirstName(dato);
            personaname.setLastName(dato);             
            }else if(tipo.equals(associatedContact)){
            associatedContact.setRelation(dato);
            }else if(tipo.equals(email)){
            email.setEmail(dato);
            }else if(tipo.equals(companyWebPage)){
            companyWebPage.setWebPage(dato);
            }else if(tipo.equals(companyDescription)){
            companyDescription.setDescription(dato);
    }    else if(tipo.equals(companyName)){
            companyName.setCompanyName(dato);
    }else if(tipo.equals(phoneNumber )){
            phoneNumber.setPhoneNumber(dato);
}}
}
}
