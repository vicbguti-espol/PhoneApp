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
import model.contacts.Contact;
import model.serialization.SerializationUtil;

/**
 * FXML Controller class
 *
 * @author arauj
 */
public class EditPresetAtributeController implements Initializable {

    private List<Contact> contactList;
    @FXML
    private Button btnAdd;
    @FXML
    private Label nombredeatributo;
    @FXML
    private TextField box_dato;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //List<Contact> contac= (ArrayList<Contact>)
        
       
    }
    @FXML
    private void btn_volver(MouseEvent event) throws IOException {
         App.setRoot("primary");
    }

    @FXML
    private void btn_confirmar(MouseEvent event) {
        
        String dato =box_dato.getText();
    }

    @FXML
    private void Tipos(ActionEvent event) {
        
    }
    
}
