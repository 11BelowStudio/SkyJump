package GamePackage;

import utilities.HighScoreHandler;

public class TitleScreen extends Model {


    public TitleScreen(Controller ctrl, HighScoreHandler hs) {
        super(ctrl, hs);
        carryOnAsYouWere = false;
    }

    @Override
    public Model revive() {
        return this;
    }

    @Override
    public void update() {

    }
}
