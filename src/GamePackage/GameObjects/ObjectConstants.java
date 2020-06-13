package GamePackage.GameObjects;

import utilities.ImageManager;

import java.awt.*;
import java.io.IOException;

public class ObjectConstants {

    static Image PLAYER_SPRITESHEET,
            PLATFORM,
            DOUGHNUT,
            CLOUD1,
            CLOUD2,
            CLOUD3,
            CLOUD4,
            CLOUD5,
            CLOUD6;
    static{
        try {
            PLAYER_SPRITESHEET = ImageManager.loadImage("PlayerSpritesheet");
            PLATFORM = ImageManager.loadImage("platform");
            DOUGHNUT = ImageManager.loadImage("doughnut");
            CLOUD1 = ImageManager.loadImage("cloud1");
            CLOUD2 = ImageManager.loadImage("cloud2");
            CLOUD3 = ImageManager.loadImage("cloud3");
            CLOUD4 = ImageManager.loadImage("cloud4");
            CLOUD5 = ImageManager.loadImage("cloud5");
            CLOUD6 = ImageManager.loadImage("cloud6");
        } catch (IOException e) { e.printStackTrace(); }
    }
}
