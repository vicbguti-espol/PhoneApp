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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.attributes.Attribute;
import model.attributes.company.CompanyDescription;
import model.attributes.company.CompanyWebPage;
import model.attributes.names.CompanyName;
import model.attributes.names.PersonName;
import model.attributes.reminders.Birthday;
import model.util.comparator.ComparatorUtil;
import model.contacts.Contact;
import model.user.MobilePhone;
/**
 * FXML Controller class
 *
 * @author arauj
 */
public class EditCompanyController extends DataEntryController implements Initializable {


    @FXML
    private Button btnReturn;
    @FXML
    private Label title;
    @FXML
    private Button btnEditContact;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField webTextField;
    
    private Contact contact;
    
    public EditCompanyController(Contact contact) {
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
            CompanyName cn = (CompanyName) attributes.findAll(ComparatorUtil.cmpByAttribute, new CompanyName("")).get(0);
            cn.setCompanyName(this.nameTextField.getText());
            CompanyDescription cd = (CompanyDescription) attributes.findAll(ComparatorUtil.cmpByAttribute, new CompanyDescription("")).get(0);
            cd.setDescription(this.descriptionTextField.getText());
            CompanyWebPage cwp = (CompanyWebPage) attributes.findAll(ComparatorUtil.cmpByAttribute, new CompanyWebPage()).get(0);
            cwp.setWebPage(webTextField.getText());
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
                || descriptionTextField.getText().isBlank()
                || webTextField.getText().isBlank();
    }

    private void setData() {
        CustomLinkedList<Attribute> attributes = (CustomLinkedList<Attribute>) contact.getAttributes();
        CompanyName cn = (CompanyName) attributes.findAll(ComparatorUtil.cmpByAttribute, new CompanyName("")).get(0);
        nameTextField.setText(cn.getCompanyName());
        CompanyDescription cd = (CompanyDescription) attributes.findAll(ComparatorUtil.cmpByAttribute, new CompanyDescription("")).get(0);
        descriptionTextField.setText(cd.getDescription());
        CompanyWebPage cwp = (CompanyWebPage) attributes.findAll(ComparatorUtil.cmpByAttribute, new CompanyWebPage()).get(0);
        webTextField.setText(cwp.getWebPage());
    }
}
