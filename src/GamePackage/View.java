package GamePackage;

import GamePackage.Constants;
import GamePackage.GameObjects.GameObject;
import GamePackage.Model;
import utilities.ImageManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;

//le ce218 sample code has arrived (Provided by Dr Dimitri Ognibene) (enhanced a bit by me)
import static GamePackage.Constants.*;

public class View extends JComponent {
    // background colour
    private static final Color BG_COLOR = Color.black;

    private Model model;

    private static Image bg;
    static {
        try {
            //gameBG = ImageManager.loadImage("spehss2");
            //titleBG = ImageManager.loadImage("titleBG");
            bg = ImageManager.loadImage("loading");
        } catch (IOException e) { e.printStackTrace(); }
    }

    private AffineTransform bgTransform;

    private boolean displayingModel;

    private Dimension preferredDimension;

    /* VARIABLES FOR THE UNIMPLEMENTED FULLSCREEN STUFF
    public boolean fullscreen;
    private int viewHeight;
    private int viewWidth;

    private Dimension defaultDimension;
    public static final int FRAME_HEIGHT = 512;
    public static final int FRAME_WIDTH = 512;
    public static final Dimension FRAME_SIZE = new Dimension(
            Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);*/



    private double xScale;
    private double yScale;

    View(){

        xScale = 1;
        yScale = 1;

        setPreferredDimension(Constants.DEFAULT_DIMENSION);

        displayingModel = false;


    }

    void showModel(Model m, boolean isGame){
        this.model = m;
        displayingModel = true;
        //swapBackground(isGame);
    }

    //would have got more use with the fullscreen support stuff
    public void setPreferredDimension(Dimension d){
        preferredDimension = d;
        xScale = (preferredDimension.width == DEFAULT_SIZE? 1 :
                (double)DEFAULT_SIZE/preferredDimension.width);
        yScale = (preferredDimension.height == DEFAULT_SIZE? 1 :
                (double)DEFAULT_SIZE/preferredDimension.height);
    }

    /*
    private void swapBackground(boolean gameActive){
        if (gameActive){
            bg = gameBG;
        } else{
            bg = titleBG;
        }
        setupBackgroundTransformation();
    }*/


    @Override
    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        AffineTransform initialTransform = g.getTransform();

        g.setColor(BG_COLOR);
        g.fill(this.getBounds());

        //g.drawImage(bg, bgTransform,null); //renders the background



        if (displayingModel) {
            model.draw(g);
        } else{
            g.scale(xScale,yScale);
            AffineTransform backup = g.getTransform();
            g.drawImage(bg,backup,null);
        }
        //g.setTransform(backup);
        g.setTransform(initialTransform);
        revalidate();
    }

    @Override
    public Dimension getPreferredSize() { return preferredDimension; }

}