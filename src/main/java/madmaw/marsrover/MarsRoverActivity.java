package madmaw.marsrover;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.covata.marsrover.R;
import madmaw.marsrover.domain.PositionAndOrientation;
import madmaw.marsrover.domain.Rover;
import madmaw.marsrover.domain.RoverAndCommands;
import madmaw.marsrover.domain.World;
import madmaw.marsrover.importer.WorldImporter;
import madmaw.marsrover.importer.text.mars.TextMarsWorldImporter;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by Chris on 7/9/2015.
 */
public class MarsRoverActivity extends Activity {

    private static final int REQUEST_CODE = 10101010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);

        final TextView inputView = (TextView)this.findViewById(R.id.rover_input);
        final TextView outputView = (TextView)this.findViewById(R.id.rover_output);
        final String encoding = "UTF8";
        final WorldImporter worldImporter = new TextMarsWorldImporter(encoding);

        Button inputLoadButton = (Button)this.findViewById(R.id.rover_input_load);
        inputLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("text/plain");
                Intent chooseIntent = Intent.createChooser(intent, getString(R.string.rover_input_load_file));
                MarsRoverActivity.this.startActivityForResult(chooseIntent, REQUEST_CODE);
            }
        });
        Button runButton = (Button)this.findViewById(R.id.rover_run);
        runButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                CharSequence input = inputView.getText();
                StringBuffer sb = new StringBuffer();
                try {
                    InputStream ins = IOUtils.toInputStream(input, encoding);
                    World world = worldImporter.readWorld(ins);
                    List<RoverAndCommands> roversAndCommands = world.getRovers();
                    for( RoverAndCommands roverAndCommands : roversAndCommands) {
                        Rover rover = roverAndCommands.executeCommands();
                        PositionAndOrientation positionAndOrientation = rover.getPositionAndOrientation();
                        int x = positionAndOrientation.getX();
                        int y = positionAndOrientation.getY();
                        String orientation = positionAndOrientation.getOrientation().name().substring(0, 1);
                        sb.append(x).append(" ").append(y).append(" ").append(orientation).append("\n");
                    }
                } catch (Exception ex ) {
                    StringWriter sw = new StringWriter();
                    PrintWriter pw = new PrintWriter(sw);
                    ex.printStackTrace(pw);
                    pw.flush();
                    sb.append(sw.toString());
                }
                outputView.setText(sb.toString());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( data != null && requestCode == REQUEST_CODE ) {
            TextView inputView = (TextView)this.findViewById(R.id.rover_input);

            //String filePath = data.getData().getPath();
            try {
                Uri uri = data.getData();
                InputStream ins = this.getContentResolver().openInputStream(uri);

//                InputStream ins = new FileInputStream(filePath);
                try {
                    String input = IOUtils.toString(ins);
                    inputView.setText(input);
                } finally {
                    IOUtils.closeQuietly(ins);
                }
            } catch (Exception ex) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                pw.flush();
                inputView.setText(sw.toString());
            }

        }
    }
}
