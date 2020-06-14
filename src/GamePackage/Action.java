package GamePackage;

public class Action {


    int direction; //-1: going left, 0: no direction, 1: right


    boolean theAnyButton; //if the any button is pressed

    public Action(){}

    void noAction(){
        direction = 0;
        theAnyButton = false;
    }

    void pressedTheAnyButton(){
        theAnyButton = false;
    }

    void releasedTheAnyButton(){
        theAnyButton = true;
    }

    public int getDirection() { return direction; }


}
