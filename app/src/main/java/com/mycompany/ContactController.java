/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.attributes.*;
import model.attributes.company.*;
import model.attributes.location.*;
import model.attributes.names.*;
import model.attributes.phone.*;
import model.attributes.reminders.*;
import model.comparator.ComparatorUtil;
import model.contacts.*;
import model.enums.SourceType;
import model.user.MobilePhone;

/**
 * FXML Controller class
 *
 * @author arauj
 */
public class ContactController extends Controller implements Initializable {

    @FXML
    private Button btnReturn;
    @FXML
    private CheckBox cboxFavorite;
    @FXML
    private Button btnDeleteContact;
    @FXML
    private VBox vbContent;
    @FXML
    private Label lblName;
    @FXML
    private ImageView imgvPicture;
    @FXML
    private Button btnViewImages;
    @FXML
    private BorderPane root;
    @FXML
    private Button btnAddGeneric;
    
    private Contact contact;
    private char contactType;
    

    public ContactController(Contact selectedContact) {
        contact = selectedContact;
        contactType = contact.getUID().charAt(0);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnReturn.setOnAction(r -> {
            try {
                returnContactListPage();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        btnDeleteContact.setOnAction(r -> {
            MobilePhone.removeContact(contact);
            try {
                returnContactListPage();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        btnAddGeneric.setOnAction(r -> {
            try {
                goAddAttributePage(contact);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        createHeader();
        showAttributes();
    }
    
    private void goAddAttributePage(Contact selectedContact) throws IOException {
        Controller addAtributeController = new AddAtributeController(contact);
        App.setRoot("addAtribute",addAtributeController);
    }
        
    private void returnContactListPage() throws IOException{
        App.setRoot("contactList");
    }
    
    private void goContactImagesPage(Contact contact) throws IOException {
        Controller contactImagesController = new ContactImagesController(contact);
        App.setRoot("contactImages",contactImagesController);
    }

    private void createHeader() {
        lblName.setText(""+getNameContact());
        if (contactType == 'C'){
            showDescritption();
            showWebPage();
        } else if (contactType == 'P'){
            showBirthday();
        }
        imgvPicture.setImage(getImageProfile());
        btnViewImages.setOnAction(r -> {
            try {
                goContactImagesPage(contact);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    private Name getNameContact(){
        Name name = null;
        if (contactType == 'C'){
            name = new CompanyName("");
        } else if (contactType == 'P'){
            name = new PersonName("","");
        }
        List<Attribute> names = contact.findAttributes(ComparatorUtil.cmpByAttribute, name);
        return (Name) names.get(0);
    }
    
    private Image getImageProfile(){
        Image imageProfile = null;
        List<Attribute> images = contact.findAttributes(ComparatorUtil.cmpByAttribute, new ContactImage());
        ContactImage imgProfile = (ContactImage) images.get(0);
        String path = imgProfile.getPath();
        try {
            imageProfile = new Image(new FileInputStream(path));
        } catch (Exception e) {
            System.out.println("No se encontró la ruta de la imagen");
        }
        return imageProfile;
    }

    private void showNumbers() {
        PhoneNumber phoneNumber = null;
        if (contactType == 'C'){
            phoneNumber = new CompanyPhone();
        } else if (contactType == 'P'){
            phoneNumber = new PersonPhone();
        }
        createMiniHeader("Número teléfonico",phoneNumber.getAttributeName());
        List<Attribute> phoneNumbers = contact.findAttributes(ComparatorUtil.cmpByAttribute, phoneNumber);
        if (!phoneNumbers.isEmpty()) {
            TableView<PhoneNumber> tableView = createTableView(phoneNumber,1);
            ObservableList<PhoneNumber> data = FXCollections.observableArrayList();
            for (Attribute number: phoneNumbers) {
                data.add((PhoneNumber) number);
            }
            tableView.setItems(data);
            vbContent.getChildren().add(tableView);
        }
        
    }
    
    private <T extends Attribute> TableView<T> createTableView(T attributeInstance, int c) {
        TableView<T> tableView = new TableView<>();
        
        Class<?> currentClass = attributeInstance.getClass();
        
        for (int i = 0; i <= c; i++){
            Field[] fields = currentClass.getDeclaredFields();

            System.out.println(fields.length);
            for (Field field: fields) {
                TableColumn<T, String> attributeNameColumn = new TableColumn<>(field.getName());
                System.out.println(field.getName());
                attributeNameColumn.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
                tableView.getColumns().add(attributeNameColumn);
            }
            currentClass = currentClass.getSuperclass();
        }
        TableColumn<T, Void> editColumn = new TableColumn<>("Opciones");
        Callback<TableColumn<T, Void>, TableCell<T, Void>> cellFactory = new Callback<TableColumn<T, Void>, TableCell<T, Void>>() {
            @Override
            public TableCell<T, Void> call(final TableColumn<T, Void> param) {
                TableCell<T, Void> cell = new TableCell<T, Void>() {

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbOpciones = new HBox(5);
                            T att = getTableView().getItems().get(getIndex());
                            createButtons(hbOpciones, att);
                            setGraphic(hbOpciones);
                        }
                    }
                };
                return cell;
            }
        };
        editColumn.setCellFactory(cellFactory);
        tableView.getColumns().add(editColumn);
        return tableView;
    }

    private void showLocations() {
        Location location = null;
        if (contactType == 'C'){
            location = new CompanyLocation();
        } else if (contactType == 'P'){
            location = new PersonLocation();
        }
        createMiniHeader("Dirección",location.getAttributeName());
        List<Attribute> locations = contact.findAttributes(ComparatorUtil.cmpByAttribute, location);
        
        if (!locations.isEmpty()) {
            TableView<Location> tableView = createTableView(location,1);
            ObservableList<Location> data = FXCollections.observableArrayList();
            for (Attribute l: locations) {
                data.add((Location) l);
            }
            tableView.setItems(data);
            vbContent.getChildren().add(tableView);
        }
    }
    
    private void showGenerics() {
        List<Attribute> genericAttributes = contact.findAttributes(ComparatorUtil.cmpByAttribute, new GenericAttribute());
        
        if (!genericAttributes.isEmpty()) {
            TableView<GenericAttribute> tableView = createTableView(new GenericAttribute(),0);
            ObservableList<GenericAttribute> data = FXCollections.observableArrayList();
            for (Attribute ga: genericAttributes) {
                data.add((GenericAttribute) ga);
            }
            tableView.setItems(data);
            vbContent.getChildren().add(tableView);
        }
    }    

    private void showDescritption() {
        List<Attribute> descriptions = contact.findAttributes(ComparatorUtil.cmpByAttribute, new CompanyDescription());
        CompanyDescription description = (CompanyDescription) descriptions.get(0);
        Label descrip = new Label(description.getDescription());
        vbContent.getChildren().add(1, descrip);
    }

    private void showWebPage() {
        List<Attribute> webPages = contact.findAttributes(ComparatorUtil.cmpByAttribute, new CompanyWebPage());
        CompanyWebPage webPage = (CompanyWebPage) webPages.get(0);
        Label title = new Label("Página web: " + webPage.getWebPage());
        vbContent.getChildren().add(2, title);
    }

    private void showReminder() {
        GenericReminder genericReminder = new GenericReminder();
        List<Attribute> reminders = contact.findAttributes(ComparatorUtil.cmpByAttribute, genericReminder);
        createMiniHeader("Recordatorios", genericReminder.getAttributeName());        
        if (!reminders.isEmpty()) {
            TableView<GenericReminder> tableView = createTableView(genericReminder,1);
            ObservableList<GenericReminder> data = FXCollections.observableArrayList();
            for (Attribute r: reminders) {
                data.add((GenericReminder) r);
            }
            tableView.setItems(data);
            vbContent.getChildren().add(tableView);
        }
    }
    
    private void showBirthday() {
        List<Attribute> reminders = contact.findAttributes(ComparatorUtil.cmpByAttribute, new Birthday());
        Birthday remind = (Birthday) reminders.get(0);
        Label birthday = new Label("Cumpleaños: " + remind.getDate());
        vbContent.getChildren().add(1, birthday);
    }
    
    private void showSocialMedia() {
        SocialMedia social = new SocialMedia();
        List<Attribute> socialMedia = contact.findAttributes(ComparatorUtil.cmpByAttribute, social);
        createMiniHeader("Redes sociales",social.getAttributeName());
        
        if (!socialMedia.isEmpty()) {
            TableView<SocialMedia> tableView = createTableView(social,0);
            ObservableList<SocialMedia> data = FXCollections.observableArrayList();
            for (Attribute sm: socialMedia) {
                data.add((SocialMedia) sm);
            }
            tableView.setItems(data);
            if (contactType == 'C') vbContent.getChildren().add(tableView);
            else if (contactType == 'P') vbContent.getChildren().add(tableView);
            //vbContent.getChildren().add(tableView);
        }
    }

    private void showEmails() {
        Email email = new Email();
        List<Attribute> emails = contact.findAttributes(ComparatorUtil.cmpByAttribute, email);
        createMiniHeader("Correo electrónico", email.getAttributeName());
        
        if (!emails.isEmpty()) {
            TableView<Email> tableView = createTableView(email,0);
            ObservableList<Email> data = FXCollections.observableArrayList();
            for (Attribute e: emails) {
                data.add((Email) e);
            }
            tableView.setItems(data);
            if (contactType == 'C') vbContent.getChildren().add(7,tableView);
            else if (contactType == 'P') vbContent.getChildren().add(6,tableView);
            //vbContent.getChildren().add(tableView);
        }
    }

    private void showAssociatedContacts() {
        AssociatedContact asssociatedContact = new AssociatedContact();
        List<Attribute> associatedContacts = contact.findAttributes(ComparatorUtil.cmpByAttribute, asssociatedContact);
        createMiniHeader("Contactos Asociados",asssociatedContact.getAttributeName());
        
        if (!associatedContacts.isEmpty()) {
            TableView<AssociatedContact> tableView = createTableView(asssociatedContact,1);
            ObservableList<AssociatedContact> data = FXCollections.observableArrayList();
            for (Attribute ac: associatedContacts) {
                data.add((AssociatedContact) ac);
            }
            tableView.setItems(data);
            vbContent.getChildren().add(tableView);
        }
    }
    
    private void createMiniHeader(String title, String className){
        HBox header = new HBox();
        header.setSpacing(50);
        header.setAlignment(Pos.CENTER);
        Label title1 = new Label(title);
        Button btnAdd = new Button("Agregar");
        if(title.equals("Recordatorios")){
            btnAdd.setOnAction(r -> {
                try {
                    goAddReminderPage(contact);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        } else {
            btnAdd.setOnAction(r -> {
                try {
                    goAddPresetAttributePage(contact, className);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        header.getChildren().addAll(title1,btnAdd);
        vbContent.getChildren().add(header);
    }
    
    private <T extends Attribute> void createButtons(HBox hbox, T att){
        Button btnEdit = new Button("Editar");
        btnEdit.setOnAction(r -> {
            try {
                goEditPresetAttributePage(contact, att,contact.getClass().toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        Button btnDelete = new Button("Eliminar");
        btnDelete.setOnAction(r -> {
            deleteAttribute(att);
        });
        hbox.getChildren().addAll(btnEdit, btnDelete);
    }
    
    private void goEditPresetAttributePage(Contact selectedContact, Attribute att,String c) throws IOException {
        Controller editPresetAtributeController = new EditPresetAtributeController(selectedContact, att,c);
        App.setRoot("editPresetAtribute",editPresetAtributeController);
    }
    
    
    private void goAddPresetAttributePage(Contact selectedContact, String className) throws IOException {
        Controller addPresetAtributeController = new AddPresetAtributeController(selectedContact, className);
        App.setRoot("addPresetAtribute",addPresetAtributeController);
    }
    
    private void goAddReminderPage(Contact selectedContact) throws IOException {
        Controller addReminderController = new AddReminderController(selectedContact);
        App.setRoot("addReminder",addReminderController);
    }
    
    private <T extends Attribute> void deleteAttribute(T att) {
        /*Comparator<Attribute> c = new Comparator<>() {

            @Override
            public int compare(Attribute o1, Attribute o2) {
                
            }
        };*/
    }
    
    private void showAttributes() {
        showNumbers();
        showLocations();
        showSocialMedia();
        showEmails();
        showAssociatedContacts();
        showReminder();
        showGenerics();
    }

    

    
    
    
    
}
