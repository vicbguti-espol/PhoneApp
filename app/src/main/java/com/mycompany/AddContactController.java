package com.mycompany;

import collections.CustomLinkedList;
import collections.CustomList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Pair;
import model.attributes.location.PersonLocation;
import model.attributes.names.PersonName;
import model.attributes.phone.PersonPhone;
import model.attributes.reminders.Birthday;
import model.contacts.Person;
import model.enums.SourceType;

public class AddContactController extends AddContactTypeController 
        implements Initializable {

    @FXML
    private ComboBox<Pair<String, SourceType>> cmbPhoneType;
    @FXML
    private ComboBox<Pair<String, SourceType>> cmbLocationType;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private TextField txtLocationDescription;
    @FXML
    private TextField txtLocationURL;
    @FXML
    private DatePicker dateBirth;
    @FXML
    private Button btnReturn;
    @FXML
    private Button btnAdd;
    @FXML
    private VBox centerVBox;

    private Birthday birthday;
    private final static Pair<String, SourceType> EMPTY_TYPES_PAIR =
            new Pair<>("", null);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();
    }
    
    
    private void initCmbTypo(ComboBox cmbTypos) {
        CustomList<Pair<String,SourceType>> types = new CustomLinkedList<>();

        types.add( new Pair<>("Personal", SourceType.PERSONAL) );
        types.add( new Pair<>("Trabajo", SourceType.WORK) );
        
        cmbTypos.getItems().add( EMPTY_TYPES_PAIR );
        cmbTypos.getItems().addAll( types );
        cmbTypos.setValue( EMPTY_TYPES_PAIR );
        
        
        Callback<ListView<Pair<String,SourceType>>, ListCell<Pair<String,SourceType>>> factory =
            (lv) ->
                    new ListCell<Pair<String,SourceType>>() {
                        @Override
                        protected void updateItem(Pair<String, SourceType> item, boolean empty) {
                            super.updateItem(item, empty);
                            if( empty ) {
                                setText("");
                            } else {
                                setText( item.getKey() );
                            }
                        }
                    };

        cmbTypos.setCellFactory( factory );
        cmbTypos.setButtonCell( factory.call( null ) );
    }
    
    @Override
    void loadPhone(){
        // create attribute instances
        phone = new PersonPhone(
        txtPhoneNumber.getText(),
        cmbPhoneType.getValue().getValue());
    }
    
    @Override
    void loadContact(){
        // create person contact
        contact = new Person(phone);
        addAttribute(new PersonName(
        txtName.getText(), 
        txtLastName.getText()));
    }
    
    @Override
    void loadLocation(){
        // handling location and birthday contact info
        location = new PersonLocation(
                cmbLocationType.getValue().getValue(),
                txtLocationDescription.getText(),
                txtLocationURL.getText());    
    }
    
    @Override
    void loadTypeData(){
        birthday = new Birthday(dateBirth.getValue());
    }

    @Override
    boolean isTypePrepared(){
        return !txtPhoneNumber.getText().equals("")
                && cmbPhoneType.getValue().getValue() != null
                && !txtName.getText().equals("")
                && !txtLastName.getText().equals("")
                && !txtLocationDescription.getText().equals("")
                && !txtLocationURL.getText().equals("")
                && dateBirth.getValue() != null;
    }    

    @Override
    void addTypeAttributes() {
        addAttribute(birthday);
    }

    @Override
    void typeInitialization() {
        initCmbTypo(cmbPhoneType);
        initCmbTypo(cmbLocationType);
        btnReturn.setOnAction(e -> super.returnHomePage());
        btnAdd.setOnAction(e -> super.addContact());
    }

    @Override
    void buildCenterVBox() {
        super.setCenterVBox(this.centerVBox);
    }
}
