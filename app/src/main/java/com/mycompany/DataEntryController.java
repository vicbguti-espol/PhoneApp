package com.mycompany;

import javafx.scene.control.Alert;

public abstract class DataEntryController extends Controller {
    void noDataAlert(){
        Alert a = new Alert(Alert.AlertType.NONE);

        a.setAlertType(Alert.AlertType.ERROR);
        a.setContentText("Todos los campos son obligatorios");
        a.show();
    }
    
    void sucessDialog(){
        Alert a = new Alert(Alert.AlertType.NONE);
        
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText("Agregado con éxito");
        a.show();
    }
    
    
}
