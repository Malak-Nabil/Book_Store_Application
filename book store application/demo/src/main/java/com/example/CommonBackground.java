package com.example;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class CommonBackground {

    public static Background createCommonBackground() {
        Image backgroundImage = new Image("file:C:\\Users\\victory tech\\Desktop\\book store application\\background.jpg");
        BackgroundImage background = new BackgroundImage(
            backgroundImage,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(
                BackgroundSize.AUTO,
                BackgroundSize.AUTO,
                true,
                true,
                true,
                true
            )
        );
        return new Background(background); // Return a Background instance with the BackgroundImage
    }
}
