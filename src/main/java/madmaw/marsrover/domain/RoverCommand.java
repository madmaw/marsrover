package madmaw.marsrover.domain;

/**
 * Created by Chris on 7/9/2015.
 */
public interface RoverCommand {

    void perform(Rover rover) throws RoverException;

}
