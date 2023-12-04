/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany;

import collections.CustomLinkedList;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.attributes.AssociatedContact;
import model.attributes.Attribute;
import model.attributes.ContactImage;
import model.attributes.Email;
import model.attributes.GenericAttribute;
import model.attributes.SocialMedia;
import model.attributes.company.CompanyDescription;
import model.attributes.company.CompanyWebPage;
import model.attributes.location.CompanyLocation;
import model.attributes.location.Location;
import model.attributes.location.PersonLocation;
import model.attributes.names.CompanyName;
import model.attributes.names.Name;
import model.attributes.names.PersonName;
import model.attributes.phone.CompanyPhone;
import model.attributes.phone.PersonPhone;
import model.attributes.phone.PhoneNumber;
import model.attributes.reminders.Birthday;
import model.attributes.reminders.GenericReminder;
import model.util.comparator.ComparatorUtil;
import model.contacts.Contact;
import model.user.MobilePhone;
/**
 * FXML Controller class
 *
 * @author arauj
 */
public class ContactAssociatedController extends Controller implements Initializable {


    @FXML
    private BorderPane root;
    @FXML
    private HBox header;
    @FXML
    private Button btnReturn;
    @FXML
    private VBox vbContent;
    @FXML
    private Label lblName;
    @FXML
    private ImageView imgvPicture;
    @FXML
    private Button btnViewImages;
    
