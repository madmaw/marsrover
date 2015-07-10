package madmaw.marsrover.domain.mars;

import madmaw.marsrover.domain.Orientation;
import madmaw.marsrover.domain.RoverException;

/**
 * Created by Chris on 7/9/2015.
 */
public class MarsRoverUnexpectedOrientationException extends RoverException {

    private Orientation unexpectedOrientation;

    public MarsRoverUnexpectedOrientationException(Orientation unexpectedOrientation) {
        this.unexpectedOrientation = unexpectedOrientation;
    }

    public Orientation getUnexpectedOrientation() {
        return this.unexpectedOrientation;
    }

}
