package GamePackage;

import GamePackage.GameObjects.GameObject;
import utilities.HighScoreHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Model {

    List<GameObject> gameObjects;
    List<GameObject> hudObjects;
    List<GameObject> backgroundObjects;

    List<GameObject> aliveGameObjects;
    List<GameObject> aliveHUD;
    List<GameObject> aliveBackground;

    List<GameObject> deadObjects;

    Color backgroundColor;
    Rectangle backgroundRect = new Rectangle(0,0,512,512);

    boolean gameOver;

    boolean stopThat;

    Controller ctrl;

    HighScoreHandler hs;

    public Model(Controller ctrl, HighScoreHandler hs){
        gameObjects = new ArrayList<>();
        hudObjects = new ArrayList<>();
        backgroundObjects = new ArrayList<>();

        aliveGameObjects = new ArrayList<>();
        aliveHUD = new ArrayList<>();
        aliveBackground = new ArrayList<>();

        deadObjects = new ArrayList<>();

        gameOver = false;
        stopThat = false;
        backgroundColor = Color.CYAN;
        this.hs = hs;
        this.ctrl = ctrl;
    }

    public void draw(Graphics2D g){
        g.setColor(backgroundColor);
        g.fill(backgroundRect);
        synchronized (Model.class) {
            /*
            for (GameObject o: backgroundObjects){
                o.draw(g);
                //draws background objects
            }*/
            for (GameObject o : gameObjects) {
                o.draw(g);
                //basically calls the draw method of each gameObject
            }

            for (GameObject o : hudObjects) {
                o.draw(g);
                //and then the HUD (so its displayed above the game objects)
            }
        }
    }

    void refreshLists(){
        synchronized (Model.class) {
            backgroundObjects.clear();
            backgroundObjects.addAll(aliveBackground);


            gameObjects.clear();
            gameObjects.addAll(aliveGameObjects);


            hudObjects.clear();
            hudObjects.addAll(aliveHUD);
        }
        aliveBackground.clear();
        aliveGameObjects.clear();
        aliveHUD.clear();
        deadObjects.clear();
    }

    void endThis(){
        stopThat = true;
    }

    public Model revive(){
        ctrl.noAction();
        refreshLists();
        backgroundObjects.clear();
        gameObjects.clear();
        hudObjects.clear();

        return this;
    }

    abstract public void update();

    public boolean keepGoing(){
        return !stopThat;
    }
}
