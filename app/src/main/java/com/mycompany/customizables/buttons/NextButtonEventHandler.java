/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.customizables.buttons;

import collections.CustomIterator;
import com.mycompany.customizables.ImagePagination;
import javafx.scene.image.Image;

/**
 *
 * @author vicbguti
 */
public class NextButtonEventHandler extends CircularButtonEventHandler {
    public NextButtonEventHandler(CustomIterator<Image> circularIterator,
                ImagePagination.ImageViewChanger imageViewChanger) {
            super(circularIterator, imageViewChanger);
        }

        @Override
        Image getImage() {
            return circularIterator.next();
        }
}
