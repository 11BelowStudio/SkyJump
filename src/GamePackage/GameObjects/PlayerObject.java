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
    boolean lowPlatformJump; //whether or not the next jump is from the lowest platform
    boolean apex;
    boolean haveWeStartedTheFire; //obligatory banepost; whether it's about to rise up

    static final int APEX_HEIGHT = 96;

    Controller ctrl;
    Action currentAction;

    int directionToDraw;

    double yVelocityChange;

    static final int MAX_SIDE_SPEED = 200;
    static final int UP_Y_CHANGE = 0;
    static final int DOWN_Y_CHANGE = 128;

    static final double SIDEWAYS_DRAG = 0.985;


    public PlayerObject(Controller c) {
        super(new Vector2D(256,256), new Vector2D(0,0));
        img = PLAYER_SPRITESHEET;
        width = 32;
        height = 48;

        goingUp = false;
        ctrl = c;
        directionToDraw = 1;
        yVelocityChange = DOWN_Y_CHANGE;
        apex = false;
        haveWeStartedTheFire = false;
    }

    public PlayerObject revive(){
        super.revive();
        position.set(256,256);
        velocity.set(0,0);
        goingUp = false;
        //ctrl = c;
        directionToDraw = 1;
        yVelocityChange = DOWN_Y_CHANGE;
        apex = false;
        haveWeStartedTheFire = false;
        return this;
    }

    @Override
    public void update(){


        if (alive) {

            Action currentAction = this.ctrl.action();

            int currentDirection = currentAction.getDirection();
            //System.out.println(currentDirection);

            if (goingUp) {
                if (position.y <= APEX_HEIGHT) {
                    velocity.y = 30;
                    goingUp = false;
                    apex = true;
                }
            } else if (apex){
                position.y = APEX_HEIGHT;
                apex = false;
                yVelocityChange = DOWN_Y_CHANGE;
            } else if (haveWeStartedTheFire){
                //the fire rises.
                haveWeStartedTheFire = false;
                goingUp = true;
                if (lowPlatformJump){
                    position.y = 384;
                    //288 to jump
                    velocity.y = -288;
                } else{
                    position.y = 192;
                    //96 to jump
                    velocity.y = -96;
                }
                System.out.println(position.y - APEX_HEIGHT);
                yVelocityChange = UP_Y_CHANGE;
            }


            Vector2D addToVelocity = new Vector2D(currentDirection*128,yVelocityChange);

            velocity.addScaled(addToVelocity,DT);
            velocity.multX(SIDEWAYS_DRAG);

            velocity.capX(MAX_SIDE_SPEED);

            position.addScaled(velocity,DT);

            position.wrapX(DEFAULT_SIZE);

            directionToDraw = currentDirection+1;
        }
        amIAlive();
    }

    void renderObject(Graphics2D g){

        //int spriteRow = goingUp ? 0 : 64;
        int spriteRow = goingUp ? 0 : 48;
        //top row drawn if going up, bottom row if going down
        int spriteCol = directionToDraw*32;
        //middle if no direction pressed, left if going left, right if going right

        g.drawImage(img,
                -16,
                -24,
                16,
                24,
                spriteCol,
                spriteRow,
                spriteCol+32,
                spriteRow+48,
                null
        );
    }

    public boolean comingDown(){
        return !goingUp;
    }

    public void riseUp(){
        /*
        if (position.y > 384){
            position.y = 384;
        } else if (position.y < 256 && position.y > 192){
            position.y = 192;
        }*/

        velocity.y = 0;
        haveWeStartedTheFire = true;

        lowPlatformJump = (position.y > 320);
        System.out.println(position.y - APEX_HEIGHT);
        //return position.y - APEX_HEIGHT;
    }

    public boolean isLowPlatformJump(){
        return lowPlatformJump;
    }

    public boolean verticalDirectionChange(){
        return (apex || haveWeStartedTheFire);
    }

    public boolean hitTheApex(){
        return apex;
    }

    public boolean theFireRises(){
        return haveWeStartedTheFire;
    }

    public void startScrolling(boolean doubleScroll){
        //no.
    }

    public void stopScrolling(){
        //no.
    }

}
