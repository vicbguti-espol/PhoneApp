package view;

import collections.CustomIterator;
import collections.CustomLinkedList;
import collections.CustomList;
import java.util.List;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ImagePagination extends CustomComponent {
    private CustomList<Image> imageList;
    private CustomIterator<Image> circularIterator;
    private int index;
    private ImageView imageView;
    private Button rightButton;
    private Button leftButton;
    
    public ImagePagination(){
        imageList = new CustomLinkedList<>();
        index = 0;
        super.buildComponent();
    }
    
    public int getIndex(){
        return index;
    }

    public List<Image> getImageList(){
        return imageList;
    }   

    /**
     * Method used to start viewing something in the container
     */
    public void initPagination(){
        circularIterator = imageList.customIterator();
        imageView.setImage(circularIterator.next());
        buildRightButton();
        buildLeftButton();
    }
    
    private void buildRightButton(){
        rightButton = new Button("►");
        rightButton.setOnAction(e ->{
                new Thread(()->{
                    Platform.runLater(()->{
                        imageView.setImage(circularIterator.next());
                        if (index == imageList.size() - 1) index = 0;
                        else index = index + 1;
                    });
                }).start();
        });
    }
    
    private void buildLeftButton(){
        leftButton = new Button("◄");
        leftButton.setOnAction(e -> {
            new Thread(()->{
                Platform.runLater(()->{
                    imageView.setImage(circularIterator.previous());
                    if (index == 0) index = imageList.size() - 1;
                    else index = index - 1;
                });
            }).start();    
        });
    }
    
    @Override
    protected void buildSubComponents(){
        buildRightButton();
        buildLeftButton();
        buildImageView();
    }


    @Override
    protected void buildContainer(){
        HBox buttonsHBox = new HBox(leftButton, rightButton);
        container = new VBox(buttonsHBox,
                imageView);
    }

    private void buildImageView(){
        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);
    }

}
