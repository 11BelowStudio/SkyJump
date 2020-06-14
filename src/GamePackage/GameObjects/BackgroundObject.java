package GamePackage.GameObjects;

import GamePackage.utilities.Vector2D;

import java.awt.*;

import static GamePackage.GameObjects.ObjectConstants.CLOUD1;
import static GamePackage.GameObjects.ObjectConstants.CLOUD2;
import static GamePackage.GameObjects.ObjectConstants.CLOUD3;
import static GamePackage.GameObjects.ObjectConstants.CLOUD4;
import static GamePackage.GameObjects.ObjectConstants.CLOUD5;
import static GamePackage.GameObjects.ObjectConstants.CLOUD6;

public class BackgroundObject extends GameObject {

    public BackgroundObject() {
        super(new Vector2D(0,0), new Vector2D(0,0));
        chooseImage();
    }

    @Override
    void renderObject(Graphics2D g) {
        g.drawImage(img,-(width/2),0,null);
    }

    private void chooseImage(){
        switch((int) (Math.random() * 6)){
            case (0):
                img = CLOUD1;
                width = 96;
                height = 64;
                break;
            case (1):
                img = CLOUD2;
                width = 96;
                height = 64;
                break;
            case (2):
                img = CLOUD3;
                width = 96;
                height = 64;
                break;
            case (3):
                img = CLOUD4;
                width = 96;
                height = 64;
                break;
            case(4):
                img = CLOUD5;
                width = 96;
                height = 64;
                break;
            case(5):
                img = CLOUD6;
                width = 96;
                height = 64;
                break;
        }
    }

    @Override
    public BackgroundObject revive() {
        super.revive(
                new Vector2D(
                        48 + (Math.random()*416),
                        -32 + (Math.random() * -960)
                ),
                new Vector2D(0,0)
        );
        chooseImage();
        return this;
    }
}
