package com.mycompany;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.attributes.reminders.GenericReminder;
import model.attributes.reminders.Reminder;

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnReturn.setOnAction(e -> super.returnHomePage());
        initBtnAddReminder();
    }   
    
    public void initBtnAddReminder(){
        btnAddReminder.setOnAction(e -> 
        {
            if (isPrepared()){
                genericReminder = new GenericReminder(
                        reminderDatePicker.getValue(), 
                        descriptionTextField.getText());
                super.sucessDialog();
                super.returnHomePage();
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
    
}
