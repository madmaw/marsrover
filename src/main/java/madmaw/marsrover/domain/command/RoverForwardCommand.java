package madmaw.marsrover.domain.command;

import madmaw.marsrover.domain.Rover;
import madmaw.marsrover.domain.RoverCommand;
import madmaw.marsrover.domain.RoverException;

/**
 * Created by Chris on 7/9/2015.
 */
public class RoverForwardCommand implements RoverCommand {

    @Override
    public void perform(Rover rover) throws RoverException {
        rover.forward();
    }
}
