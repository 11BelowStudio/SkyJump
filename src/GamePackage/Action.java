package GamePackage;

import java.awt.*;

public class Action {

    Point clickLocation; //last position of mouse
    boolean clicked; //false if mouse has not just been clicked
    int direction; //-1: going left, 0: no direction, 1: right

    boolean theAnyButton; //if the any button is pressed

    public Action(){};

    void noAction(){
        direction = 0;
        theAnyButton = false;
        clicked = false;
    }

    void pressedTheAnyButton(){
        theAnyButton = false;
    }

    void releasedTheAnyButton(){
        theAnyButton = true;
    }

    public int getDirection() { return direction; }


}
