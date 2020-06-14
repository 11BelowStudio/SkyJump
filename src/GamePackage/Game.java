package GamePackage;


import GamePackage.GameObjects.*;
import GamePackage.utilities.HighScoreHandler;
import GamePackage.utilities.SoundManager;
import GamePackage.utilities.Vector2D;

import java.util.Stack;

public class Game extends Model {

    PlayerObject player;

    Stack<JumpPlatform> platformStack;

    Stack<DoughnutObject> doughnutStack;

    Stack<BackgroundObject> backgroundStack;

    AttributeStringObject<Integer> scoreDisplay;

    static final double ABOVE_PLAT_2JUMP = -160;
    static final double ABOVE_PLAT_1JUMP = -352;


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
    public Game revive() {
        ctrl.noAction();
        refreshLists();
        backgroundObjects.clear();
        gameObjects.clear();
        hudObjects.clear();
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
                        SoundManager.playClap();
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
                } else if (o instanceof PlayerObject){
                    gameOver = true;
                    System.out.println("u died lmao");
                    SoundManager.playCrunch();
                }
            }
        }

        for (GameObject o: hudObjects){
            o.update();
            if (o.stillAlive()){
                aliveHUD.add(o);
            }
        }

        for  (GameObject o: backgroundObjects){
            o.update();
            if (o.stillAlive()){
                aliveBackground.add(o);
            } else{
                if (o instanceof BackgroundObject){
                    backgroundStack.push((BackgroundObject) o);
                }
            }
        }

        if (player.verticalDirectionChange()) { //OH BOY TIME TO START/STOP SCROLLING
            boolean lowJump = player.isLowPlatformJump();
            if (player.theFireRises()) {
                for (GameObject o : aliveGameObjects) {
                    o.startScrolling(lowJump);
                }
                for (GameObject o: aliveBackground){
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
                for (GameObject o: aliveBackground){
                    o.stopScrolling();
                }
                aliveGameObjects.add(platformStack.pop().revive(ABOVE_PLAT_1JUMP));
                if (!lowJump){
                    aliveGameObjects.add(platformStack.pop().revive(ABOVE_PLAT_2JUMP));
                }
                if (!doughnutStack.isEmpty()){
                    if ((Math.random() * DOUGHNUT_CHANCES) < doughnutSpawnChance){
                        aliveGameObjects.add(doughnutStack.pop().revive());
                        doughnutSpawnChance = 1;
                    } else{
                        doughnutSpawnChance++;
                    }

                }


                for (int i = 0; i < 3; i++) {
                    if (!backgroundStack.isEmpty()) {
                        if (((Math.random() * BACKGROUND_CHANCES)) < backgroundSpawnChance) {
                            aliveBackground.add(backgroundStack.pop().revive());
                            backgroundSpawnChance = 1;
                        } else{
                            backgroundSpawnChance++;
                        }
                    } else {
                        break;
                    }
                }

            }
        }
        if (gameOver){
            endThis();
        }
        refreshLists();
    }

    void setupModel(){

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

        backgroundStack = new Stack<>();

        for (int i = 0; i < 10; i++){
            backgroundStack.push(new BackgroundObject());
        }
        backgroundObjects.add(backgroundStack.pop().revive());
        backgroundObjects.add(backgroundStack.pop().revive());




        stopThat = false;
        gameOver = false;

        doughnutSpawnChance = backgroundSpawnChance = 1;

        switch ((int)(Math.random() * 4)){
            case 0:
                backgroundColor = SKYBLUE;
                break;
            case 1:
                backgroundColor = NIGHT;
                break;
            case 2:
                backgroundColor = SUNRISE;
                break;
            case 3:
                backgroundColor = SUNSET;
                break;
        }

    }

    private void updateScore(int addToScore){
        score += addToScore;
        scoreDisplay.showValue(score);
    }

}
