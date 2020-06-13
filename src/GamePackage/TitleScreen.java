package GamePackage;

import utilities.HighScoreHandler;

public class TitleScreen extends Model {

    //TODO: this.


    public TitleScreen(Controller ctrl, HighScoreHandler hs) {
        super(ctrl, hs);
        stopThat = true;
    }

    @Override
    public Model revive() {
        super.revive(); return this;
    }

    @Override
    public void update() {

    }
}
