package madmaw.marsrover.domain;

import java.util.List;

/**
 * Created by Chris on 7/9/2015.
 */
public class World {

    private List<RoverAndCommands> rovers;

    public World(List<RoverAndCommands> rovers) {
        this.rovers = rovers;
    }

    public List<RoverAndCommands> getRovers() {
        return this.rovers;
    }
}
