package com.mycompany;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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
    
    boolean confirmationAlert(String string){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Atención!");
        a.setContentText("Estas seguro de eliminar el " + string + "?"
                + "\nNo podrá revertir esta acción");
        Optional<ButtonType> result = a.showAndWait();
        return (result.get() == ButtonType.OK);
    }
}
