package madmaw.marsrover.domain;

import java.util.List;

/**
 * Created by Chris on 7/9/2015.
 */
public class RoverAndCommands {

    private Rover rover;
    private List<RoverCommand> commands;

    public RoverAndCommands(Rover rover, List<RoverCommand> commands) {
        this.rover = rover;
        this.commands = commands;
    }

    public Rover getRover() {
        return this.rover;
    }

    public List<RoverCommand> getCommands() {
        return this.commands;
    }

    public Rover executeCommands() throws RoverException {
        for( RoverCommand command : this.commands ) {
            command.perform(this.rover);
        }
        return this.rover;
    }
}
