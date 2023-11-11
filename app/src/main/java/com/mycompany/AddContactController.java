package com.mycompany;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Pair;
import model.attributes.Attribute;
import model.attributes.Location;
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
    private TextField txtImageSource;
    @FXML
    private DatePicker dateBirth;
    @FXML
    private VBox boxImages;

    private Birthday birthday;
    private final static Pair<String, SourceType> EMPTY_TYPES_PAIR =
            new Pair<>("", null);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();
        initCmbTypo(cmbPhoneType);
        initCmbTypo(cmbLocationType);
    }    
    
    @FXML
    void openImages(ActionEvent event){
        fileDialog.setTitle("Abrir imagenes");
        imageList = fileDialog.showOpenMultipleDialog(App.stage);
        if(imageList != null) {
            pagination.setPageCount(imageList.size());
            txtImageSource.setText(imageList.get(0).getParent());
        }
    }
    
    @FXML
    protected void returnHomePage(){
        super.returnHomePage();
    }
    
    @Override
    void initImageSourceText(){
        txtImageSource.setEditable(false);
        txtImageSource.setFocusTraversable(false);
    }
    
    
    private void initCmbTypo(ComboBox cmbTypos) {
        List<Pair<String,SourceType>> types = new ArrayList<>();

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
        contact.attributes.add(new PersonName(
        txtName.getText(), 
        txtLastName.getText()));
    }
    
    @Override
    void loadLocation(){
        // handling location and birthday contact info
        location = new Location(
                cmbLocationType.getValue().getValue(),
                txtLocationDescription.getText(),
                txtLocationURL.getText());    
    }
    
    private void loadBirthday(){
        birthday = new Birthday(dateBirth.getValue());
    }
    
    @Override
    void loadInfo(){
        super.loadInfo();
        loadBirthday();
    }

    @Override
    void addAttributes(){
        super.addAttributes();
        List<Attribute> attributes = contact.attributes;
        attributes.add(birthday);
        
    }
    
    @Override
    void addPagination(){
        boxImages.getChildren().add(pagination);
    }

    @FXML
    private void addPerson(ActionEvent event){
        super.addContact();
    }

    @Override
    boolean isPrepared(){
        return !txtPhoneNumber.getText().equals("")
                && cmbPhoneType.getValue().getValue() != null
                && !txtName.getText().equals("")
                && !txtLastName.getText().equals("")
                && !txtLocationDescription.getText().equals("")
                && !txtLocationURL.getText().equals("")
                && dateBirth.getValue() != null
                && imageList != null
                ;
    }    

}
