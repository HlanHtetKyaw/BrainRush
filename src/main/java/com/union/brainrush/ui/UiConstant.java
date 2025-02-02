package com.union.brainrush.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UiConstant {
    public static int WIDTH = 1024;
    public static int HEIGHT = 768;
    public static final String NOTO_REGULAR_PATH = "/font/NotoSerifMyanmar-Regular.ttf";
    public static final Image firstPlayer = new Image("images/player/firstPlayer.png");
    public static final Image secondPlayer = new Image("images/player/secondPlayer.png");
    public static final Image thirdPlayer = new Image("images/player/thirdPlayer.png");
    public static final ImageView fpV = new ImageView(firstPlayer);
    public static final ImageView spV = new ImageView(secondPlayer);
    public static final ImageView tpV = new ImageView(thirdPlayer);
    public static final Image firstPlayerConfirm = new Image("images/player/firstPlayerC.png");
    public static final Image secondPlayerConfirm = new Image("images/player/secondPlayerC.png");
    public static final Image thirdPlayerConfirm = new Image("images/player/thirdPlayerC.png");
}

