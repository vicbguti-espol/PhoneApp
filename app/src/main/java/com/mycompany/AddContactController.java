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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.Pair;
import model.attributes.PhoneNumber;
import model.contacts.Contact;
import model.enums.ContactType;
import model.enums.SourceType;
/**
 * FXML Controller class
 *
 * @author vicbguti
 */
public class AddContactController implements Initializable {


    @FXML
    private ComboBox<Pair<String, String>> cmbContactType;
    @FXML
    private Label lblContact;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLastName;
    @FXML
    private ComboBox<Pair<String, SourceType>> cmbPhoneType;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private ComboBox<?> cmbLocationType;
    @FXML
    private TextField txtLocationDescription;
    @FXML
    private TextField txtLocationURL;
    @FXML
    private DatePicker dateBirth;
    
        
    private final static Pair<String, String> EMPTY_PAIR = new Pair<>("", "");
    private final static Pair<String, SourceType> EMPTY_TYPES_PAIR = new Pair<>("", null);
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblContact.setText("Agregar Persona");
        initCmbContactType();
        initCmbPhoneType();
    }    
    
    @FXML
    private void returnHomePage() throws IOException{
        App.setRoot("primary");
    }
    
    @FXML
    private void afterSelection(ActionEvent event) throws IOException {
        String fxml = cmbContactType.getValue().getValue();
        App.setRoot(fxml);
    }
    
    private void initCmbContactType() {

        List<Pair<String,String>> accounts = new ArrayList<>();

        accounts.add( new Pair<>("Persona", "addContact") );
        accounts.add( new Pair<>("Empresa", "addCompany") );

        cmbContactType.getItems().add( EMPTY_PAIR );
        cmbContactType.getItems().addAll( accounts );
        cmbContactType.setValue( EMPTY_PAIR );

        Callback<ListView<Pair<String,String>>, ListCell<Pair<String,String>>> factory =
            (lv) ->
                    new ListCell<Pair<String,String>>() {
                        @Override
                        protected void updateItem(Pair<String, String> item, boolean empty) {
                            super.updateItem(item, empty);
                            if( empty ) {
                                setText("");
                            } else {
                                setText( item.getKey() );
                            }
                        }
                    };

        cmbContactType.setCellFactory( factory );
        cmbContactType.setButtonCell( factory.call( null ) );
    }
    
    private void initCmbPhoneType(){
        List<Pair<String,SourceType>> phoneTypes = new ArrayList<>();

        phoneTypes.add( new Pair<>("Personal", SourceType.PERSONAL) );
        phoneTypes.add( new Pair<>("Trabajo", SourceType.WORK) );
        
        cmbPhoneType.getItems().add( EMPTY_TYPES_PAIR );
        cmbPhoneType.getItems().addAll( phoneTypes );
        cmbPhoneType.setValue( EMPTY_TYPES_PAIR );
        
        
        Callback<ListView<Pair<String,SourceType>>, ListCell<Pair<String,SourceType>>> factory =
            (lv) ->
                    new ListCell<Pair<String,SourceType>>() {
                        @Override
                        protected void updateItem(Pair<String, SourceType> item, boolean empty) {
                            super.updateItem(item, empty);
                            if( empty ) {
                                setText("");
                            } else {
                                setText( item.getKey() );
                            }
                        }
                    };

        cmbPhoneType.setCellFactory( factory );
        cmbPhoneType.setButtonCell( factory.call( null ) );
    }

    @FXML
    private void addPerson(ActionEvent event) {
        ContactType contactType = ContactType.PERSON;
        PhoneNumber phoneNumber = new PhoneNumber(
                txtPhoneNumber.getText(),
                cmbPhoneType.getValue().getValue());
        
        Contact person = new Contact(contactType, phoneNumber);
    }
        

}
