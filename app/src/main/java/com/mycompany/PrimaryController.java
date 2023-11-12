package com.mycompany;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;


public class PrimaryController implements Initializable {
    @FXML
    private Button btnAddContact;
    @FXML
    private Button btnAddAtribute;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnAddContact.setOnAction(e -> {
            App.setRoot("chooseContactType");
        });
        btnAddAtribute.setOnAction(e -> {
            App.setRoot("chooseAttributeType");
        });
    }
}
