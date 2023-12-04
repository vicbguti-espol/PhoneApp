package com.mycompany;

import collections.CustomLinkedList;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.attributes.*;
import model.attributes.company.*;
import model.attributes.location.*;
import model.attributes.names.*;
import model.attributes.phone.*;
import model.attributes.reminders.*;
import model.util.comparator.ComparatorUtil;
import model.contacts.*;
import model.user.MobilePhone;

public class ContactController extends DataEntryController implements Initializable {

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
    private Button btnAddImages;
    @FXML
    private BorderPane root;
    @FXML
    private Button btnAddGeneric;
    @FXML
    private HBox header;
    @FXML
    private Button btnEditContact;
    
    
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

        setScrollPane();
        configureHeader();
        cboxFavorite.setStyle("-fx-text-fill: white;");
        configureButtons();
        createHeader();
        showAttributes();
         cboxFavorite.setOnAction(e -> {
            new Thread(() -> {
                Platform.runLater(() -> {
                    
                    MobilePhone.addContactFavorito(contact);
                });
            }).start();
        });
    }
    
    protected void setScrollPane(){
        ScrollPane scrollPane = new ScrollPane();
        root.setCenter(scrollPane);
        scrollPane.setContent(vbContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPannable(true);
    }
    
    private void configureButtons(){
        configureReturn();
        configureDeleteContact();
        configureAddGeneric();
        configureEditContact();
        configureAddImages();
    }
    
    private void configureAddImages(){
        btnAddImages.setOnAction(e -> goAddImagesPage());
    }
    
    private void goAddImagesPage(){
        Controller addImagesController = new AddImagesController(contact);
        try {
            App.setRoot("addImages", addImagesController);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void configureEditContact(){
        if (contactType == 'C'){
            btnEditContact.setOnAction(e -> goEditCompanyPage());
        } else if (contactType == 'P') {
            btnEditContact.setOnAction(e -> goEditContactPage());
        }
    }
    
    private void goEditCompanyPage(){
        Controller editCompanyController = new EditCompanyController(contact);
        try {
            App.setRoot("editCompany", editCompanyController);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void goEditContactPage(){
        Controller editContactController = new EditContactController(contact);
        try {
            App.setRoot("editContact", editContactController);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void configureReturn(){
        btnReturn.setOnAction(r -> {
            super.returnHomePage();
        });
    }
    
    private void configureDeleteContact(){
        btnDeleteContact.setOnAction(r -> {
            if (super.confirmationAlert("contacto")){
                MobilePhone.removeContact(contact);
                super.returnHomePage();
            }            
        });
    }
    
    private void configureAddGeneric(){
        btnAddGeneric.setOnAction(r -> {
            try {
                goAddAttributePage();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    protected void configureHeader() {
        header.setSpacing(40);
        header.setAlignment(Pos.CENTER);
    }
    
    private void goAddAttributePage() throws IOException {
        Controller addAtributeController = new AddAtributeController(contact);
        App.setRoot("addAtribute",addAtributeController);
    }
    
    private void goContactImagesPage() throws IOException {
        App.setRoot(new ContactImagesController(contact));
        // App.setRoot("contactImages",contactImagesController);
    }

    protected void createHeader() {
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
                goContactImagesPage();
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
        CustomLinkedList<Attribute> names = 
                (CustomLinkedList<Attribute>) contact.
                        findAttributes(
                                ComparatorUtil.cmpByAttribute, name);
        return (Name) names.getFirst();
    }
    
    private Image getImageProfile(){
        Image imageProfile = null;
        CustomLinkedList<Attribute> images = 
                (CustomLinkedList<Attribute>)
                contact.findAttributes(
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
        if (contactType == 'C'){
            phoneNumber = new CompanyPhone();
        } else if (contactType == 'P'){
            phoneNumber = new PersonPhone();
        }
        createMiniHeader("Número teléfonico",phoneNumber,phoneNumber.getAttributeName());
        List<Attribute> phoneNumbers = contact.findAttributes(ComparatorUtil.cmpByAttribute, phoneNumber);
        if (!phoneNumbers.isEmpty()) {
            HBox hbox = new HBox();
            hbox.setPrefWidth(440);
            TableView<PhoneNumber> tableView = createTableView(phoneNumber,1);
            CustomLinkedList<PhoneNumber> data = new CustomLinkedList<>();
            for (Attribute number : phoneNumbers) {
                data.add((PhoneNumber) number);
            }
            ObservableList<PhoneNumber> observableData = FXCollections.observableList(data);
            tableView.setItems(observableData);
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
                if (field.getName().equals("description")){
                    //mostrar descripcion/nombre generico
                }else {
                    attributeNameColumn.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
                }
                attributeNameColumn.setPrefWidth(150);
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
    
    private TableView<AssociatedContact> createTableViewAssoContact(){
        
        TableView<AssociatedContact> tableView = createTableView(new AssociatedContact(),0);
        tableView.getColumns().remove(tableView.getColumns().size()-1);
        TableColumn<AssociatedContact, Void> editColumn = new TableColumn<>("Opciones");
        Callback<TableColumn<AssociatedContact, Void>, TableCell<AssociatedContact, Void>> cellFactory = new Callback<TableColumn<AssociatedContact, Void>, TableCell<AssociatedContact, Void>>() {
            @Override
            public TableCell<AssociatedContact, Void> call(final TableColumn<AssociatedContact, Void> param) {
                TableCell<AssociatedContact, Void> cell = new TableCell<AssociatedContact, Void>() {
                    
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbOpciones = new HBox(5);
                            AssociatedContact att = getTableView().getItems().get(getIndex());
                            createLink(hbOpciones,att);
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
    
    private void createLink(HBox hbox, AssociatedContact att){
        Button btnLink = new Button("Ver contacto");
        btnLink.setOnAction(r -> {
            try {
                goAssociatedContactPage(att);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        hbox.getChildren().addAll(btnLink);
    }
    
    private void goAssociatedContactPage(AssociatedContact att) throws IOException {
        Controller contactAssociatedController = new ContactAssociatedController(contact,att.getContact());
        App.setRoot("contactAssociated",contactAssociatedController);
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
        if (contactType == 'C'){
            location = new CompanyLocation();
        } else if (contactType == 'P'){
            location = new PersonLocation();
        }
        createMiniHeader("Dirección",location, location.getAttributeName());
        List<Attribute> locations = contact.findAttributes(ComparatorUtil.cmpByAttribute, location);
        
        if (!locations.isEmpty()) {
            HBox hbox = new HBox();
            hbox.setPrefWidth(440);
            TableView<Location> tableView = createTableView(location,1);
            CustomLinkedList<Location> data = new CustomLinkedList<>();
            for (Attribute l : locations) {
                data.add((Location) l);
            }
            ObservableList<Location> observableData = FXCollections.observableList(data);
            tableView.setItems(observableData);
            setTableView(tableView);
            hbox.getChildren().add(tableView);
            vbContent.getChildren().add(hbox);
        }
    }
    
    private void showGenerics() {
        HBox header = new HBox();
        header.setSpacing(50);
        header.setAlignment(Pos.CENTER);
        Label title1 = new Label("Genericos");
        header.getChildren().addAll(title1);
        vbContent.getChildren().add(header);
        List<Attribute> genericAttributes = contact.findAttributes(ComparatorUtil.cmpByAttribute, new GenericAttribute());
        if (!genericAttributes.isEmpty()) {
            HBox hbox = new HBox();
            hbox.setPrefWidth(440);
            TableView<GenericAttribute> tableView = createTableView(new GenericAttribute(),0);
            CustomLinkedList<GenericAttribute> data = new CustomLinkedList<>();
            for (Attribute ga : genericAttributes) {
                data.add((GenericAttribute) ga);
            }
            ObservableList<GenericAttribute> observableData = FXCollections.observableList(data);
            tableView.setItems(observableData);
            setTableView(tableView);
            hbox.getChildren().add(tableView);
            vbContent.getChildren().add(hbox);
        }
    }    

    private void showDescritption() {
        CustomLinkedList<Attribute> descriptions = (CustomLinkedList<Attribute>) contact.findAttributes(ComparatorUtil.cmpByAttribute, new CompanyDescription());
        CompanyDescription description = (CompanyDescription) descriptions.getFirst();
        Label descrip = new Label(description.getDescription());
        vbContent.getChildren().add(1, descrip);
    }

    private void showWebPage() {
        CustomLinkedList<Attribute> webPages = (CustomLinkedList<Attribute>) contact.findAttributes(ComparatorUtil.cmpByAttribute, new CompanyWebPage());
        CompanyWebPage webPage = (CompanyWebPage) webPages.getFirst();
        Label title = new Label("Página web: " + webPage.getWebPage());
        vbContent.getChildren().add(2, title);
    }

    private void showReminder() {
        GenericReminder genericReminder = new GenericReminder();
        List<Attribute> reminders = contact.findAttributes(ComparatorUtil.cmpByAttribute, genericReminder);
        createMiniHeader("Recordatorios", genericReminder, genericReminder.getAttributeName());        
        if (!reminders.isEmpty()) {
            HBox hbox = new HBox();
            hbox.setPrefWidth(440);
            TableView<GenericReminder> tableView = createTableView(genericReminder,1);
            CustomLinkedList<GenericReminder> data = new CustomLinkedList<>();
            for (Attribute r: reminders) {
                data.add((GenericReminder) r);
            }
            ObservableList<GenericReminder> observableData = FXCollections.observableList(data);
            tableView.setItems(observableData);
            setTableView(tableView);
            hbox.getChildren().add(tableView);
            vbContent.getChildren().add(hbox);
        }
    }
    
    private void showBirthday() {
        CustomLinkedList<Attribute> reminders = (CustomLinkedList<Attribute>)
                contact.findAttributes(
                        ComparatorUtil.cmpByAttribute, new Birthday());
        Birthday remind = (Birthday) reminders.getFirst();
        Label birthday = new Label("Cumpleaños: " + remind.getDate());
        vbContent.getChildren().add(1, birthday);
    }
    
    private void showSocialMedia() {
        SocialMedia social = new SocialMedia();
        List<Attribute> socialMedia = contact.findAttributes(ComparatorUtil.cmpByAttribute, social);
        createMiniHeader("Redes sociales", social, social.getAttributeName());
        
        if (!socialMedia.isEmpty()) {
            HBox hbox = new HBox();
            hbox.setPrefWidth(440);
            TableView<SocialMedia> tableView = createTableView(social,0);
            CustomLinkedList<SocialMedia> data = new CustomLinkedList<>();
            for (Attribute sm: socialMedia) {
                data.add((SocialMedia) sm);
            }
            ObservableList<SocialMedia> observableData = FXCollections.observableList(data);
            tableView.setItems(observableData);
            setTableView(tableView);
            hbox.getChildren().add(tableView);
            vbContent.getChildren().add(hbox);
        }
    }

    private void showEmails() {
        Email email = new Email();
        List<Attribute> emails = contact.findAttributes(ComparatorUtil.cmpByAttribute, email);
        createMiniHeader("Correo electrónico", email, email.getAttributeName());
        
        if (!emails.isEmpty()) {
            HBox hbox = new HBox();
            hbox.setPrefWidth(440);
            TableView<Email> tableView = createTableView(email,0);
            CustomLinkedList<Email> data = new CustomLinkedList<>();
            for (Attribute e: emails) {
                data.add((Email) e);
            }
            ObservableList<Email> observableData = FXCollections.observableList(data);
            tableView.setItems(observableData);
            setTableView(tableView);
            hbox.getChildren().add(tableView);
            vbContent.getChildren().add(hbox);
        }
    }

    private void showAssociatedContacts() {
        AssociatedContact asssociatedContact = new AssociatedContact();
        List<Attribute> associatedContacts = contact.findAttributes(ComparatorUtil.cmpByAttribute, asssociatedContact);
        createMiniHeader("Contactos Asociados", asssociatedContact, asssociatedContact.getAttributeName());
        
        if (!associatedContacts.isEmpty()) {
            HBox hbox = new HBox();
            hbox.setPrefWidth(440);
            TableView<AssociatedContact> tableView = createTableViewAssoContact();
            CustomLinkedList<AssociatedContact> data = new CustomLinkedList<>();
            for (Attribute ac: associatedContacts) {
                data.add((AssociatedContact) ac);
            }
            ObservableList<AssociatedContact> observableData = FXCollections.observableList(data);
            tableView.setItems(observableData);
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
                    goAddPresetAttributePage(contact, att, className);
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
                goEditPresetAttributePage(contact, att);
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
    
    private void goEditPresetAttributePage(Contact selectedContact, Attribute att) throws IOException {
        Controller editPresetAtributeController = new EditPresetAtributeController(selectedContact, att);
        App.setRoot("editPresetAtribute",editPresetAtributeController);
    }
    
    private void goAddPresetAttributePage(Contact selectedContact, Attribute att , String className) throws IOException {
        Controller addPresetAtributeController = new AddPresetAtributeController(selectedContact, att, className);
        App.setRoot("addPresetAtribute",addPresetAtributeController);
    }
    
    private void goAddReminderPage(Contact selectedContact) throws IOException {
        Controller addReminderController = new AddReminderController(selectedContact);
        App.setRoot("addReminder",addReminderController);
    }
    
    private void deleteAttribute(Attribute att) {
        if (super.confirmationAlert("atributo")){
            contact.getAttributes().remove(att);
            MobilePhone.updateContactList();
            try {
                goContactPage(contact);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void goContactPage(Contact selectedContact) throws IOException {
        Controller contactController = new ContactController(selectedContact);
        App.setRoot("contact",contactController);
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
