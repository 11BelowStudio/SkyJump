package GamePackage.GameObjects;

import utilities.Vector2D;

import java.awt.*;

import static GamePackage.GameObjects.ObjectConstants.PLATFORM;

public class JumpPlatform extends GameObject implements Collidable{


    public JumpPlatform() {
        super(new Vector2D(0,0), new Vector2D(0,0));
        img = PLATFORM;
        width = 96;
        height = 32;
    }

    public JumpPlatform revive(double yPos){
        this.revive(
                new Vector2D(48 + (Math.random()*416), yPos)
        );

        return this;
    }

    public JumpPlatform revive(Vector2D p){
        super.revive(p, new Vector2D(0,0));
        return this;
    }

    @Override
    void renderObject(Graphics2D g) { g.drawImage(img,-48,0,null); }

    @Override
    public boolean collideWithPlayer(PlayerObject p) {
        Vector2D playerPos = p.getPos();
        return (playerPos.xDist(position,512) < 64 && playerPos.yDist(position) < 24);
    }
}

