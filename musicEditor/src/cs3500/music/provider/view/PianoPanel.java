package cs3500.music.provider.view;

import cs3500.music.provider.model.MusicOperations;
import cs3500.music.provider.model.Note;
import cs3500.music.provider.model.Pitch;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A view that draws a MusicOperations as a piano.
 */
public class PianoPanel extends JPanel {

    // Constants
    private final int WHITE_KEY_WIDTH = 10;
    private final int BLACK_KEY_WIDTH = 6;
    private final int HALF_BLACK_KEY_WIDTH = BLACK_KEY_WIDTH / 2;
    private final int NUM_OCTAVES = 10;
    private final int WHITE_KEYS_PER_OCTAVE = 7;
    private final int OCTAVE_WIDTH = WHITE_KEY_WIDTH * WHITE_KEYS_PER_OCTAVE;
    private final int TOTAL_WIDTH = OCTAVE_WIDTH * NUM_OCTAVES;
    private final int WHITE_KEY_HEIGHT = 80;
    private final int BLACK_KEY_HEIGHT = 40;

    // Members
    private MusicOperations<Note> model;
    private int currentBeat;

    PianoPanel() {
        model = null;
        currentBeat = 0;
        setPreferredSize(new Dimension(TOTAL_WIDTH, WHITE_KEY_HEIGHT));
    }

