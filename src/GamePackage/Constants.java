package GamePackage;

import java.awt.*;

public class Constants {

    public static final int DEFAULT_SIZE = 512;
    public static final Dimension DEFAULT_DIMENSION = new Dimension(DEFAULT_SIZE,DEFAULT_SIZE);

    // sleep time between two frames
    static final int DELAY = 20;  // time between frames in milliseconds
    public static final double DT = DELAY / 1000.0;  // DELAY in seconds
}
