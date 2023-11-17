/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
    
    
    public ContactController(){}

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
            App.setRoot("addAtribute");
        });
        createHeader();
        showAttributes();
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
        System.out.println(path);
        try {
            imageProfile = new Image(new FileInputStream(path));
        } catch (Exception e) {
            System.out.println("No se encontró la ruta de la imagen");
            //imageProfile = new Image(new FileInputStream("images/default.png"));
        }
        return imageProfile;
    }

    private void showNumbers() {
        createMiniHeader("Número teléfonico");
        PhoneNumber phoneNumber = null;
        if (contactType == 'C'){
            phoneNumber = new CompanyPhone();
        } else if (contactType == 'P'){
            phoneNumber = new PersonPhone();
        }
        List<Attribute> phoneNumbers = contact.findAttributes(ComparatorUtil.cmpByAttribute, phoneNumber);
        VBox vbPhoneNumbers = new VBox();
        for(Attribute phonNumber: phoneNumbers){
            HBox content = new HBox();
            PhoneNumber phone = (PhoneNumber) phonNumber;
            if(phone instanceof PersonPhone){
                PersonPhone personPhone = (PersonPhone) phone;
                Label type = new Label(String.valueOf(personPhone.getPhoneType()));
                content.getChildren().add(type);
            }
            Label number = new Label(phone.getPhoneNumber());
            setHBox(content, new ArrayList<>(Arrays.asList(number)));
            createButtons(content);
            vbPhoneNumbers.getChildren().add(content);
        }
        vbContent.getChildren().add(vbPhoneNumbers);
    }

    private void showLocations() {
        createMiniHeader("Dirección");
        Location location = null;
        if (contactType == 'C'){
            location = new CompanyLocation();
        } else if (contactType == 'P'){
            location = new PersonLocation();
        }
        List<Attribute> locations = contact.findAttributes(ComparatorUtil.cmpByAttribute, location);
        VBox vbLocations = new VBox();
        for(Attribute locate: locations){
            HBox content = new HBox();
            Location loc = (Location) locate;
            if(loc instanceof PersonLocation){
                PersonLocation personLocation = (PersonLocation) loc;
                System.out.println(String.valueOf(personLocation.getLocationType()));
                Label type = new Label(String.valueOf(personLocation.getLocationType()));
                content.getChildren().add(type);
            }
            Label details = new Label(loc.getDetails());
            Label mapsUrl = new Label(loc.getMapsURL());
            setHBox(content, new ArrayList<>(Arrays.asList(details,mapsUrl)));
            createButtons(content);
            vbLocations.getChildren().add(content);
        }
        vbContent.getChildren().add(vbLocations);
    }
    
    private void showGenerics() {
        List<Attribute> genericAttributes = contact.findAttributes(ComparatorUtil.cmpByAttribute, new GenericAttribute());
        VBox vbGenerics = new VBox();
        for(Attribute genericAttribute: genericAttributes){
            HBox content = new HBox();
            GenericAttribute genericAtt = (GenericAttribute) genericAttribute;
            Label name = new Label(genericAtt.getAttributeName());
            Label value = new Label(""+genericAtt.getValue());
            setHBox(content, new ArrayList<>(Arrays.asList(name,value)));
            createButtons(content);
            vbGenerics.getChildren().add(content);
        }
        vbContent.getChildren().add(vbGenerics);
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
        List<Attribute> reminders = contact.findAttributes(ComparatorUtil.cmpByAttribute, new GenericReminder());
        createMiniHeader("Recordatorios");
        VBox vbReminder = new VBox();
        for(Attribute reminder: reminders){
            HBox content = new HBox();
            GenericReminder remind = (GenericReminder) reminder;
            Label name = new Label(remind.getDescription());
            Label date = new Label(""+remind.getDate());
            setHBox(content, new ArrayList<>(Arrays.asList(name,date)));
            createButtons(content);
            vbReminder.getChildren().addAll(content);
        }
        vbContent.getChildren().add(vbReminder);
    }
    
    /*private void showBirthday() {
        List<Attribute> reminders = contact.findAttributes(ComparatorUtil.cmpByAttribute, new Birthday());
        VBox vbBirthday = new VBox();
        for(Attribute reminder: reminders){
            HBox content = new HBox();
            Birthday remind = (Birthday) reminder;
            Label name = new Label("Birthday");
            Label date = new Label(""+remind.getDate());
            Button btnEdit = new Button("Editar");
            Button btnDelete = new Button("Eliminar");
            content.getChildren().addAll(name, date, btnEdit, btnDelete);
            content.setSpacing(20);
            content.setAlignment(Pos.CENTER);
            vbBirthday.getChildren().addAll(content);
        }
        vbContent.getChildren().add(vbBirthday);
    }*/
    
    private void showBirthday() {
        List<Attribute> reminders = contact.findAttributes(ComparatorUtil.cmpByAttribute, new Birthday());
        Birthday remind = (Birthday) reminders.get(0);
        Label birthday = new Label("Cumpleaños: " + remind.getDate());
        vbContent.getChildren().add(1, birthday);
    }
    
    private void showSocialMedia() {
        List<Attribute> socialMedia = contact.findAttributes(ComparatorUtil.cmpByAttribute, new SocialMedia());
        createMiniHeader("Redes sociales");
        VBox vbSocialMedia = new VBox();
        for(Attribute social: socialMedia){
            HBox content = new HBox();
            SocialMedia socMed = (SocialMedia) social;
            Label type = new Label(""+socMed.getSocialMedia());
            Label user = new Label(""+socMed.getUser());
            setHBox(content, new ArrayList<>(Arrays.asList(type,user)));
            createButtons(content);
            vbSocialMedia.getChildren().addAll(content);
        }
        if (contactType == 'C') vbContent.getChildren().add(5,vbSocialMedia);
        else if (contactType == 'P') vbContent.getChildren().add(4,vbSocialMedia);
    }

    private void showEmails() {
        List<Attribute> emails = contact.findAttributes(ComparatorUtil.cmpByAttribute, new Email());
        createMiniHeader("Correo electrónico");
        VBox vbEmails = new VBox();
        for(Attribute email: emails){
            HBox content = new HBox();
            Email mail = (Email) email;
            Label type = new Label(""+mail.getEmailType());
            Label user = new Label(""+mail.getEmail());
            setHBox(content, new ArrayList<>(Arrays.asList(type,user)));
            createButtons(content);
            vbEmails.getChildren().add(content);
        }
        if (contactType == 'C') vbContent.getChildren().add(7,vbEmails);
        else if (contactType == 'P') vbContent.getChildren().add(6,vbEmails);
    }

    private void showAssociatedContacts() {
        List<Attribute> associatedContacts = contact.findAttributes(ComparatorUtil.cmpByAttribute, new AssociatedContact());
        createMiniHeader("Contactos Asociados");
        VBox vbAssociated = new VBox();
        for(Attribute associatedContact: associatedContacts){
            HBox content = new HBox();
            AssociatedContact associated = (AssociatedContact) associatedContact;
            Label type = new Label(""+associated.getRelation());
            Label contact1 = new Label(""+associated.getContact());
            setHBox(content, new ArrayList<>(Arrays.asList(type,contact1)));
            createButtons(content);
            vbAssociated.getChildren().add(content);
        }
        vbContent.getChildren().add(vbAssociated);
    }
    
    private void createMiniHeader(String title){
        HBox header = new HBox();
        header.setSpacing(50);
        header.setAlignment(Pos.CENTER);
        Label title1 = new Label(title);
        Button btnAdd = new Button("Agregar");
        if(title.equals("Recordatorios")){
            btnAdd.setOnAction(r -> {
                App.setRoot("addReminder");
            });
        } else {
            btnAdd.setOnAction(r -> {
                App.setRoot("addPresetAttribute");
            });
        }
        header.getChildren().addAll(title1,btnAdd);
        vbContent.getChildren().add(header);
    }
    
    private void createButtons(HBox hbox){
        Button btnEdit = new Button("Editar");
        btnEdit.setOnAction(r -> {
            App.setRoot("editPresetAtribute");
        });
        Button btnDelete = new Button("Eliminar");
        hbox.getChildren().addAll(btnEdit, btnDelete);
    }
    
    private void setHBox(HBox hbox, Collection<? extends Node> c){
        hbox.getChildren().addAll(c);
        hbox.setSpacing(20);
        hbox.setAlignment(Pos.CENTER);
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
