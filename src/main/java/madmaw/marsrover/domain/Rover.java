package madmaw.marsrover.domain;

/**
 * Created by Chris on 7/9/2015.
 */
public interface Rover {

    void left() throws RoverException;

    void right() throws RoverException;

    /**
     * moves the rover forward or throws an exception if the command fails
     */
    void forward() throws RoverException;

    PositionAndOrientation getPositionAndOrientation();
}
