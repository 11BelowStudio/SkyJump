package GamePackage.GameObjects;

import utilities.ImageManager;

import java.awt.*;
import java.io.IOException;

public class ObjectConstants {

    static Image PLAYER_SPRITESHEET, CLOUD, DOUGHNUT;
    static{
        try {
            PLAYER_SPRITESHEET = ImageManager.loadImage("PlayerSpritesheet");
            CLOUD = ImageManager.loadImage("cloud");
            DOUGHNUT = ImageManager.loadImage("doughnut");
        } catch (IOException e) { e.printStackTrace(); }
    }
}
