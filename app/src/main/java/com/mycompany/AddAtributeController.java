package com.mycompany;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class AddAtributeController implements Initializable {

    @FXML
    private Button btnReturn;
    @FXML
    private Label Mensaje;
    @FXML
    private Button btn_A;
    @FXML
    private ComboBox<?> cbx_Generico;
    @FXML
    private TextField box_Descripcion;
    @FXML
    private TextField box_dato;

    @FXML
    private void goContactListPage(ActionEvent event) {
        App.setRoot("contactList");
    }

    @FXML
    private void Guardar(MouseEvent event) {
    }

    @FXML
    private void selecion(ActionEvent event) {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    
    
}
