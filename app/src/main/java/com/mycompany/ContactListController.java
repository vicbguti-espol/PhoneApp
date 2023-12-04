package com.mycompany;

import collections.CustomIterator;
import collections.CustomLinkedList;
import collections.CustomList;
import view.CustomComponent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import model.attributes.Attribute;
import model.attributes.ContactImage;
import model.contacts.Contact;
import model.user.MobilePhone;
import model.util.structures.SortFilterUtil;

public class ContactListController extends AIOController {
    private Button btnAdd;
    private ContactListView contactListView;
    
    private TextField personNameTextField;
    private TextField contactTypeTextField;
    private TextField filterByAttributesTextField;
    
    public ContactListController(){
        buildBtnAdd();
        buildRootPane();
    }
    
    private void buildBtnAdd(){
        btnAdd = new Button("➕");
        btnAdd.setOnAction(e -> App.setRoot("chooseContactType"));
    }

    @Override
    protected void buildRootPane() {
        Button sortByAttributesButton = new Button("Ordenar por Cantidad de Atributos");
        Button sortByPersonNameButton = new Button("Ordenar por Nombre y Apellido de una Persona");
        Button filterByPersonNameButton = new Button("Filtrar por Nombre y Apellido de una Persona");
        personNameTextField = new TextField();
        Button filterByContactTypeButton = new Button("Filtrar por Tipo de Contacto");
        contactTypeTextField = new TextField();
        Button filterByAttributesButton = new Button("Filtrar por Cantidad de Atributos");
        filterByAttributesTextField = new TextField();
        Button filterByFavoritesButton = new Button("Filtrar por Favoritos");

        rootPane = new VBox(
        new StackPane(new Label("Contactos")),
        new StackPane(btnAdd),
        new StackPane(sortByAttributesButton),
        new StackPane(sortByPersonNameButton),
        new StackPane(filterByPersonNameButton), new StackPane(personNameTextField),
        new StackPane(filterByContactTypeButton),new StackPane(contactTypeTextField),
        new StackPane(filterByAttributesButton),new StackPane(filterByAttributesTextField));

        contactListView = new ContactListView();
        List<ContactCard> contactCards = contactListView.getContactCards();
        List<Contact> contactList = MobilePhone.getContactList();
        if (!contactList.isEmpty()){
            contactList.forEach(e -> contactCards.add(new ContactCard(e)));
            contactListView.initContactListView();
            rootPane.getChildren().add(contactListView.getContainer());
        } else {
            rootPane.getChildren().add(new StackPane(new Label("No existen contactos")));
        }

        sortByAttributesButton.setOnAction(e -> sortByAttributesListSizeAscending()); 
        sortByPersonNameButton.setOnAction(e -> sortByPersonName());
        filterByPersonNameButton.setOnAction(e -> filterByPersonName());
        filterByContactTypeButton.setOnAction(e -> filterByContactType());
        filterByAttributesButton.setOnAction(e -> filterByAttributesListSize());
        filterByFavoritesButton.setOnAction(e -> filterByFavorites());
    }
    private void sortByAttributesListSizeAscending() {
        List<ContactCard> contactCards = contactListView.getContactCards();
        contactCards.clear();
        
        List<Contact> sortedList = SortFilterUtil.sortByAttributesListSize(MobilePhone.getContactList());
        if (!sortedList.isEmpty()) {
            sortedList.forEach(e -> contactCards.add(new ContactCard(e)));
            
            new Thread(()->{
                Platform.runLater(()->{
                    contactListView.initContactListView();
                });
            }).start();
        }
     }
    
    private void sortByPersonName() {
        List<ContactCard> contactCards = contactListView.getContactCards();
        contactCards.clear();
        
        List<Contact> sortedList = SortFilterUtil.sortByPersonName(MobilePhone.getContactList());
        if (!sortedList.isEmpty()) {
            sortedList.forEach(e -> contactCards.add(new ContactCard(e)));
           new Thread(()->{Platform.runLater(()->{
                   contactListView.initContactListView();
               });
           }).start();
        } 
    }

    
    private void filterByPersonName() {
        List<ContactCard> contactCards = contactListView.getContactCards();
        contactCards.clear();
        
        List<Contact> persons = (List<Contact>) SortFilterUtil.filterByContactType("Persona");
        String[] nameSplit = personNameTextField.getText().split(" ");
        CustomList<Contact> filteredList = (CustomLinkedList<Contact>) SortFilterUtil.filterByPersonName(nameSplit[0], nameSplit[1], persons);
        if (!filteredList.isEmpty()) {
            filteredList.forEach(e -> contactCards.add(new ContactCard(e)));
            new Thread(()->{Platform.runLater(()->{
                    contactListView.initContactListView();
                });
            }).start();
        }
    }
    
