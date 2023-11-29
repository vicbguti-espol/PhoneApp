package com.mycompany.customizables;

import collections.CustomIterator;
import collections.CustomLinkedList;
import java.util.List;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ImagePagination extends CustomComponent {
    private List<Image> imageList;
    private CustomIterator<Image> circularIterator;
    private ImageViewChanger imageViewChanger;
    private Button rightButton;
    private Button leftButton;
    
    public ImagePagination(){
        imageList = new CustomLinkedList<>();
        super.buildComponent();
    }

    public List<Image> getImageList(){
        return imageList;
    }   

    /**
     * Method used to start viewing something in the container
     */
    public void initPagination(){
        buildCircularIterator();
        imageViewChanger.changeImage(circularIterator.next());
        rightButton.setOnAction(new RightButtonEventHandler(
                circularIterator, imageViewChanger));
        leftButton.setOnAction(new LeftButtonEventHandler(
                circularIterator, imageViewChanger));
    }
    
    @Override
    protected void buildSubComponents(){
        buildImageViewChanger();
        buildButtons();
    }


    @Override
    protected void buildContainer(){
        HBox buttonsHBox = new HBox(leftButton, rightButton);
        container = new VBox(buttonsHBox,
                imageViewChanger.getImageView());
    }

    private void buildImageViewChanger(){
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(400);
        imageView.setFitWidth(600);
        imageViewChanger = new ImageViewChanger(imageView);
    }

    private void buildButtons(){
        leftButton = new Button("◄");
        rightButton = new Button("►");
    }

    private void buildCircularIterator(){
        CustomLinkedList<Image> circularImageList = 
            new CustomLinkedList<>(imageList);
        circularIterator = circularImageList.circularIterator();
    }
    
    private class ImageViewChanger {
        ImageView imageView;

        ImageViewChanger(ImageView imageView){
            this.imageView = imageView;
        }

        void changeImage(Image image){
            new Thread(()->{
                Platform.runLater(()->{
                    imageView.setImage(image);
                });
            }).start();
        }

        ImageView getImageView(){
            return imageView;
        }
    }        


}
