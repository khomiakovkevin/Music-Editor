package cs3500.music.provider.view;

import cs3500.music.provider.model.MusicOperations;
import cs3500.music.provider.model.Note;
import cs3500.music.provider.model.Pitch;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pbatt on 3/21/2017.
 */
public class MusicConsoleView implements MusicView<Note> {

    private final Appendable consoleView;
    private MusicOperations<Note> model;

    /**
     * Constructor which takes in an appendable for testing purposes.
     *
     * @param appen The {@code Appendable} that should be written to.
     */
    public MusicConsoleView(Appendable appen) {
        this.consoleView = appen;
        this.model = null;
    }

    /**
     * Draws the music in the view implemented.
     */
    private void drawView() {

        Note highestNote = model.getHighestNote();
        Note lowestNote = model.getLowestNote();

        int sheetWidth = 1 + (Pitch.values().length * (highestNote.getOctave()
                - lowestNote.getOctave()) + (highestNote.getPitch().ordinal()
                - lowestNote.getPitch().ordinal()));

        //First column length
        //Should be "0" for a single digit and " 0" for 2 digits etc...
        int length = Integer.toString(model.getMusicDuration()).length();

        //Create a list of string arrays which will correspond to columns
        List<String[]> finalOut = new ArrayList<>();

        //Initialize the above list
        for (int z = 0; z < model.getMusicDuration(); z++) {
            finalOut.add(new String[sheetWidth]);
        }

        //Set appropriate symbols across all columns
        //Make sure each column has a symbol
        //Spaces are added and handled later
        for (int b = 0; b < model.getMusicDuration(); b++) {
            if (model.getNotesStartAtBeat(b) != null) {
                List<Note> startNotes = model.getNotesStartAtBeat(b);

                for (Note aNote : startNotes) {
                    int location = getPrintLocation(aNote, lowestNote);
                    finalOut.get(b)[location] = "X";

                }
            }

            if (model.getNotesSustainedAtBeat(b) != null) {
                List<Note> sustainNotes = model.getNotesSustainedAtBeat(b);

                for (Note aNote : sustainNotes) {
                    int location = getPrintLocation(aNote, lowestNote);
                    finalOut.get(b)[location] = "|";
                }
            }
        }

        //Begin building final output string from previous string arrays
        try {
            consoleView.append(String.format("%" + Integer.toString(length) + "s", ""));
        } catch (IOException e) {
            // Writing to the appendable failed - nothing to do.
        }

        //A list of the number of spaces we need to add
        List<Integer> addSpaces = new ArrayList<>();

        for (int y = 0; y < sheetWidth; y++) {
            //variable for each label to build
            StringBuilder aLabel = new StringBuilder();

            //Get the appropriate pitch for the lowest until highest (highest being when last iteration)
            Pitch pitch = Pitch.values()[(y + lowestNote.getPitch().ordinal()) % Pitch.values().length];

            aLabel.append(pitch.toString());

            aLabel.append(Integer.toString((lowestNote.getNoteNumber() + y) / 12));

            //Handle standard non oct 10 notes
            if (aLabel.length() == 2) {
                aLabel.insert(0, "  ");
                aLabel.append(" ");
            }

            //Handle sharps and norm oct 10 notes
            if (aLabel.length() == 3) {
                aLabel.insert(0, " ");
                aLabel.append(" ");
            }

            //Handle the cases for octave 10
            if (aLabel.length() == 4) {
                addSpaces.add(4);
            }

            //Spaces per empty column
            addSpaces.add(5);

            try {
                //Add in each column label
                consoleView.append(aLabel.toString());
            } catch (IOException e) {
                // Writing to the appendable failed - nothing to do.
            }
        }
        //add a new line
        try {
            consoleView.append("\n");
        } catch (IOException e) {
            // Writing to the appendable failed - nothing to do.
        }

        //Add each rows column values
        for (int index = 0; index < finalOut.size(); index++) {
            String[] aRow = finalOut.get(index);
            try {
                consoleView.append(String.format("%" + Integer.toString(length) + "s",
                        Integer.toString(index)));
            } catch (IOException e) {
                // Writing to the appendable failed - nothing to do.
            }
            for (int c = 0; c < aRow.length; c++) {
                String str = aRow[c];
                if (str == null) {
                    str = "";
                }
                try {
                    consoleView.append(columnCenter(str, addSpaces.get(c)));
                } catch (IOException e) {
                    // Writing to the appendable failed - nothing to do.
                }
            }
            try {
                consoleView.append("\n");
            } catch (IOException e) {
                // Writing to the appendable failed - nothing to do.
            }
        }
    }

    private int getPrintLocation(Note aNote, Note lowest) {
        return Pitch.values().length * (aNote.getOctave() - lowest.getOctave())
                + aNote.getPitch().ordinal() - lowest.getPitch().ordinal();

    }

    private String columnCenter(String aString, int length) {
        int stringLength = aString.length();

        int trailSpaces = (length - stringLength) / 2;

        int leadSpaces = length - trailSpaces - stringLength;

        return String.format("%" + Integer.toString(leadSpaces) + "s", " ")
                + aString + String.format("%" + Integer.toString(trailSpaces) + "s", "  ");
    }

    /**
     * Set the current size of the frame for the scrollbars to update.
     *
     * @param width  - Canvas Width.
     * @param height - Canvas Height.
     */
    @Override
    public void setCanvasSize(int width, int height) {
        //Non-op - Not necessary for this view
    }

    @Override
    public void setModel(MusicOperations<Note> model) {
        this.model = model;
    }

    @Override
    public void setCurrentBeat(int beat) {
        // No-op, as this view doesn't change its rendering based on the current beat.
    }

    @Override
    public void drawMusic() {
        if (model != null) {
            drawView();
        }
    }

    @Override
    public void setKeyListener(KeyListener keyListener) {
        // No-op, as this view doesn't have key events.
    }

    @Override
    public void setMouseListener(MouseListener mouseListener) {
        // No-op as this view doesn't have mouse events.
    }

    @Override
    public Note getNoteAtLocation(int x, int y) {
        return null;
    }
}
