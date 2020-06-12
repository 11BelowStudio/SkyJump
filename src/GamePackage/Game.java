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

    static final double ABOVE_PLAT_2JUMP = -160;
    static final double ABOVE_PLAT_1JUMP = -352;

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
            if (player.comingDown()) {
                if (o instanceof JumpPlatform) {
                    if (((JumpPlatform) o).collideWithPlayer(player)) {
                        System.out.println("OH LAWD IT'S HAPPENED");
                        player.riseUp();
                    }
                }
            }
            if (o.stillAlive()){
                aliveGameObjects.add(o);
            } else{
                deadObjects.add(o);
            }
        }

        for (GameObject o: deadObjects){
            if (o instanceof JumpPlatform){
                platformStack.push((JumpPlatform) o);
            }
        }

        if (player.verticalDirectionChange()) {
            boolean lowJump = player.isLowPlatformJump();
            if (player.theFireRises()) {
                for (GameObject o : aliveGameObjects) {
                    o.startScrolling(lowJump);
                }
            } else if (player.hitTheApex()) {
                for (GameObject o : aliveGameObjects) {
                    o.stopScrolling();
                }
                aliveGameObjects.add(platformStack.pop().revive(ABOVE_PLAT_1JUMP));
                if (!lowJump){
                    aliveGameObjects.add(platformStack.pop().revive(ABOVE_PLAT_2JUMP));
                }
            }
        }
        refreshLists();
    }

    private void setupStacks(){
        platformStack = new Stack<>();
        for (int i = 0; i < 6; i++) {
            platformStack.push(new JumpPlatform());
        }
        //192 between each platform
        gameObjects.add(platformStack.pop().revive(new Vector2D(256,416)));
        gameObjects.add(platformStack.pop().revive(224));
        gameObjects.add(platformStack.pop().revive(32));
        gameObjects.add(platformStack.pop().revive(-160));
        gameObjects.add(platformStack.pop().revive(-352));
    }
}
