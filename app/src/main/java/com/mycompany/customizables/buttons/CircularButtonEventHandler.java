package com.mycompany.customizables.buttons;

import collections.CustomIterator;
import com.mycompany.customizables.ImagePagination;
import com.mycompany.customizables.pagination.Paginable;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.image.Image;

public abstract class CircularButtonEventHandler {
        private abstract class ButtonEventHandler<E> implements EventHandler {
        CustomIterator<E> circularIterator;
        Paginable paginable;

        ButtonEventHandler(CustomIterator<Image> circularIterator, 
                ImagePagination.Paginable paginable){
            this.circularIterator = circularIterator;
            this.paginable = paginable;
        }

        void setImageViewChanger(ImagePagination.ImageViewChanger imageViewChanger){
            this.imageViewChanger = imageViewChanger;
        }

        @Override
        public void handle(Event t) {
            paginable.change(getImage());
        }

        abstract Image getImage();

    }

    private class PreviousButtonEventHandler extends ButtonEventHandler{

        

        

    }
}
