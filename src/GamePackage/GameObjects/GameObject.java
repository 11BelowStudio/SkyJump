package GamePackage.GameObjects;

import utilities.Vector2D;
import java.awt.*;
import java.awt.geom.AffineTransform;

import static GamePackage.Constants.DEFAULT_SIZE;
import static GamePackage.Constants.DT;

public abstract class GameObject {

    boolean alive;

    Vector2D position;
    Vector2D velocity;

    Color objectColour;

    Image img;

    int width;
    int height;

    static final double UP_RADIANS = Math.toRadians(270);


    public GameObject(Vector2D p, Vector2D v){
        this.position = p;
        this.velocity = v;
        alive = true;
    }

    public GameObject revive(){
        alive = true;
        return this;
    }

    public GameObject revive(Vector2D p, Vector2D v){
        alive = true;
        position = p;
        velocity = v;
        return this;
    }

    public void update(){
        amIAlive();
        if (alive){
            position.addScaled(velocity, DT);
            position.wrapX(DEFAULT_SIZE);
        }
    }

    void amIAlive(){
        if (position.y >= 512){
            this.alive = false;
        }
    }


    public void draw(Graphics2D g){
        AffineTransform backup = g.getTransform();
        g.translate(position.x, position.y);
        renderObject(g);
        g.setTransform(backup);
    }

    abstract void renderObject(Graphics2D g);

    public boolean stillAlive(){
        return alive;
    }

    Vector2D getPos(){
        return position;
    }

    public void startScrolling(boolean normalScroll){
        if (normalScroll){
            this.velocity.y = 192;
        } else{
            this.velocity.y = 384;
        }
    }

    public void stopScrolling(){
        this.velocity.y = 0;
    }

}
