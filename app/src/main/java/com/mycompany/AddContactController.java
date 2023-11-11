/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import javafx.util.Pair;
import model.attributes.Attribute;
import model.attributes.Image;
import model.attributes.Location;
import model.attributes.names.PersonName;
import model.attributes.PhoneNumber;
import model.attributes.reminders.Birthday;
import model.contacts.Person;
import model.enums.SourceType;
import model.user.MobilePhone;

public class AddContactController implements Initializable {

    @FXML
    private Label lblContact;
    @FXML
    private ComboBox<Pair<String, String>> cmbContactType;
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
    
    private final static Pair<String, String> EMPTY_PAIR = 
            new Pair<>("", "");
    private final static Pair<String, SourceType> EMPTY_TYPES_PAIR =
            new Pair<>("", null);
    
    private FileChooser fileDialog;
    private List<File> imageList;
    private Pagination pagination;
    
    
    PhoneNumber personPhone;
    Location location;
    List<Image> images;
    Birthday birthday;
    Person person;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblContact.setText("Agregar Persona");
        initCmbContactType();
        initCmbTypo(cmbPhoneType);
        initCmbTypo(cmbLocationType);
        initPagination();
        initImageSourceText();
        initFileDialog();
        boxImages.getChildren().add(pagination);
    }    
    
    @FXML
    private void openImages(ActionEvent event){
        fileDialog.setTitle("Abrir imagenes");
        imageList = fileDialog.showOpenMultipleDialog(App.stage);
        if(imageList != null) {
            pagination.setPageCount(imageList.size());
            txtImageSource.setText(imageList.get(0).getParent());
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
    
    @FXML
    private void afterSelection(ActionEvent event) throws IOException {
        String fxml = cmbContactType.getValue().getValue();
        App.setRoot(fxml);
    }
    
    private void initPagination(){
        pagination = new Pagination();
        pagination.setPageFactory(index -> {
        if (imageList != null && index < imageList.size()) {

            String url = imageList.get(index).toURI().toString();

            ImageView imageView = new ImageView(url);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(400);
            imageView.setFitWidth(600);

            return imageView;

        } else {
            return new Label("No hay imágenes seleccionadas");
        }
        });
    }
    
    private void initFileDialog(){
        fileDialog = new FileChooser();
        fileDialog.getExtensionFilters()
                .add(new ExtensionFilter("Imagen", "*.jpg", "*.png", "*.bmp", "*.gif"));
    }
    
    private void initImageSourceText(){
        txtImageSource.setEditable(false);
        txtImageSource.setFocusTraversable(false);
    }
    
    private void initCmbContactType() {

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
    
    private boolean isPrepared(){
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
    
    private void loadPhone(){
        // create attribute instances
        personPhone = new PhoneNumber(
        txtPhoneNumber.getText(),
        cmbPhoneType.getValue().getValue());
    }
    
    private void loadContact(){
        // create person contact
        person = new Person(personPhone);
        person.attributes.add(new PersonName(
        txtName.getText(), 
        txtLastName.getText()));
    }
    
    private void loadLocation(){
        // handling location and birthday contact info
        location = new Location(
        txtLocationDescription.getText(),
                txtLocationURL.getText()
        );    
    }
    
    private void loadImages(){
        // handling image attribute
        images = new ArrayList<>();
        for (File file: imageList){
            try{
                Image newImage = new Image();
                String source = "images/" + person.getRefId() + "/";
                Files.createDirectories(Paths.get(source));
                String targetPath = source + newImage.getRefId() + ".jpg";
                
                File target = new File(targetPath);
                //get Url and open stream
                String urlString = file.toURI().toString();
                InputStream inputStream = new URL(urlString).openStream();

                //copy bytes from the stream to the target file
                Files.copy(inputStream, 
                        target.toPath(), 
                        StandardCopyOption.REPLACE_EXISTING);
                newImage.setPath(targetPath);
                images.add(newImage);
            } catch (Exception x){
                System.err.println("Failed to save Image");
                x.printStackTrace();
            }   
        }     
    }
    
    private void loadBirthday(){
        birthday = new Birthday(dateBirth.getValue());
    }
    
    private void loadInfo(){
        loadPhone();
        loadContact();
        loadLocation();
        loadImages();
        loadBirthday();
    }

    private void addAttributes(){
        List<Attribute> attributes = person.attributes;
        attributes.add(location);
        attributes.add(birthday);
        attributes.addAll(images);
    }
    
    private void noDataAlert(){
        Alert a = new Alert(AlertType.NONE);

        a.setAlertType(AlertType.ERROR);
        a.setContentText("Faltan datos para agregar contacto");
        a.show();
    }
    
    private void sucessDialog(){
        Alert a = new Alert(AlertType.NONE);
        
        a.setAlertType(AlertType.INFORMATION);
        a.setContentText("Contacto tipo persona agregado con éxito");
        a.show();
    }
    
    private void saveContact(){
        try {
            MobilePhone.addContact(person);
        } catch (IOException ex) {
            System.err.println("Failed to save contact");
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void addPerson(ActionEvent event){
        if (isPrepared()){
            loadInfo();
            addAttributes();
            saveContact();
            returnHomePage();
            sucessDialog();
        } else{
            noDataAlert();
        }
    }
    
    
        

}
