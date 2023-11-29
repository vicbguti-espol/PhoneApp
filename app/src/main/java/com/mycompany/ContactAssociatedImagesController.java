package com.mycompany;

import collections.CustomLinkedList;
import com.mycompany.customizables.ImagePagination;
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

public class ContactAssociatedImagesController extends AIOController {

    private Contact c;
    private Contact associated;
    private ImagePagination imagePagination;

    public ContactAssociatedImagesController(Contact c, Contact associated) {
        this.c = c;
        this.associated = associated;
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
            App.setRoot("contactAssociated", new ContactAssociatedController(c,associated));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private Name getContactName(){
        Name name = null;
        if (associated instanceof Company){
            name = new CompanyName("random");
        } else if (associated instanceof Person){
            name = new PersonName("random","random");
        }
        CustomLinkedList<Attribute> names = 
                (CustomLinkedList<Attribute>) associated.findAttributes(
                ComparatorUtil.cmpByAttribute, name);
        return (Name) names.getFirst();
    }
    
    private List<ContactImage> getContactImageList(){
        List<ContactImage> contactImages = new CustomLinkedList<>();
        List<Attribute> imagesAtt = associated.findAttributes(
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
        VBox vbox = new VBox(
                btnReturn,
                new Label("Fotos de " + getContactName().toString()),
                imagePagination.getContainer());
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.TOP_LEFT);
        rootPane = vbox;
    }
}
