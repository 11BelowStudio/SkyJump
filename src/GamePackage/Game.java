package GamePackage;

import GamePackage.GameObjects.Collidable;
import GamePackage.GameObjects.GameObject;
import GamePackage.GameObjects.JumpPlatform;
import GamePackage.GameObjects.PlayerObject;
import utilities.HighScoreHandler;
import utilities.Vector2D;

import java.util.Stack;

public class Game extends Model {

    PlayerObject player;

    Stack<JumpPlatform> platformStack;

    public Game(Controller c, HighScoreHandler hs) {
        super(c, hs);
        player = new PlayerObject(c);
        gameObjects.add(player);
        setupStacks();
    }

    @Override
    public Model revive() {
        refreshLists();
        player.revive();
        gameObjects.add(player);
        setupStacks();
        return this;
    }

    @Override
    public void update() {
        for(GameObject o: gameObjects){
            o.update();
            if (player.comingDown())
            if (o instanceof JumpPlatform){
                if (((JumpPlatform) o).collideWithPlayer(player)){
                    System.out.println("OH LAWD IT'S HAPPENED");
                    player.riseUp();
                }
            }
            if (o.stillAlive()){
                aliveGameObjects.add(o);
            } else{
                deadObjects.add(o);
            }
        }
        refreshLists();
    }

    private void setupStacks(){
        platformStack = new Stack<>();
        for (int i = 0; i < 6; i++) {
            platformStack.push(new JumpPlatform());
        }
        gameObjects.add(platformStack.pop().revive(new Vector2D(256,416)));
        gameObjects.add(platformStack.pop().revive(224));
        gameObjects.add(platformStack.pop().revive(32));
    }
}
