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
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.Pair;
/**
 * FXML Controller class
 *
 * @author vicbguti
 */
public class AddCompanyController implements Initializable {


    @FXML
    private ComboBox<Pair<String, String>> cmbContactType;
    @FXML
    private Button btnAdd;
    @FXML
    private Label lblContact;
    
    private final static Pair<String, String> EMPTY_PAIR = new Pair<>("", "");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblContact.setText("Agregar Empresa");
        initCombo();
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
    
    private void initCombo() {

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
}
