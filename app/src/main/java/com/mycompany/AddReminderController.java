package com.mycompany;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.attributes.reminders.GenericReminder;
import model.attributes.reminders.Reminder;
import model.contacts.Contact;
import model.user.MobilePhone;

public class AddReminderController 
        extends DataEntryController 
        implements Initializable {
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnAddReminder;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private DatePicker reminderDatePicker;
    
    private Reminder genericReminder;
    
    private Contact contact;

    AddReminderController(Contact selectedContact) {
        contact = selectedContact;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnReturn.setOnAction(e -> {
             try {
                 returnContactPage();
             } catch (IOException ex) {
                 ex.printStackTrace();
             }
        });
        initBtnAddReminder();
    }   
    
    public void initBtnAddReminder(){
        btnAddReminder.setOnAction(e -> 
        {
            if (isPrepared()){
                genericReminder = new GenericReminder(
                        reminderDatePicker.getValue(), 
                        descriptionTextField.getText());
                contact.getAttributes().add(genericReminder);
                MobilePhone.updateContactList();
                super.sucessDialog();
                try {
                    super.returnContactPage(contact);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else{
                super.noDataAlert();
            }
        }
        );
    }
    
    public Boolean isPrepared(){
        return !descriptionTextField.getText().equals("") &&
               reminderDatePicker.getValue() != null;
    }
    
    public Reminder getGenericReminder(){
        return genericReminder;
    }
    
    private void returnContactPage() throws IOException{
        App.setRoot("contact", new ContactController(contact));
    }
    
}
