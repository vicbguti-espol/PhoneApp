package com.mycompany;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.util.Pair;

public class ChooseAttributeTypeController extends Controller 
        implements Initializable {
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnContinue;
    @FXML
    private ComboBox<CustomPair> cmbAttributeType;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initContinueButton();
        initCmb();
        btnReturn.setOnAction(e -> super.returnHomePage());
    }    
    
    private void initContinueButton(){
        btnContinue.setOnAction(e -> 
        {
            App.setRoot(cmbAttributeType.getValue().getValue());
            
        });
    }
    
    private void initCmb(){
        cmbAttributeType.getItems().add(new CustomPair(
                "Reminder", 
                "addReminder"));
    }
    
    private class CustomPair extends Pair<String, String>{
    
        public CustomPair(String k, String v) {
            super(k, v);
        }
        
        @Override
        public String toString(){
            return super.getKey();
        }
    
    }
    
    
}
