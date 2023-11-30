/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany;

import collections.CustomLinkedList;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.attributes.Attribute;
import model.attributes.names.PersonName;
import model.attributes.reminders.Birthday;
import model.comparator.ComparatorUtil;
import model.contacts.Contact;
import model.user.MobilePhone;
/**
 * FXML Controller class
 *
 * @author arauj
 */
public class EditContactController extends DataEntryController implements Initializable {


    @FXML
    private Button btnReturn;
    @FXML
    private Label title;
    @FXML
    private Button btnEditContact;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private DatePicker birthdayDatePicker;
    
    private Contact contact;

    public EditContactController(Contact contact) {
        this.contact = contact;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        title.setText(title.getText() + contact);
        configureButtons();
        setData();
    }    

    private void configureButtons() {
        btnReturn.setOnAction(e -> {
            try {
                super.returnContactPage(contact);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        btnEditContact.setOnAction(e -> editContact());
    }

    private void editContact() {
        if (editable()){
            super.noDataAlert();
        } else {
            CustomLinkedList<Attribute> attributes = (CustomLinkedList<Attribute>) contact.getAttributes();
            PersonName pn = (PersonName) attributes.findAll(ComparatorUtil.cmpByAttribute, new PersonName("","")).get(0);
            pn.setFirstName(nameTextField.getText());
            pn.setLastName(lastNameTextField.getText());
            Birthday b = (Birthday) attributes.findAll(ComparatorUtil.cmpByAttribute, new Birthday()).get(0);
            b.setDate(birthdayDatePicker.getValue());
            super.sucessEditDialog();
            MobilePhone.updateContactList();
            try {
                super.returnContactPage(contact);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean editable() {
        return nameTextField.getText().isBlank()
                || lastNameTextField.getText().isBlank()
                || birthdayDatePicker.getValue() == null;
    }

    private void setData() {
        CustomLinkedList<Attribute> attributes = (CustomLinkedList<Attribute>) contact.getAttributes();
        PersonName pn = (PersonName) attributes.findAll(ComparatorUtil.cmpByAttribute, new PersonName("","")).get(0);
        nameTextField.setText(pn.getFirstName());
        lastNameTextField.setText(pn.getLastName());
        Birthday b = (Birthday) attributes.findAll(ComparatorUtil.cmpByAttribute, new Birthday()).get(0);
        birthdayDatePicker.setValue(b.getDate());
    }
    
    
}
