package com.mycompany;

import collections.CustomIterator;
import collections.CustomLinkedList;
import collections.CustomList;
import com.mycompany.customizables.CustomComponent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class ContactListController extends AIOController {
    private Button btnAdd;
    private ContactListView contactListView;
    
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
        rootPane = new VBox(
        new StackPane(new Label("Contactos")),
        new StackPane(btnAdd));

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
            
            container = new HBox(contactsVBox, buttonsVBox);
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
                System.out.println(tmp2.contact);
                new Thread(()->{
                    Platform.runLater(()->{
                        contactsVBox.getChildren().
                        remove(contactsVBox.getChildren().size()-1);
                        
                        contactsVBox.getChildren().add(0,tmp2.getContainer());
                    });
                }).start();
            });
        }
        
        void initContactListView(){
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

