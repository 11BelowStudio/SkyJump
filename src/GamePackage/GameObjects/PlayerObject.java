package GamePackage.GameObjects;

import GamePackage.Action;
import GamePackage.Controller;
import utilities.ImageManager;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static GamePackage.Constants.DEFAULT_SIZE;
import static GamePackage.Constants.DT;
import static GamePackage.GameObjects.ObjectConstants.PLAYER_SPRITESHEET;

public class PlayerObject extends GameObject {

    boolean goingUp;
    boolean apex;

    static final int APEX_HEIGHT = 96;

    Controller ctrl;
    Action currentAction;

    int directionToDraw;

    static final int MAX_SIDE_SPEED = 200;

    public PlayerObject(Controller c) {
        super(new Vector2D(256,256), new Vector2D(0,0));
        img = PLAYER_SPRITESHEET;
        width = 32;
        height = 64;

        goingUp = false;
        ctrl = c;
        directionToDraw = 1;
        apex = false;
    }

    public PlayerObject revive(){
        goingUp = false;
        //ctrl = c;
        directionToDraw = 1;
        apex = false;
        return this;
    }

    @Override
    public void update(){


        if (alive) {

            Action currentAction = this.ctrl.action();

            if (goingUp) {
                if (position.y < APEX_HEIGHT) {
                    velocity.y = 10;
                    goingUp = false;
                    apex = true;
                }
            } else if (apex){
                position.y = APEX_HEIGHT;
                apex = false;
            }


            int currentDirection = currentAction.getDirection();
            System.out.println(currentDirection);
            Vector2D addToVelocity = new Vector2D(currentDirection,20);

            addToVelocity.x = currentDirection*40;
            velocity.addScaled(addToVelocity,DT);

            velocity.capX(MAX_SIDE_SPEED);

            position.addScaled(velocity,DT);

            position.wrapX(DEFAULT_SIZE);

            directionToDraw = currentDirection+1;
        }
    }

    void renderObject(Graphics2D g){

        int spriteRow = goingUp ? 0 : 64;
        //top row drawn if going up, bottom row if going down
        int spriteCol = directionToDraw*32;
        //middle if no direction pressed, left if going left, right if going right

        g.drawImage(img,
                -16,
                -32,
                16,
                32,
                spriteCol,
                spriteRow,
                spriteCol+32,
                spriteRow+64,
                null
        );
    }

    public boolean comingDown(){
        return !goingUp;
    }

    public double riseUp(){
        velocity.y = -128;
        goingUp = true;
        System.out.println(position.y - APEX_HEIGHT);
        return position.y - APEX_HEIGHT;
    }

}
