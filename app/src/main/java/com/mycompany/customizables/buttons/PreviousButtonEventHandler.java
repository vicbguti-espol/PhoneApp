package com.mycompany.customizables.buttons;

import collections.CustomIterator;
import com.mycompany.customizables.ImagePagination;
import javafx.scene.image.Image;

public class PreviousButtonEventHandler {
    public LeftButtonEventHandler(CustomIterator<Image> circularIterator,
                ImagePagination.ImageViewChanger imageViewChanger) {
            super(circularIterator, imageViewChanger);
        }
    
    @Override
        Image getImage() {
            return circularIterator.previous();
        }
}
