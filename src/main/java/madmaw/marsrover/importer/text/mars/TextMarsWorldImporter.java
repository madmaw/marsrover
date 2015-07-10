package madmaw.marsrover.importer.text.mars;

import madmaw.marsrover.domain.Orientation;
import madmaw.marsrover.domain.RoverAndCommands;
import madmaw.marsrover.domain.RoverCommand;
import madmaw.marsrover.domain.World;
import madmaw.marsrover.domain.command.RoverForwardCommand;
import madmaw.marsrover.domain.command.RoverTurnLeftCommand;
import madmaw.marsrover.domain.command.RoverTurnRightCommand;
import madmaw.marsrover.domain.mars.MarsRover;
import madmaw.marsrover.importer.WorldImporter;
import madmaw.marsrover.importer.WorldParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Chris on 7/9/2015.
 */
public class TextMarsWorldImporter implements WorldImporter {

    private String encoding;

    public TextMarsWorldImporter(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public World readWorld(InputStream ins) throws IOException, WorldParseException {
        InputStreamReader reader = new InputStreamReader(ins, this.encoding);
        BufferedReader bufferedReader = new BufferedReader(reader);
        boolean worldSizeValid;
        Throwable cause = null;
        Integer worldWidth = null;
        Integer worldHeight = null;
        String worldSizeLine = bufferedReader.readLine();
        if( worldSizeLine != null ) {
            worldSizeValid = true;
            StringTokenizer worldSizeTokenizer = new StringTokenizer(worldSizeLine);
            String worldWidthString = worldSizeTokenizer.nextToken();
            String worldHeightString = worldSizeTokenizer.nextToken();

            try {
                worldWidth = Integer.parseInt(worldWidthString);
                worldHeight = Integer.parseInt(worldHeightString);
            } catch( Exception ex ) {
                worldSizeValid = false;
                cause = ex;
            }
            worldSizeValid = worldSizeValid && !worldSizeTokenizer.hasMoreTokens();
        } else {
            worldSizeValid = false;
        }
        if( !worldSizeValid ) {
            throw new WorldParseException("invalid world size line (should be \"<width> <height>\" received: "+worldSizeLine+")", cause);
        } else {
            boolean done = false;
            ArrayList<RoverAndCommands> rovers = new ArrayList<RoverAndCommands>();
            while( !done ) {
                RoverAndCommands rover = this.readRoverAndCommands(bufferedReader, worldWidth, worldHeight);
                if( rover != null ) {
                    rovers.add(rover);
                } else {
                    done = true;
                }
            }
            return new World(rovers);
        }

    }

    RoverAndCommands readRoverAndCommands(BufferedReader reader, int worldWidth, int worldHeight) throws IOException, WorldParseException {
        MarsRover rover = this.readRover(reader, worldWidth, worldHeight);
        if( rover != null ) {
            // parse the commands
            List<RoverCommand> commands = this.readRoverCommands(reader);
            if( commands != null ) {
                return new RoverAndCommands(rover, commands);
            } else {
                throw new WorldParseException("last rover missing commands!");
            }
        } else {
            return null;
        }
    }

    MarsRover readRover(BufferedReader reader, int worldWidth, int worldHeight) throws IOException, WorldParseException {
        String roverPositionString = reader.readLine();
        if( roverPositionString != null ) {
            boolean roverPositionValid = true;
            Throwable cause = null;
            Integer roverX = null;
            Integer roverY = null;
            Orientation roverOrientation = null;
            StringTokenizer roverPositionTokenizer = new StringTokenizer(roverPositionString);
            String roverXString = roverPositionTokenizer.nextToken();
            String roverYString = roverPositionTokenizer.nextToken();
            String roverOrientationString = roverPositionTokenizer.nextToken();
            if( "N".equals(roverOrientationString) ) {
                roverOrientation = Orientation.North;
            } else if( "S".equals(roverOrientationString) ) {
                roverOrientation = Orientation.South;
            } else if( "E".equals(roverOrientationString) ) {
                roverOrientation = Orientation.East;
            } else if( "W".equals(roverOrientationString) ) {
                roverOrientation = Orientation.West;
            } else {
                roverPositionValid = false;
            }
            try {
                roverX = Integer.parseInt(roverXString);
                roverY = Integer.parseInt(roverYString);
            } catch( Exception ex ) {
                roverPositionValid = false;
                cause = ex;
            }
            if( !roverPositionValid ) {
                throw new WorldParseException("invalid rover position (should be \"<x> <y> <orientation>\" received: "+roverPositionString+")", cause);
            } else {
                return new MarsRover(roverX, roverY, roverOrientation, worldWidth, worldHeight);
            }
        } else {
            // end of input
            return null;
        }
    }

    List<RoverCommand> readRoverCommands(BufferedReader reader) throws IOException, WorldParseException {
        String commandsString = reader.readLine();
        if( commandsString != null ) {
            commandsString = commandsString.trim();
            ArrayList<RoverCommand> commands = new ArrayList<RoverCommand>(commandsString.length());
            for( int i=0; i<commandsString.length(); i++ ) {
                char commandChar = commandsString.charAt(i);

                RoverCommand command;
                switch( commandChar ) {
                    case 'L':
                        command = new RoverTurnLeftCommand();
                        break;
                    case 'R':
                        command = new RoverTurnRightCommand();
                        break;
                    case 'M':
                        command = new RoverForwardCommand();
                        break;
                    default:
                        throw new WorldParseException("unknown command character '"+commandChar+"'");
                }
                commands.add(command);
            }
            return commands;
        } else {
            return null;
        }
    }
}
