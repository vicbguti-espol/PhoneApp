package com.mycompany;

import collections.CustomLinkedList;
import com.mycompany.customizables.ImagePagination;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.scene.control.Button;
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
        rootPane = new VBox(
                btnReturn,
                new Label(getContactName().toString()),
                imagePagination.getContainer());
    }
}
