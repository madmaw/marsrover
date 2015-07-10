package madmaw.marsrover.importer;

import madmaw.marsrover.domain.World;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Chris on 7/9/2015.
 */
public interface WorldImporter {

    World readWorld(InputStream ins) throws IOException, WorldParseException;

}
