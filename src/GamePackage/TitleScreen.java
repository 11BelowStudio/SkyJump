package GamePackage;

import GamePackage.GameObjects.*;
import utilities.HighScoreHandler;
import utilities.Vector2D;

import java.util.ArrayList;
import java.util.Arrays;


public class TitleScreen extends Model {
    

    private boolean showingTitle;

    private final StringObject titleText;
    private final StringObject subtitleText;

    private final StringObject play1;
    private final StringObject play2;

    private final StringObject quitText;


    private final ArrayList<String> introList;


    public TitleScreen(Controller ctrl, HighScoreHandler hs) {
        super(ctrl, hs);
        stopThat = false;


        titleText = new StringObject(new Vector2D(256,128),new Vector2D(),"SkyJump",StringObject.MIDDLE_ALIGN,StringObject.BIG_SANS);

        subtitleText = new StringObject(new Vector2D(256,160), new Vector2D(),"by 11BelowStudio",StringObject.MIDDLE_ALIGN,StringObject.MEDIUM_SANS);

        play1 = new StringObject(new Vector2D(256,256),new Vector2D(),"Press the ANY button",StringObject.MIDDLE_ALIGN,StringObject.MEDIUM_SANS);
        play2 = new StringObject(new Vector2D(256,300),new Vector2D(),"to start the game!",StringObject.MIDDLE_ALIGN,StringObject.MEDIUM_SANS);

        quitText = new StringObject(new Vector2D(256,384),new Vector2D(),"press ESC to quit",StringObject.MIDDLE_ALIGN,StringObject.SANS);


        String[] introArray = new String[]{
                "It is the far future",
                "All around, there is nothing but war.",
                "",
                "",
                "",
                "However, there is one way out",
                "One way that can still be used to escape this war zone",
                "",
                "",
                "And that way",
                "",
                "",
                "Is up."
        };

        introList = new ArrayList<>();
        introList.addAll(Arrays.asList(introArray));
    }

    @Override
    public TitleScreen revive() {
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
        for (GameObject o : gameObjects) {
            o.update();
            if (o.stillAlive()) {
                aliveGameObjects.add(o);
            } else {
                //deadObjects.add(o);
                if (o instanceof DoughnutObject) {
                    ((DoughnutObject) o).revive();
                } else if (o instanceof JumpPlatform) {
                    ((JumpPlatform) o).revive(-64);
                }
                o.startScrolling(true);
                aliveGameObjects.add(o);
            }
        }

        for (GameObject o : hudObjects) {
            o.update();
            if (o.stillAlive()) {
                aliveHUD.add(o);
            }
        }

        for (GameObject o : backgroundObjects) {
            o.update();
            if (o.stillAlive()) {
                aliveBackground.add(o);
            } else {
                if (o instanceof BackgroundObject) {
                    ((BackgroundObject) o).revive();
                    o.startScrolling(true);
                    aliveBackground.add(o);
                }
            }
        }

        if (aliveHUD.isEmpty()){
            showTitle();
        }

        if (ctrl.theAnyButton()){
            if (showingTitle) {
                endThis();
            } else{
                aliveHUD.clear();
                ctrl.noAction();
            }
        }



        refreshLists();
    }

    @Override
    void setupModel() {

        ctrl.noAction();

        showingTitle = false;

        //192 between each platform
        gameObjects.add(new JumpPlatform().revive(512));
        gameObjects.add(new JumpPlatform().revive(320));
        gameObjects.add(new JumpPlatform().revive(128));



        gameObjects.add(new DoughnutObject().revive());

        for (GameObject g: gameObjects) {
            g.startScrolling(true);
        }


        backgroundObjects.add(new BackgroundObject().revive());
        backgroundObjects.add(new BackgroundObject().revive());
        backgroundObjects.add(new BackgroundObject().revive());
        backgroundObjects.add(new BackgroundObject().revive());
        backgroundObjects.add(new BackgroundObject().revive());
        backgroundObjects.add(new BackgroundObject().revive());


        for (GameObject g:
                backgroundObjects) {
            g.startScrolling(true);
        }

        stopThat = false;
        gameOver = false;

        doughnutSpawnChance = backgroundSpawnChance = 1;

        showingTitle = false;

        play2.kill();
        play1.kill();
        subtitleText.kill();
        titleText.kill();
        quitText.kill();


        createScrollingText(introList,0,50);
    }

    private void showTitle(){
        aliveHUD.clear();
        aliveHUD.add(titleText.revive());
        aliveHUD.add(subtitleText.revive());
        aliveHUD.add(play1.revive());
        aliveHUD.add(play2.revive());
        aliveHUD.add(quitText.revive());
        showingTitle = true;
    }

    private void createScrollingText(ArrayList<String> theText, int distFromBottom, double scrollSpeed){
        aliveHUD.clear();

        for (String s: theText){
            aliveHUD.add(new StringObject(new Vector2D(256,512+distFromBottom),scrollSpeed,s,StringObject.MIDDLE_ALIGN));
            //distFromBottom += distBetweenLines;
            distFromBottom += 22;
        }

    }
}
