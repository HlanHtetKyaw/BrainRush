package com.union.brainrush.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UiConstant {
    public static int WIDTH = 1024;
    public static int HEIGHT = 768;
    public static final String NOTO_REGULAR_PATH = "/font/NotoSerifMyanmar-Regular.ttf";
    public static final ImageView firstPlayer = new ImageView(new Image("images/player/firstPlayer.png"));
    public static final ImageView secondPlayer = new ImageView(new Image("images/player/secondPlayer.png"));
    public static final ImageView thirdPlayer = new ImageView(new Image("images/player/thirdPlayer.png"));
    public static int playerQuantity = 3;
}