    private void filterByContactType() {
        List<ContactCard> contactCards = contactListView.getContactCards();
        contactCards.clear();

        List<Contact> filteredList = 
                SortFilterUtil.filterByContactType(
                        contactTypeTextField.getText());
        if (!filteredList.isEmpty()) {
            filteredList.forEach(e -> contactCards.add(new ContactCard(e)));
            new Thread(()->{Platform.runLater(()->{
                    contactListView.initContactListView();
                });
            }).start();
        } 
    }
    
    private void filterByAttributesListSize() {
        List<ContactCard> contactCards = contactListView.getContactCards();
        contactCards.clear();

        List<Contact> filteredList = SortFilterUtil.
                filtrarPorCAtributos(
                        Integer.valueOf(
                                filterByAttributesTextField.getText()));
        if (!filteredList.isEmpty()) {
            filteredList.forEach(e -> contactCards.add(new ContactCard(e)));
            new Thread(()->{Platform.runLater(()->{
                    contactListView.initContactListView();
                });
            }).start();
        }
    }
    
    private void filterByFavorites() {
        CustomList<ContactCard> contactCards = contactListView.getContactCards();
        contactCards.clear();

        List<Contact> favoritesList = MobilePhone.getContactListFavorito();
        if (!favoritesList.isEmpty()) {
           favoritesList.forEach(e -> contactCards.add(new ContactCard(e)));
           new Thread(()->{Platform.runLater(()->{
                   contactListView.initContactListView();
               });
           }).start();
        } 
    }
    
    private class ContactCard extends CustomComponent {
        private Contact contact;
        private Shape imageShape;

        public ContactCard(Contact contact){
            this.contact = contact;
            super.buildComponent();
        }
       
        @Override
        public void buildSubComponents(){
            buildImageShape();
        }
        

        @Override
        public void buildContainer(){
            container = new HBox(imageShape, 
                    new VBox(new Label(contact.toString()),
                    new Label(contact.getType()))); 
            container.setOnMouseClicked(e -> goContactPage(contact));
        }
        
        void buildImageShape(){
            CustomLinkedList<Attribute> images = (CustomLinkedList<Attribute>) contact.
            findByAttribute(new ContactImage());
            ContactImage image = (ContactImage) images.getFirst();
            imageShape = new Circle(50,50,50);
            imageShape.setFill(new ImagePattern(
                    new Image(
                    new File(image.getPath()).toURI().toString(),
                    50,50,true,false,false)));
        }
        
    }
    
    private class ContactListView extends CustomComponent {
        private final CustomList<ContactCard> contactCards 
                = new CustomLinkedList<>();
        private CustomIterator<ContactCard> contactCardIterator;
        private VBox contactsVBox;
        private VBox buttonsVBox;
        private Button nextButton;
        private Button previousButton;
        private int viewSize;
        
        
        ContactListView(){
            super.buildComponent();
        }
        
        @Override
        protected void buildSubComponents(){
            contactsVBox = new VBox();
            buildButtonsVBox();
        }
        
        @Override
        protected void buildContainer(){
            container = new HBox(contactsVBox,
                    new StackPane(),
                    buttonsVBox);
        }
        
        private void buildButtonsVBox(){
            buildNextButton();
            buildPreviousButton();
            buttonsVBox = new VBox(previousButton,nextButton);
        }
        
        private void buildNextButton(){
            nextButton = new Button("⬇");

            nextButton.setOnAction(e -> {
                ContactCard tmp = contactCardIterator.next();
                while(!tmp.getContainer().equals(contactsVBox.getChildren().get(contactsVBox.getChildren().size()-1))){
                    tmp = contactCardIterator.next();
                }
                new Thread(()->{
                    Platform.runLater(()->{
                        contactsVBox.getChildren().remove(0);
                        contactsVBox.getChildren().add(contactCardIterator
                                .next().getContainer());
                    });
                }).start();
                
            });     
        }
        
        private void buildPreviousButton(){
            previousButton = new Button("⬆");
            previousButton.setOnAction(e -> {
                ContactCard tmp = contactCardIterator.previous();
                while (!tmp.getContainer().equals(contactsVBox.getChildren().get(0))){
                    tmp = contactCardIterator.previous();
                }
                ContactCard tmp2 = contactCardIterator.previous();
                new Thread(()->{
                    Platform.runLater(()->{
                        contactsVBox.getChildren().
                        remove(contactsVBox.getChildren().size()-1);
                        contactsVBox.getChildren().add(0,tmp2.getContainer());
                    });
                }).start();
            });
        }
        
        public void initContactListView(){
            contactsVBox.getChildren().clear();
            buildCustomIterator();
            viewSize = 0;
            while (viewSize<5 && viewSize<contactCards.size()){
                contactsVBox.getChildren().add(
                        contactCardIterator.next().getContainer());
                viewSize++;
            }
        }
        
        private void buildCustomIterator(){
            contactCardIterator = contactCards.customIterator();
        }
        
        CustomList<ContactCard> getContactCards(){ return contactCards;}
        
    }
    
    private void goContactPage(Contact selectedContact) {
        Controller contactController = new ContactController(selectedContact);
        try {
            App.setRoot("contact",contactController);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

