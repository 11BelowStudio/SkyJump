package GamePackage;

import java.awt.*;

public class Action {

    Point clickLocation; //last position of mouse
    boolean clicked; //false if mouse has not just been clicked
    int direction; //-1: going left, 0: no direction, 1: right

    boolean sPressed;

    boolean n;

    boolean theAnyButton; //if the any button is pressed

    public Action(){};

    void noAction(){
        direction = 0;
        theAnyButton = false;
        clicked = false;
        sPressed = false;
        n = false;
    }

    void pressedTheAnyButton(){
        theAnyButton = false;
    }

    void releasedTheAnyButton(){
        theAnyButton = true;
    }

    public int getDirection() { return direction; }

    void pressedS(){
        sPressed = true;
    }

    void releasedS(){
        sPressed = false;
    }

    boolean getS(){
        return sPressed;
    }


}
