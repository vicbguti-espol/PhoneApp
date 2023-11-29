package com.mycompany;

import collections.CustomLinkedList;
import com.mycompany.customizables.CustomComponent;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
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
            Label contactLabel = new Label(contact.toString());
            container = new HBox(imageShape, contactLabel); 
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
        private final List<ContactCard> contactCards = new CustomLinkedList<>();
        
        ContactListView(){
            super.buildComponent();
        }
        
        @Override
        protected void buildSubComponents(){
            
        }
        
        @Override
        protected void buildContainer(){
            container = new VBox();
        }   
        
        void initContactListView(){
            Iterator<ContactCard> it = contactCards.iterator();
            int i = 0;
            while (it.hasNext() && i < 3) 
                container.getChildren().add(it.next().getContainer());
                i++;
        }
        List<ContactCard> getContactCards(){ return contactCards;}
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

