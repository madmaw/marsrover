package madmaw.marsrover.domain.mars;

import madmaw.marsrover.domain.*;

/**
 * Created by Chris on 7/9/2015.
 */
public class MarsRover implements Rover {

    private int x;
    private int y;
    private Orientation orientation;
    private int worldWidth;
    private int worldHeight;

    public MarsRover(int x, int y, Orientation orientation, int worldWidth, int worldHeight) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    @Override
    public void left() {
        Orientation[] values = Orientation.values();
        this.orientation = values[(orientation.ordinal() + 1) % values.length];
    }

    @Override
    public void right() {
        Orientation[] values = Orientation.values();
        int index = orientation.ordinal() - 1;
        if( index < 0 ) {
            index += values.length;
        }
        this.orientation = values[index];
    }

    @Override
    public void forward() throws RoverException {
        int dx;
        int dy;
        if( this.orientation == Orientation.East ) {
            dx = 1;
            dy = 0;
        } else if( this.orientation == Orientation.North ) {
            dx = 0;
            dy = 1;
        } else if( this.orientation == Orientation.West ) {
            dx = -1;
            dy = 0;
        } else if( this.orientation == Orientation.South ) {
            dx = 0;
            dy = -1;
        } else {
            // throw an exception
            throw new MarsRoverUnexpectedOrientationException(this.orientation);
        }
        int x = this.x + dx;
        int y = this.y + dy;
        if( x < 0 || x > worldWidth ||  y < 0 || y > worldHeight ) {
            // you would have fallen off the plateau!
            throw new RoverOutOfBoundsException();
        } else {
            this.x = x;
            this.y = y;
        }
    }

    @Override
    public PositionAndOrientation getPositionAndOrientation() {
        return new PositionAndOrientation(this.x, this.y, this.orientation);
    }
}
