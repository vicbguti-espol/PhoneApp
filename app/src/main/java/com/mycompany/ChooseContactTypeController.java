package com.mycompany;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.Pair;

public class ChooseContactTypeController extends Controller 
        implements Initializable {


    @FXML
    private Button btnReturn;
    @FXML
    private ComboBox<Pair<String,Pair<String, Controller>>> cmbContactType;
    
    private final static Pair<String, Pair<String, Controller>> EMPTY_PAIR = 
        new Pair<>("", new Pair<>("", null));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCmbContactType();
    }   
    
    @FXML
    private void afterContinue(){
        if ( !cmbContactType.getValue().equals(EMPTY_PAIR) ){
            try {
                String fxml = cmbContactType.getValue().getValue().getKey();
                Controller controller = cmbContactType.getValue().getValue().getValue();
                App.setRoot(fxml, controller);
            } catch (IOException ex) {
                System.err.println("Failed to open the selected "
                        + "contact type template");
                ex.printStackTrace();
            }
        } else {
            
        }

    }
    
    @FXML
    private void returnHomePage(){
        try {
            App.setRoot("primary");
        } catch (IOException ex) {
            System.err.println("Failed to return to home page");
            ex.printStackTrace();
        }  
    }
    
    private void initCmbContactType() {

        List<Pair<String,Pair<String, Controller>>> accounts = new ArrayList<>();

        accounts.add( new Pair<>("Persona", new Pair<>("addContact",
                new AddContactController())) );
        accounts.add( new Pair<>("Empresa", new Pair<>("addCompany",
                new AddCompanyController())) );

        cmbContactType.getItems().add( EMPTY_PAIR );
        cmbContactType.getItems().addAll( accounts );
        cmbContactType.setValue( EMPTY_PAIR );

        Callback<ListView<Pair<String,Pair<String, Controller>>>, ListCell<Pair<String,Pair<String, Controller>>>> factory =
            (lv) ->
                    new ListCell<Pair<String,Pair<String, Controller>>>() {
                        @Override
                        protected void updateItem(Pair<String, Pair<String, Controller>> item, boolean empty) {
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
