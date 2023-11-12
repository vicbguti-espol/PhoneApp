/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
/**
 * FXML Controller class
 *
 * @author naomi
 */
public class AddPresetAtributeController implements Initializable {


    @FXML
    private Button btnAdd;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       btnAdd.setOnAction(e -> {
      App.setRoot("primary");
       });
    }    
    
}
