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
import model.user.OrderFiltro;

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
        Button ordenar = new Button("ordenar");     
        Button filtro1= new Button("filtroNombre");
        TextField caja1= new TextField();
        Button filtro2= new Button("filtroTipo");
        TextField caja2= new TextField();
        Button filtro3= new Button("filtroCantidadA");
        TextField caja3= new TextField();
        Button filtro4= new Button("Favorito");
        Button filtro5= new Button("Ordenar Apellido Nombre");
        rootPane = new VBox(
        new StackPane(new Label("Contactos")),
        new StackPane(btnAdd),
        new StackPane(ordenar),
        new StackPane(filtro1), new StackPane(caja1),
        new StackPane(filtro2),new StackPane(caja2),
        new StackPane(filtro3),new StackPane(caja3) , 
        new StackPane(filtro4),new StackPane(filtro5));

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
        
//        ordenar.setOnAction(e -> {
//            new Thread(() -> {
//                Platform.runLater(() -> {
//                    ordenarC();
//                });
//            }).start();
//        });

        ordenar.setOnAction(e -> sortByAttributesListSizeAscending());
        
        filtro1.setOnAction(e -> {
            new Thread(() -> {
                Platform.runLater(() -> {
                    String c=caja1.getText();
                    filtroNombre(c);
                });
            }).start();
        });
        filtro2.setOnAction(e -> {
            new Thread(() -> {
                Platform.runLater(() -> {
                    String c=caja2.getText();
                    filtroTipo(c);
                });
            }).start();
        });
        filtro3.setOnAction(e -> {
            new Thread(() -> {
                Platform.runLater(() -> {
                    String c=caja3.getText();
                     filtroCAtributo(c);
                });
            }).start();
        });
        filtro4.setOnAction(e -> {
            new Thread(() -> {
                Platform.runLater(() -> {
                    
                     ListFavorito();
                });
            }).start();
        });
        filtro5.setOnAction(e -> {
            new Thread(() -> {
                Platform.runLater(() -> {
                    
                    ordenarNombre();
                });
            }).start();
        });
        
    }
    private void sortByAttributesListSizeAscending() {
        List<ContactCard> contactCards = contactListView.getContactCards();

        // Limpiar la lista actual y la vista de la lista
        contactCards.clear();
        // contactListView.getContainer().getChildren().clear();
        for (Contact c: MobilePhone.getContactList()){
            System.out.println("" + c + c.getAttributes().size());
        }
        List<Contact> sortedList = OrderFiltro.sortByAttributesListSize(MobilePhone.getContactList());
        if (!sortedList.isEmpty()) {
            sortedList.forEach(e -> contactCards.add(new ContactCard(e)));
            new Thread(()->{
                Platform.runLater(()->{
                    contactListView.initContactListView();
                });
            }).start();
        } else {
            rootPane.getChildren().add(new StackPane(new Label("No existen contactos")));
        }
            // contactCards.forEach(contactCard -> contactListView.getContainer().getChildren().add(contactCard.getContainer()));
            // contactListView.getContainer().getChildren();
     }
     
     protected void ordenarNombre() {
    List<ContactCard> contactCards = contactListView.getContactCards();

    // Limpiar la lista actual y la vista de la lista
    contactCards.clear();
    contactListView.getContainer().getChildren().clear();
 // List<Contact> contactList = OrderFiltro.ordenarPorCAtributos(MobilePhone.getContactList());
    
    List<Contact> contactList = OrderFiltro.ordenarPorNombreApellido(MobilePhone.getContactList());
    if (!contactList.isEmpty()) {
        contactList.forEach(e -> contactCards.add(new ContactCard(e)));
        contactListView.initContactListView();
        
       
        contactCards.forEach(contactCard -> contactListView.getContainer().getChildren().add(contactCard.getContainer()));
         contactListView.getContainer().getChildren();
    } else {
        rootPane.getChildren().add(new StackPane(new Label("No existen contactos")));
    }
}
           protected void ListFavorito() {
    CustomList<ContactCard> contactCards = contactListView.getContactCards();

    // Limpiar la lista actual y la vista de la lista
    contactCards.clear();
    contactListView.getContainer().getChildren().clear();

    List<Contact> contactList = MobilePhone.getContactListFavorito();
    if (!contactList.isEmpty()) {
        contactList.forEach(e -> contactCards.add(new ContactCard(e)));
        contactListView.initContactListView();
        
       
        contactCards.forEach(contactCard -> contactListView.getContainer().getChildren().add(contactCard.getContainer()));
         contactListView.getContainer().getChildren();
    } else {
        rootPane.getChildren().add(new StackPane(new Label("No existen contactos")));
    }
}
protected void filtroNombre(String s) {
    CustomList<ContactCard> contactCards = contactListView.getContactCards();
    CustomList<Contact> contac= (CustomList<Contact>) OrderFiltro.filtrarPorTipoContacto("Persona");
    // Limpiar la lista actual y la vista de la lista
    contactCards.clear();
    contactListView.getContainer().getChildren().clear();
    String[]t=s.split(" ");
    CustomList<Contact> contactList = (CustomList<Contact>) OrderFiltro.filtrarPorNombreApellido(t[0], t[1], contac);
    if (!contactList.isEmpty()) {
        contactList.forEach(e -> contactCards.add(new ContactCard(e)));
        contactListView.initContactListView();
        
        contactCards.forEach(contactCard -> contactListView.getContainer().getChildren().add(contactCard.getContainer()));
    } else {
        rootPane.getChildren().add(new StackPane(new Label("No existen contactos")));
    }
}

protected void filtroTipo(String s) {
    List<ContactCard> contactCards = contactListView.getContactCards();
    
    // Limpiar la lista actual y la vista de la lista
    contactCards.clear();
    contactListView.getContainer().getChildren().clear();
    
    List<Contact> contactList = OrderFiltro.filtrarPorTipoContacto(s);
    if (!contactList.isEmpty()) {
        contactList.forEach(e -> contactCards.add(new ContactCard(e)));
        contactListView.initContactListView();
        
        contactCards.forEach(contactCard -> contactListView.getContainer().getChildren().add(contactCard.getContainer()));
    } else {
        rootPane.getChildren().add(new StackPane(new Label("No existen contactos")));
    }
}

protected void filtroCAtributo(String s) {
    List<ContactCard> contactCards = contactListView.getContactCards();
    
    // Limpiar la lista actual y la vista de la lista
    contactCards.clear();
    contactListView.getContainer().getChildren().clear();
    
    List<Contact> contactList = OrderFiltro.filtrarPorCAtributos(Integer.valueOf(s));
    if (!contactList.isEmpty()) {
        contactList.forEach(e -> contactCards.add(new ContactCard(e)));
        contactListView.initContactListView();
        
        contactCards.forEach(contactCard -> contactListView.getContainer().getChildren().add(contactCard.getContainer()));
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