    @Override
    public void paintComponent(Graphics g) {
        // Handle the default painting
        super.paintComponent(g);

        // Ensure there is a model to draw.
        if (this.model == null) {
            return;
        }

        // Set the drawing sizes.
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));

        // Get the notes at the current currentBeat. This is the combined list of notes that are
        // starting this beat and being sustained this beat.
        List<Note> notes = model.getNotesStartAtBeat(this.currentBeat);
        notes.addAll(model.getNotesSustainedAtBeat(this.currentBeat));

        // Draw all octaves of the piano.
        for (int octave = 0; octave < NUM_OCTAVES; octave++) {
            drawPianoOctave(g2, octave, notes);
        }
    }

    public void setModel(MusicOperations<Note> model) {
        this.model = model;
    }

    public void setCurrentBeat(int currentBeat) {
        this.currentBeat = currentBeat;
    }

    /**
     * Given an (x, y) position, returns the corresponding Note.
     *
     * @param x The x position.
     * @param y The y position.
     * @return The corresponding Note, with a duration of 1.
     */
    public Note getNoteAtLocation(int x, int y) {
        // Iterate through every octave...
        for (int octave = 0; octave < NUM_OCTAVES; octave++) {
            // Check the black keys...
            for (int noteNum = 0; noteNum < 12; noteNum++) {
                Rectangle2D keyRect = pitchIndexToBlackKeyRect(noteNum, octave);
                if (keyRect != null && keyRect.contains(x, y)) {
                    // This is the key that was clicked.
                    return new Note(Pitch.fromNoteNumber(noteNum), octave, 1);
                }
            }

            // Check the white keys...
            for (int noteNum = 0; noteNum < 12; noteNum++) {
                Rectangle2D keyRect = pitchIndexToWhiteKeyRect(noteNum, octave);
                if (keyRect != null && keyRect.contains(x, y)) {
                    // This is the key that was clicked.
                    return new Note(Pitch.fromNoteNumber(noteNum), octave, 1);
                }
            }
        }

        // The click was not on a key, so no Note was clicked.
        return null;
    }

    /**
     * Draws an octave of the piano.
     *
     * @param g2     The 2D graphics instance to draw with.
     * @param octave The octave to draw.
     * @param notes  The list of notes to draw - does not have to be just the notes in the octave.
     */
    private void drawPianoOctave(Graphics2D g2, int octave, List<Note> notes) {
        // Filter the list of notes to only include notes in this octave.
        notes = notes.stream().filter(n -> n.getOctave() == octave).collect(Collectors.toList());

        // Draw each white key.
        for (int i = 0; i < 12; i++) {
            boolean active = notes.contains(new Note(Pitch.fromNoteNumber(i), octave, 1));

            Rectangle2D keyRect = pitchIndexToWhiteKeyRect(i, octave);
            if (keyRect != null) {
                // This is a white key.
                drawKey(g2, keyRect, Color.white, active);
            }
        }

        // Draw each black key.
        for (int i = 0; i < 12; i++) {
            boolean active = notes.contains(new Note(Pitch.fromNoteNumber(i), octave, 1));

            Rectangle2D keyRect = pitchIndexToBlackKeyRect(i, octave);
            if (keyRect != null) {
                // This is a black key.
                drawKey(g2, keyRect, Color.black, active);
            }
        }
    }

    /**
     * Draws a key of the piano.
     *
     * @param g2            The 2D graphics instance to draw with.
     * @param key           The key to draw, with the correct x, y, w, h values.
     * @param inactiveColor The color to draw if the key is not active. Typically, black or white.
     * @param active        If the key is being played. If true, the key will be drawn yellow.
     */
    private void drawKey(Graphics2D g2, Rectangle2D key, Color inactiveColor, boolean active) {
        // Save the old settings.
        Color oldColor = g2.getColor();
        Stroke oldStroke = g2.getStroke();

        // Draw the background.
        if (active) {
            g2.setColor(Color.orange);
        } else {
            g2.setColor(inactiveColor);
        }
        g2.fill(key);

        // Draw the outline.
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(1));
        g2.draw(key);

        // Restore the old color.
        g2.setColor(oldColor);
        g2.setStroke(oldStroke);
    }

    private Rectangle2D pitchIndexToWhiteKeyRect(int index, int octave) {
        int octaveX = OCTAVE_WIDTH * octave;
        switch (index) {
            case 0:
                return new Rectangle2D.Double(octaveX,
                        0, WHITE_KEY_WIDTH, WHITE_KEY_HEIGHT);
            case 2:
                return new Rectangle2D.Double(octaveX + WHITE_KEY_WIDTH,
                        0, WHITE_KEY_WIDTH, WHITE_KEY_HEIGHT);
            case 4:
                return new Rectangle2D.Double(octaveX + (2 * WHITE_KEY_WIDTH),
                        0, WHITE_KEY_WIDTH, WHITE_KEY_HEIGHT);
            case 5:
                return new Rectangle2D.Double(octaveX + (3 * WHITE_KEY_WIDTH),
                        0, WHITE_KEY_WIDTH, WHITE_KEY_HEIGHT);
            case 7:
                return new Rectangle2D.Double(octaveX + (4 * WHITE_KEY_WIDTH),
                        0, WHITE_KEY_WIDTH, WHITE_KEY_HEIGHT);
            case 9:
                return new Rectangle2D.Double(octaveX + (5 * WHITE_KEY_WIDTH),
                        0, WHITE_KEY_WIDTH, WHITE_KEY_HEIGHT);
            case 11:
                return new Rectangle2D.Double(octaveX + (6 * WHITE_KEY_WIDTH),
                        0, WHITE_KEY_WIDTH, WHITE_KEY_HEIGHT);
            default:
                return null;
        }
    }

    private Rectangle2D pitchIndexToBlackKeyRect(int index, int octave) {
        int octaveX = OCTAVE_WIDTH * octave;
        switch (index) {
            case 1:
                return new Rectangle2D.Double(octaveX + WHITE_KEY_WIDTH - HALF_BLACK_KEY_WIDTH,
                        0, BLACK_KEY_WIDTH, BLACK_KEY_HEIGHT);
            case 3:
                return new Rectangle2D.Double(octaveX + (2 * WHITE_KEY_WIDTH) - HALF_BLACK_KEY_WIDTH,
                        0, BLACK_KEY_WIDTH, BLACK_KEY_HEIGHT);
            case 6:
                return new Rectangle2D.Double(octaveX + (4 * WHITE_KEY_WIDTH) - HALF_BLACK_KEY_WIDTH,
                        0, BLACK_KEY_WIDTH, BLACK_KEY_HEIGHT);
            case 8:
                return new Rectangle2D.Double(octaveX + (5 * WHITE_KEY_WIDTH) - HALF_BLACK_KEY_WIDTH,
                        0, BLACK_KEY_WIDTH, BLACK_KEY_HEIGHT);
            case 10:
                return new Rectangle2D.Double(octaveX + (6 * WHITE_KEY_WIDTH) - HALF_BLACK_KEY_WIDTH,
                        0, BLACK_KEY_WIDTH, BLACK_KEY_HEIGHT);
            default:
                return null;
        }
    }
}
