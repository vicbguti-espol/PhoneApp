package com.mycompany;

import collections.CustomLinkedList;
import com.mycompany.customizables.CustomComponent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.attributes.Attribute;
import model.attributes.ContactImage;
import model.contacts.Contact;
import model.user.MobilePhone;

public class ContactListController {

//    @FXML
//    private Button btnAdd;
    //@FXML
    // private ListView<Contact> contactListView;
    //@FXML
    // private BorderPane root;
    
    private Pane rootPane;
    private Button btnAdd;
    private ContactListView contactListView;
    
    public ContactListController(){
        // buildListView();
        buildBtnAdd();
        buildPane();
    }
    
    public Pane getRoot(){
        return rootPane;
    }
    
    private void buildPane(){
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
    
    private void buildBtnAdd(){
        btnAdd = new Button("➕");
        btnAdd.setOnAction(e -> App.setRoot("chooseContactType"));
    }
    
//    private void buildListView(){
//        List<Contact> contactList = MobilePhone.getContactList();
//        ObservableList<Contact> contactObservableList = 
//                FXCollections.observableList(contactList);
//        contactListView = new ListView<>(contactObservableList);
//    }
    
    private class ContactCard extends CustomComponent {
        private Contact contact;
        private ImageView contactImageView;

        public ContactCard(Contact contact){
            this.contact = contact;
            super.buildComponent();
        }

        @Override
        public void buildSubComponents(){
            buildContactImageView();
        }
        
        void buildContactImageView(){
            CustomLinkedList<Attribute> images = (CustomLinkedList<Attribute>) contact.
            findByAttribute(new ContactImage());
            ContactImage image = (ContactImage) images.getFirst();
            System.out.println(image.getPath());
            contactImageView = new ImageView(image.getPath());
        }

        @Override
        public void buildContainer(){
            Label contactLabel = new Label(contact.toString());
            super.container = new HBox(contactLabel,contactImageView);           
        }  
    }
    
    private class ContactListView extends CustomComponent {
        private final List<ContactCard> contactCards = new CustomLinkedList<>();
        
        @Override
        protected void buildSubComponents(){
            
        }
        
        @Override
        protected void buildContainer(){
            container = new VBox();
        }   
        
        void initContactListView(){
            Iterator<ContactCard> it = contactCards.iterator();
            int i = 3;
            while (it.hasNext() && i < 3) 
                container.getChildren().add(it.next().getContainer());
        }
        List<ContactCard> getContactCards(){ return contactCards;}
    }
    
//     /**
//     * Initializes the controller class.
//     */
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        
//        
//        root.setCenter(contactListView);
//        contactListView.setOnMouseClicked(eh -> {
//            Contact selectedContact = contactListView.getSelectionModel().getSelectedItem();
//            if (selectedContact != null){
//                try {
//                    goContactPage(selectedContact);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });
//    }

    private void goContactPage(Contact selectedContact) throws IOException {
        Controller contactController = new ContactController(selectedContact);
        App.setRoot("contact",contactController);
    }
    
    @FXML
    private void goPrimaryPage(){
        App.setRoot("primary");
    }
}

