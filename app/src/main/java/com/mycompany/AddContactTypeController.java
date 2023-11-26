package com.mycompany;

import collections.CustomLinkedList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.attributes.Attribute;
import model.attributes.ContactImage;
import model.attributes.location.Location;
import model.attributes.phone.PhoneNumber;
import model.contacts.Contact;
import model.user.MobilePhone;


public abstract class AddContactTypeController extends DataEntryController {
    
    protected FileChooser fileDialog;
    protected List<File> imageList;
    protected Pagination pagination;
    protected TextField imageSourceTextField;
    protected Button btnImageAdding;
    protected VBox imageVBox;
    
    
    protected PhoneNumber phone;
    protected Location location;
    protected List<ContactImage> images;
    protected Contact contact;
    
    Iterator<String> imageURLIterator;
    
    
    
    private void saveContact() {
        MobilePhone.addContact(contact);
    }
    
    void addContact() {
        if (isPrepared()){
            loadInfo();
            addAttributes();
            saveContact();
            returnHomePage();
            sucessDialog();
        } else{
            noDataAlert();
        }
    }
    
    void addAttributes(){
        List<Attribute> attributes = contact.attributes;
        attributes.add(location);
        attributes.addAll(images);
        addTypeAttributes();
    }
    
    void addAttribute(Attribute attribute){
        List<Attribute> attributes = contact.attributes;
        attributes.add(attribute);
    }
    
    void loadInfo(){
        loadPhone();
        loadContact();
        loadLocation();
        loadImages();
        loadTypeData();
    }
    

    
    private void loadImages(){
    // handling image attribute
    images = new ArrayList<>();
    for (File file: imageList){
        try{
            //get Url and open stream
            String urlString = file.toURI().toString();
            String[] splittedPath = urlString.split("/");
            String imageSRC = splittedPath[splittedPath.length - 1];
            InputStream inputStream = new URL(urlString).openStream();
            
            ContactImage newImage = new ContactImage();
            String source = "images/" + contact.getUID() + "/";
            Files.createDirectories(Paths.get(source));
            String targetPath = source + imageSRC;
            File target = new File(targetPath);

            //copy bytes from the stream to the target file
            Files.copy(inputStream, 
                    target.toPath(), 
                    StandardCopyOption.REPLACE_EXISTING);
            newImage.setPath(targetPath);
            images.add(newImage);
        } catch (Exception x){
            System.err.println("Failed to save Image");
            x.printStackTrace();
        }   
        }     
    }
    
    private void initImageChooser(){
        initPagination();
        initImagesVBox();
        initImageSourceText();
        initBtnImageDialog();
        initFileDialog();
    }
        
    private void initImageSourceText(){
        initImageTextField();
        imageSourceTextField.setEditable(false);
        imageSourceTextField.setFocusTraversable(false);
    }
    
    private void initBtnImageDialog(){
        initBtnImageAdding();
        btnImageAdding.setOnAction(e -> openImagesDialog());
    }
    
    private void initPagination(){
        pagination = new Pagination();
        pagination.setPageFactory(index -> {
        if (imageList != null && index < imageList.size()) {
            String url = imageList.get(index).toURI().toString();
            ImageView imageView = new ImageView(url);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(400);
            imageView.setFitWidth(600);

            return imageView;

        } else {
            return new Label("No hay imágenes seleccionadas");
        }
        });
        
    }
        
    private void initFileDialog(){
        fileDialog = new FileChooser();
        fileDialog.getExtensionFilters()
                .add(new FileChooser.
                        ExtensionFilter("Imagen", 
                                "*.jpg", 
                                "*.png", 
                                "*.bmp", 
                                "*.gif"));
    }
    
    private void openImagesDialog(){
        fileDialog.setTitle("Abrir imagenes");
        imageList = fileDialog.showOpenMultipleDialog(App.stage);
        if(imageList != null) {
            new Thread(()->{
                Platform.runLater(()->{
                    initImagePagination();
                });
            }).start();
            
        }
    }
    
    private void initImageURLIterator(){
        CustomLinkedList<String> urlList = new CustomLinkedList<>();
        for (File f: imageList){
            urlList.add(f.toURI().toString());
        }
        imageURLIterator = urlList.circularIterator();
    }

    private void initImagePagination() {
        NodePaginationDirector director = new NodePaginationDirector(
                new CircularImagePagination());
        director.buildNodePagination();
        NodePagination nodePagination = director.getNodePagination();
        initImageURLIterator();
        ImageView imageView = (ImageView) nodePagination.getNode();
        imageView.setImage(new Image(imageURLIterator.next()));
        nodePagination.setButtonBehaviour(
                e -> {
                    new Thread(()->{
                        Platform.runLater(()->{
                            imageView.setImage(new Image(imageURLIterator.next()));
                        });
                    }).start();
                    
                });
        
        if (imageVBox.getChildren().size() > 3) 
           imageVBox.getChildren().remove(imageVBox.getChildren().size()-1);
        imageVBox.getChildren().add(nodePagination.getContainer());
    }

    
    private class NodePagination<E extends Node, C> {
        Pane container;
        E node;
        Button btnNext;
        
        Pane getContainer(){
            return container;
        }
        
        void setButtonBehaviour(EventHandler e){
            btnNext.setOnAction(e);
        }
        
        E getNode(){
            return node;
        }
        
        
    }
    
    
    private abstract class NodePaginationBuilder<E extends Node, C> {
        protected NodePagination<E,C> nodePagination;
        abstract void initNodePagination();
        abstract void buildPane();
        abstract void buildAlignment();
        abstract void buildSizing();
        
        public NodePagination getNodePagination(){
            return nodePagination;
        }
        
    }
    
    private class NodePaginationDirector<E extends Node,C> {
        NodePaginationBuilder<E,C> paginationBuilder;
        
        NodePaginationDirector(NodePaginationBuilder paginationBuilder){
            this.paginationBuilder = paginationBuilder;
        }
        
        void buildNodePagination(){
            paginationBuilder.initNodePagination();
            paginationBuilder.buildPane();
            paginationBuilder.buildAlignment();
            paginationBuilder.buildSizing();
        }
        
        NodePagination getNodePagination(){
            return paginationBuilder.getNodePagination();
        }
    }
    
    private class CircularImagePagination 
            extends NodePaginationBuilder<ImageView,File> {
        
        @Override
        void initNodePagination() {
           nodePagination = new NodePagination<>();
        }
        
        @Override
        public void buildPane(){
            nodePagination.btnNext = new Button("Siguiente");
            nodePagination.node = new ImageView();
            nodePagination.container = new VBox(
                    nodePagination.node, 
                    nodePagination.btnNext);
        }
        
        @Override
        public void buildAlignment(){
            VBox container = (VBox) nodePagination.container;
            container.setAlignment(Pos.CENTER);
        }

        @Override
        void buildSizing() {
            ImageView imageView = (ImageView) nodePagination.node;
            imageView.setFitWidth​(400);
            imageView.setFitHeight​(200);
            imageView.setPreserveRatio(true);
            
        }
        
        
    }
    
    void initialize(){
        initImageChooser();
        typeInitialization();
    }
    
    boolean isPrepared(){
        return imageList != null && 
                isTypePrepared();
    }
    
    abstract void initImagesVBox();
    abstract void initImageTextField();
    abstract void initBtnImageAdding();
    abstract void typeInitialization();
    abstract void loadPhone(); 
    abstract void loadContact();
    abstract void loadLocation();
    abstract void addTypeAttributes();
    abstract boolean isTypePrepared();
    abstract void loadTypeData();
}
