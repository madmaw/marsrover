package madmaw.marsrover;

import madmaw.marsrover.domain.Orientation;
import madmaw.marsrover.domain.Rover;
import madmaw.marsrover.domain.RoverAndCommands;
import madmaw.marsrover.domain.World;
import madmaw.marsrover.importer.text.mars.TextMarsWorldImporter;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by Chris on 7/9/2015.
 */

public class MarsRoverTest {

    public static final String ENCODING = "UTF8";

    private TextMarsWorldImporter importer;

    @Before
    public void setUp() {
        this.importer = new TextMarsWorldImporter(ENCODING);
    }

    @Test
    public void testExampleInput() throws Exception {

        World world = this.importer.readWorld(IOUtils.toInputStream("5 5\n" +
                "1 2 N\n" +
                "LMLMLMLMM\n" +
                "3 3 E\n" +
                "MMRMMRMRRM", ENCODING));
        List<RoverAndCommands> roversAndCommands = world.getRovers();
        Assert.assertEquals(roversAndCommands.size(), 2);
        RoverAndCommands roverAndCommands1 = roversAndCommands.get(0);
        Assert.assertEquals(roverAndCommands1.getCommands().size(), 9);
        Rover oldRover1 = roverAndCommands1.getRover();
        Assert.assertEquals(oldRover1.getPositionAndOrientation().getX(), 1);
        Assert.assertEquals(oldRover1.getPositionAndOrientation().getY(), 2);
        Assert.assertEquals(oldRover1.getPositionAndOrientation().getOrientation(), Orientation.North);
        Rover newRover1 = roverAndCommands1.executeCommands();
        Assert.assertEquals(newRover1.getPositionAndOrientation().getX(), 1);
        Assert.assertEquals(newRover1.getPositionAndOrientation().getY(), 3);
        Assert.assertEquals(newRover1.getPositionAndOrientation().getOrientation(), Orientation.North);

        RoverAndCommands roverAndCommands2 = roversAndCommands.get(1);
        Rover oldRover2 = roverAndCommands2.getRover();
        Assert.assertEquals(oldRover2.getPositionAndOrientation().getX(), 3);
        Assert.assertEquals(oldRover2.getPositionAndOrientation().getY(), 3);
        Assert.assertEquals(oldRover2.getPositionAndOrientation().getOrientation(), Orientation.East);
        Rover newRover2 = roverAndCommands2.executeCommands();
        Assert.assertEquals(newRover2.getPositionAndOrientation().getX(), 5);
        Assert.assertEquals(newRover2.getPositionAndOrientation().getY(), 1);
        Assert.assertEquals(newRover2.getPositionAndOrientation().getOrientation(), Orientation.East);



    }

}
