package GamePackage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller implements KeyListener {

    Action action;

    public Controller(){ action = new Action(); }

    public Action action(){ return this.action; }

        @Override
    public void keyTyped(KeyEvent e) {
        //action.releasedTheAnyButton();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        action.pressedTheAnyButton();
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                action.direction = -1;
                break;
            case KeyEvent.VK_RIGHT:
                action.direction = 1;
                break;
        }
        //System.out.println(action.direction);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        action.releasedTheAnyButton();
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
                action.direction = 0;
                break;
        }
        //System.out.println(action.direction);
    }

    public boolean theAnyButton(){ return action.theAnyButton; }

    public void noAction(){action.noAction();}

    public boolean s(){ return action.getS(); }

    public boolean n(){ return action.n; }
}
