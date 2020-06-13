package GamePackage.GameObjects;

import utilities.Vector2D;

import java.awt.*;

import static GamePackage.GameObjects.ObjectConstants.DOUGHNUT;

public class DoughnutObject extends GameObject implements Collidable{

    int points;

    public DoughnutObject() {
        super(new Vector2D(0,0), new Vector2D(0,0));
        width = 32;
        height = 32;
        img = DOUGHNUT;
        setValue();
    }

    public DoughnutObject revive(){
        super.revive(
                new Vector2D(
                        16 + (Math.random()*480),
                        -16 + (Math.random() * -480)
                ),
                new Vector2D(0,0)
        );
        setValue();
        return this;
    }

    private void setValue(){
        points = (int) (1+(Math.random() * 5));
    }

    @Override
    void renderObject(Graphics2D g) {
        g.drawImage(img,-16,-16,null);
    }

    @Override
    public boolean collideWithPlayer(PlayerObject p) {
        Vector2D playerPos = p.getPos();
        boolean hit = (playerPos.xDist(position,512) < 32 && playerPos.yDist(position) < 48);
        if (hit){
            alive = false;
        }
        return hit;
    }

    public int getPoints(){
        return points;
    }

    @Override
    public void update() {
        super.update();
        if (!alive){
            System.out.println("am dead at" + position + " + " + velocity );
        }
    }
}
