package madmaw.marsrover.domain;

/**
 * Created by Chris on 7/9/2015.
 */
public class PositionAndOrientation {

    private int x;
    private int y;
    private Orientation orientation;

    public PositionAndOrientation(int x, int y, Orientation orientation){
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Orientation getOrientation() {
        return orientation;
    }
}
