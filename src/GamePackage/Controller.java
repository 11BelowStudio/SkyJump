package GamePackage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller implements MouseListener, KeyListener {

    Action action;

    public Controller(){ action = new Action(); }

    public Action action(){ return this.action; }

    public boolean isClicked() { return action.clicked; }

    public Point clickLocation() {
        if (isClicked()) {
            action.clicked = false;
            return action.clickLocation;
        } else {
            return null;
        }
    }

        @Override
    public void keyTyped(KeyEvent e) {action.releasedTheAnyButton();}

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

    @Override
    public void mouseClicked(MouseEvent e) {
        action.releasedTheAnyButton();
        action.clicked = false;
        //doesn't count unless the click was in the GameFrame
        if (e.getSource() instanceof GameFrame){
            action.pressedTheAnyButton();
            action.clicked = true;
            action.clickLocation = e.getPoint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) { action.pressedTheAnyButton();}

    @Override
    public void mouseReleased(MouseEvent e) {action.releasedTheAnyButton();}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
