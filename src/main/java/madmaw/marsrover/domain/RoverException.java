package madmaw.marsrover.domain;

/**
 * Created by Chris on 7/9/2015.
 */
public class RoverException extends Exception {

    public RoverException() {
    }

    public RoverException(String message) {
        super(message);
    }

    public RoverException(Throwable cause) {
        super(cause);
    }

    public RoverException(String message, Throwable cause) {
        super(message, cause);
    }

}
