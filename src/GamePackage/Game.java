package GamePackage;


import GamePackage.GameObjects.*;
import utilities.HighScoreHandler;
import utilities.Vector2D;

import java.util.Stack;

public class Game extends Model {

    PlayerObject player;

    Stack<JumpPlatform> platformStack;

    Stack<DoughnutObject> doughnutStack;

    AttributeStringObject<Integer> scoreDisplay;

    static final double ABOVE_PLAT_2JUMP = -160;
    static final double ABOVE_PLAT_1JUMP = -352;

    int doughnutSpawnChance;
    static final double DOUGHNUT_CHANCES = 10;

    Integer score;

    public Game(Controller c, HighScoreHandler hs) {
        super(c, hs);
        player = new PlayerObject(c);
        score = 0;

        scoreDisplay = new AttributeStringObject<>(
                new Vector2D(256,20),
                new Vector2D(0,0),
                "Score: ",
                score,
                StringObject.MIDDLE_ALIGN
        );
        setupModel();
    }

    @Override
    void endThis() {
        if (ctrl.theAnyButton()) {
            hs.recordHighScore(score);
            stopThat = true;
        }
    }

    @Override
    public Model revive() {
        super.revive();
        setupModel();
        return this;
    }

    @Override
    public void update() {
        for(GameObject o: gameObjects){
            o.update();
            if (o instanceof DoughnutObject)
            {
                //checks to see if this doughnut has been collected
                if (((DoughnutObject) o).collideWithPlayer(player)){
                    updateScore(((DoughnutObject) o).getPoints());
                }
            }
            else if (player.comingDown())
            {
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
                //deadObjects.add(o);
                if (o instanceof DoughnutObject){
                    doughnutStack.push((DoughnutObject) o);
                } else if (o instanceof JumpPlatform){
                    platformStack.push((JumpPlatform) o);
                }
            }
        }

        for (GameObject o: hudObjects){
            o.update();
            if (o.stillAlive()){
                aliveHUD.add(o);
            } else{
                deadObjects.add(o);
            }
        }

        /*
        for (GameObject o: deadObjects){
            if (o instanceof DoughnutObject){
                doughnutStack.push((DoughnutObject) o);
            } else if (o instanceof JumpPlatform){
                platformStack.push((JumpPlatform) o);
            } else if (o instanceof PlayerObject){
                gameOver = true;
                System.out.println("u died lmao");
            }
        }*/

        if (!player.stillAlive()){
            gameOver = true;
            System.out.println("u died lmao");
        }

        if (player.verticalDirectionChange()) { //OH BOY TIME TO START/STOP SCROLLING
            boolean lowJump = player.isLowPlatformJump();
            if (player.theFireRises()) {
                for (GameObject o : aliveGameObjects) {
                    o.startScrolling(lowJump);
                }
                if (lowJump){
                    updateScore(1);
                } else{
                    updateScore(2);
                }
            } else if (player.hitTheApex()) {
                for (GameObject o : aliveGameObjects) {
                    o.stopScrolling();
                }
                aliveGameObjects.add(platformStack.pop().revive(ABOVE_PLAT_1JUMP));
                if (!lowJump){
                    aliveGameObjects.add(platformStack.pop().revive(ABOVE_PLAT_2JUMP));
                }
                if (!doughnutStack.empty()){
                    if ((Math.random() * DOUGHNUT_CHANCES) < doughnutSpawnChance){
                        aliveGameObjects.add(doughnutStack.pop().revive());
                        doughnutSpawnChance = 1;
                    } else{
                        doughnutSpawnChance++;
                    }

                }
            }
        }
        if (gameOver){
            endThis();
        }
        refreshLists();
    }

    private void setupModel(){

        score = 0;
        gameObjects.add(player.revive());

        scoreDisplay.showValue(score);
        hudObjects.add(scoreDisplay.revive());


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

        doughnutStack = new Stack<>();
        for (int i = 0; i < 5; i++) {
            doughnutStack.push(new DoughnutObject());
        }
        gameObjects.add(doughnutStack.pop().revive());


        stopThat = false;
        gameOver = false;

        doughnutSpawnChance = 1;

    }

    private void updateScore(int addToScore){
        score += addToScore;
        scoreDisplay.showValue(score);
    }
}