    private Contact c;
    private Contact associated;
    private char type;
    
    
    public ContactAssociatedController(Contact c, Contact associated){
        this.c = c;
        this.associated = associated;
        type = this.associated.getUID().charAt(0);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setScrollPane();
        configureHeader();
        btnReturn.setOnAction(e -> {
            try {
                returnContactPage();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        createHeader();
        showAttributes();
    }    

    private void returnContactPage() throws IOException {
        Controller contactController = new ContactController(c);
        App.setRoot("contact",contactController);
    }
    
    protected void setScrollPane(){
        ScrollPane scrollPane = new ScrollPane();
        root.setCenter(scrollPane);
        scrollPane.setContent(vbContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPannable(true);
    }
    
    protected void configureHeader() {
        header.setAlignment(Pos.CENTER);
        header.getChildren().add(new Label("Contacto asociado de " + c));
        header.setSpacing(10);
    }
    
    private void goContactAssociatedImagesPage() throws IOException {
        App.setRoot(new ContactAssociatedImagesController(c,associated));
    }

    protected void createHeader() {
        lblName.setText(""+getNameContact());
        if (type == 'C'){
            showDescritption();
            showWebPage();
        } else if (type == 'P'){
            showBirthday();
        }
        imgvPicture.setImage(getImageProfile());
        btnViewImages.setOnAction(r -> {
            try {
                goContactAssociatedImagesPage();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    private Name getNameContact(){
        Name name = null;
        if (type == 'C'){
            name = new CompanyName("");
        } else if (type == 'P'){
            name = new PersonName("","");
        }
        CustomLinkedList<Attribute> names = 
                (CustomLinkedList<Attribute>) associated.
                        findAttributes(
                                ComparatorUtil.cmpByAttribute, name);
        return (Name) names.getFirst();
    }
    
    private Image getImageProfile(){
        Image imageProfile = null;
        CustomLinkedList<Attribute> images = 
                (CustomLinkedList<Attribute>)
                associated.findAttributes(
                        ComparatorUtil.cmpByAttribute, new ContactImage());
        ContactImage imgProfile = (ContactImage) images.getFirst();
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
        if (type == 'C'){
            phoneNumber = new CompanyPhone();
        } else if (type == 'P'){
            phoneNumber = new PersonPhone();
        }
        createMiniHeader("Número teléfonico",phoneNumber,phoneNumber.getAttributeName());
        List<Attribute> phoneNumbers = associated.findAttributes(ComparatorUtil.cmpByAttribute, phoneNumber);
        if (!phoneNumbers.isEmpty()) {
            HBox hbox = new HBox();
            hbox.setPrefWidth(440);
            TableView<PhoneNumber> tableView = createTableView(phoneNumber,1);
            ObservableList<PhoneNumber> data = FXCollections.observableArrayList();
            for (Attribute number: phoneNumbers) {
                data.add((PhoneNumber) number);
            }
            tableView.setItems(data);
            setTableView(tableView);
            hbox.getChildren().add(tableView);
            vbContent.getChildren().add(hbox);
        }
        
    }
    
    private <T extends Attribute> TableView<T> createTableView(T attributeInstance, int c) {
        TableView<T> tableView = new TableView<>();
        tableView.setFixedCellSize(40);
        tableView.setPrefWidth(440);
        
        Class<?> currentClass = attributeInstance.getClass();
        
        for (int i = 0; i <= c; i++){
            Field[] fields = currentClass.getDeclaredFields();
            for (Field field: fields) {
                TableColumn<T, String> attributeNameColumn = new TableColumn<>(field.getName());
                attributeNameColumn.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
                attributeNameColumn.setPrefWidth(150);
                tableView.getColumns().add(attributeNameColumn);
            }
            currentClass = currentClass.getSuperclass();
        }
        return tableView;
    }
    
    private TableView<AssociatedContact> createTableViewAssoContact(){
        TableView<AssociatedContact> tableView = createTableView(new AssociatedContact(),0);
        return tableView;
        
    }
    
    
    private void setTableView(TableView tableView){
        int numRows = tableView.getItems().size();
        double tableHeight = numRows * tableView.getFixedCellSize() + tableView.getFixedCellSize();
        tableView.setPrefHeight(tableHeight);
        tableView.setMaxHeight(tableHeight);
        tableView.setMinHeight(tableHeight);
    }

    private void showLocations() {
        Location location = null;
        if (type == 'C'){
            location = new CompanyLocation();
        } else if (type == 'P'){
            location = new PersonLocation();
        }
        createMiniHeader("Dirección",location, location.getAttributeName());
        List<Attribute> locations = associated.findAttributes(ComparatorUtil.cmpByAttribute, location);
        
        if (!locations.isEmpty()) {
            HBox hbox = new HBox();
            hbox.setPrefWidth(440);
            TableView<Location> tableView = createTableView(location,1);
            ObservableList<Location> data = FXCollections.observableArrayList();
            for (Attribute l: locations) {
                data.add((Location) l);
            }
            tableView.setItems(data);
            setTableView(tableView);
            hbox.getChildren().add(tableView);
            vbContent.getChildren().add(hbox);
        }
    }
    
    private void showGenerics() {
        List<Attribute> genericAttributes = associated.findAttributes(ComparatorUtil.cmpByAttribute, new GenericAttribute());
        System.out.println(genericAttributes);
        if (!genericAttributes.isEmpty()) {
            HBox hbox = new HBox();
            hbox.setPrefWidth(440);
            System.out.println("PEPE");
            TableView<GenericAttribute> tableView = createTableView(new GenericAttribute(),0);
            ObservableList<GenericAttribute> data = FXCollections.observableArrayList();
            for (Attribute ga: genericAttributes) {
                data.add((GenericAttribute) ga);
            }
            tableView.setItems(data);
            setTableView(tableView);
            hbox.getChildren().add(tableView);
            vbContent.getChildren().add(hbox);
        }
    }    

    private void showDescritption() {
        CustomLinkedList<Attribute> descriptions = (CustomLinkedList<Attribute>) associated.findAttributes(ComparatorUtil.cmpByAttribute, new CompanyDescription());
        CompanyDescription description = (CompanyDescription) descriptions.getFirst();
        Label descrip = new Label(description.getDescription());
        vbContent.getChildren().add(1, descrip);
    }

    private void showWebPage() {
        CustomLinkedList<Attribute> webPages = (CustomLinkedList<Attribute>) associated.findAttributes(ComparatorUtil.cmpByAttribute, new CompanyWebPage());
        CompanyWebPage webPage = (CompanyWebPage) webPages.getFirst();
        Label title = new Label("Página web: " + webPage.getWebPage());
        vbContent.getChildren().add(2, title);
    }

    private void showReminder() {
        GenericReminder genericReminder = new GenericReminder();
        List<Attribute> reminders = associated.findAttributes(ComparatorUtil.cmpByAttribute, genericReminder);
        createMiniHeader("Recordatorios", genericReminder, genericReminder.getAttributeName());        
        if (!reminders.isEmpty()) {
            HBox hbox = new HBox();
            hbox.setPrefWidth(440);
            TableView<GenericReminder> tableView = createTableView(genericReminder,1);
            ObservableList<GenericReminder> data = FXCollections.observableArrayList();
            for (Attribute r: reminders) {
                data.add((GenericReminder) r);
            }
            tableView.setItems(data);
            setTableView(tableView);
            hbox.getChildren().add(tableView);
            vbContent.getChildren().add(hbox);
        }
    }
    
    private void showBirthday() {
        CustomLinkedList<Attribute> reminders = (CustomLinkedList<Attribute>)
                associated.findAttributes(
                        ComparatorUtil.cmpByAttribute, new Birthday());
        Birthday remind = (Birthday) reminders.getFirst();
        Label birthday = new Label("Cumpleaños: " + remind.getDate());
        vbContent.getChildren().add(1, birthday);
    }
    
    private void showSocialMedia() {
        SocialMedia social = new SocialMedia();
        List<Attribute> socialMedia = associated.findAttributes(ComparatorUtil.cmpByAttribute, social);
        createMiniHeader("Redes sociales", social, social.getAttributeName());
        
        if (!socialMedia.isEmpty()) {
            HBox hbox = new HBox();
            hbox.setPrefWidth(440);
            TableView<SocialMedia> tableView = createTableView(social,0);
            ObservableList<SocialMedia> data = FXCollections.observableArrayList();
            for (Attribute sm: socialMedia) {
                data.add((SocialMedia) sm);
            }
            tableView.setItems(data);
            setTableView(tableView);
            hbox.getChildren().add(tableView);
            vbContent.getChildren().add(hbox);
        }
    }

    private void showEmails() {
        Email email = new Email();
        List<Attribute> emails = associated.findAttributes(ComparatorUtil.cmpByAttribute, email);
        createMiniHeader("Correo electrónico", email, email.getAttributeName());
        
        if (!emails.isEmpty()) {
            HBox hbox = new HBox();
            hbox.setPrefWidth(440);
            TableView<Email> tableView = createTableView(email,0);
            ObservableList<Email> data = FXCollections.observableArrayList();
            for (Attribute e: emails) {
                data.add((Email) e);
            }
            tableView.setItems(data);
            setTableView(tableView);
            hbox.getChildren().add(tableView);
            vbContent.getChildren().add(hbox);
        }
    }

    private void showAssociatedContacts() {
        AssociatedContact asssociatedContact = new AssociatedContact();
        List<Attribute> associatedContacts = associated.findAttributes(ComparatorUtil.cmpByAttribute, asssociatedContact);
        createMiniHeader("Contactos Asociados", asssociatedContact, asssociatedContact.getAttributeName());
        
        if (!associatedContacts.isEmpty()) {
            HBox hbox = new HBox();
            hbox.setPrefWidth(440);
            TableView<AssociatedContact> tableView = createTableViewAssoContact();
            ObservableList<AssociatedContact> data = FXCollections.observableArrayList();
            for (Attribute ac: associatedContacts) {
                data.add((AssociatedContact) ac);
            }
            tableView.setItems(data);
            setTableView(tableView);
            hbox.getChildren().add(tableView);
            vbContent.getChildren().add(hbox);
        }
    }
    
    private void createMiniHeader(String title, Attribute att, String className){
        HBox header = new HBox();
        header.setSpacing(50);
        header.setAlignment(Pos.CENTER);
        Label title1 = new Label(title);
        header.getChildren().addAll(title1);
        vbContent.getChildren().add(header);
    }
    
    protected void showAttributes() {
        showNumbers();
        showLocations();
        showSocialMedia();
        showEmails();
        showAssociatedContacts();
        showReminder();
        showGenerics();
    } 
    
}
