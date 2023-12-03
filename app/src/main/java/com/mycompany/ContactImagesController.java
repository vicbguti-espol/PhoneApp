package com.mycompany;

import collections.CustomLinkedList;
import view.ImagePagination;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.attributes.Attribute;
import model.attributes.ContactImage;
import model.attributes.names.CompanyName;
import model.attributes.names.Name;
import model.attributes.names.PersonName;
import model.comparator.ComparatorUtil;
import model.contacts.Company;
import model.contacts.Contact;
import model.contacts.Person;
import model.user.MobilePhone;

public class ContactImagesController extends AIOController {

    private Contact contact;
    private ImagePagination imagePagination;

    public ContactImagesController(Contact contact) {
        this.contact = contact;
        buildImagePagination();
        buildRootPane();
    }
    
    private void buildImagePagination(){
        imagePagination = new ImagePagination();
        List<Image> paginationImageList = imagePagination.getImageList();
        List<ContactImage> contactImages = getContactImageList();
        contactImages.forEach(e -> 
                paginationImageList.add(
                        new Image(new File(e.getPath())
                                .toURI().toString())));
        if (!paginationImageList.isEmpty()){
            imagePagination.initPagination();
        }
    }
    
    private void returnContactPage(){
        try {
            App.setRoot("contact", new ContactController(contact));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private Name getContactName(){
        Name name = null;
        if (contact instanceof Company){
            name = new CompanyName("random");
        } else if (contact instanceof Person){
            name = new PersonName("random","random");
        }
        CustomLinkedList<Attribute> names = 
                (CustomLinkedList<Attribute>) contact.findAttributes(
                ComparatorUtil.cmpByAttribute, name);
        return (Name) names.getFirst();
    }
    
    private List<ContactImage> getContactImageList(){
        List<ContactImage> contactImages = new CustomLinkedList<>();
        List<Attribute> imagesAtt = contact.findAttributes(
                ComparatorUtil.cmpByAttribute, new ContactImage());
        for (Attribute image : imagesAtt){
            if(image instanceof ContactImage){
                contactImages.add((ContactImage) image);
            }
        }
        return contactImages;
    }
    
    @Override
    protected void buildRootPane() {
        Button btnReturn = new Button("Volver");
        btnReturn.setOnAction(e -> returnContactPage());
        Button btnDelete = new Button("Eliminar");
        btnDelete.setOnAction(e -> deleteImage());
        VBox vbox = new VBox(
                btnReturn,
                new Label("Fotos de " + getContactName().toString()),
                imagePagination.getContainer(),
                btnDelete);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.TOP_LEFT);
        rootPane = vbox;
    }
    
    boolean confirmationAlert(String string){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Atención!");
        a.setContentText("Estas seguro de eliminar el " + string + "?"
                + "\nNo podrá revertir esta acción");
        Optional<ButtonType> result = a.showAndWait();
        return (result.get() == ButtonType.OK);
    }

    private void deleteImage() {
        CustomLinkedList<Attribute> attributes = (CustomLinkedList<Attribute>) contact.getAttributes();
        CustomLinkedList<Attribute> contactImages = (CustomLinkedList<Attribute>) attributes.findAll(ComparatorUtil.cmpByAttribute, new ContactImage());
        if (contactImages.size() <= 1){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Un contacto tiene que tener al menos una imagen");
            a.show();
        } else if (confirmationAlert("imagen")){
            int indexContactImage = imagePagination.getIndex();
            Attribute contactImage = contactImages.get(indexContactImage);
            contact.getAttributes().remove(contactImage);
            MobilePhone.updateContactList();
            returnContactPage();
        } 
    }
}
