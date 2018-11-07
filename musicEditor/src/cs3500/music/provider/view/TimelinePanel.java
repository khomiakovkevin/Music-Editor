package cs3500.music.provider.view;

import cs3500.music.provider.model.MusicOperations;
import cs3500.music.provider.model.Note;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A view that draws a MusicOperations as a timeline.
 */
public class TimelinePanel extends JPanel {

    // Constants
    private static final int LEFT_GUTTER_WIDTH = 30;
    private static final int TOP_GUTTER_HEIGHT = 25;
    private static final int VERTICAL_PIXELS_PER_NOTE = 15;
    private static final int HORIZONTAL_PIXELS_PER_BEAT = 25;

    // Members
    private MusicOperations<Note> model;
    private int currentBeat;

    TimelinePanel() {
        model = null;
        currentBeat = 0;
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
        g2.setFont(g2.getFont().deriveFont(16));

        // Get limits of the model.
        int duration = model.getMusicDuration();
        Note lowestNote = model.getLowestNote();
        Note highestNote = model.getHighestNote();

        if (lowestNote == null || highestNote == null) {
            return;
        }

        // Calculate some points for drawing, and set them as the size of this panel.
        int bottomY = getTopYForNote(highestNote.getNoteNumber(), lowestNote.getNoteNumber())
                + VERTICAL_PIXELS_PER_NOTE;
        int rightX = LEFT_GUTTER_WIDTH + (HORIZONTAL_PIXELS_PER_BEAT * duration);
        setPreferredSize(new Dimension(rightX, bottomY));
        setSize(rightX, bottomY);

        // Draw all the notes.
        for (int beat = 0; beat < duration; beat++) {
            List<Note> notes = model.getNotesStartAtBeat(beat);
            for (Note note : notes) {
                drawNote(g2, highestNote, beat, note);
            }
        }

        // Draw the container box.
        g2.drawRect(LEFT_GUTTER_WIDTH, TOP_GUTTER_HEIGHT,
                rightX - LEFT_GUTTER_WIDTH, bottomY - TOP_GUTTER_HEIGHT);

        // Draw the note labels and dividers.
        for (int i = lowestNote.getNoteNumber(); i <= highestNote.getNoteNumber(); i++) {
            Note curNote = new Note(i, 1);
            int noteBottomY = getTopYForNote(highestNote.getNoteNumber(), i) + VERTICAL_PIXELS_PER_NOTE;

            // Draw the note label.
            g2.drawString(curNote.toString(), 0, noteBottomY);

            // Draw the note divider.
            g2.drawLine(LEFT_GUTTER_WIDTH, noteBottomY, rightX, noteBottomY);
        }

        // Draw the currentBeat labels and dividers.
        for (int beat = 4; beat < duration; beat += 4) {
            int beatLeftX = getLeftXForBeat(beat);
            g2.drawString(Integer.toString(beat), beatLeftX, TOP_GUTTER_HEIGHT - 3);
            g2.drawLine(beatLeftX, TOP_GUTTER_HEIGHT, beatLeftX, bottomY);
        }

        // Draw the current currentBeat line.
        int beatX = getLeftXForBeat(this.currentBeat);
        g2.setColor(Color.red);
        g2.drawLine(beatX, TOP_GUTTER_HEIGHT, beatX, bottomY);
    }

    public void setModel(MusicOperations<Note> model) {
        this.model = model;
    }

    public void setCurrentBeat(int currentBeat) {
        this.currentBeat = currentBeat;
    }

    /**
     * Calculate the location of the current beat as a rectangle.
     *
     * @return - A rectangle location of the current beat.
     */
    public Rectangle getCurrentBeatRect() {
        int beatX = getLeftXForBeat(this.currentBeat);
        return new Rectangle(beatX, 1, 1, 1);

    }

    private int getTopYForNote(int highestNote, int note) {
        int noteIndex = highestNote - note;
        return TOP_GUTTER_HEIGHT + (VERTICAL_PIXELS_PER_NOTE * noteIndex);
    }

    private int getLeftXForBeat(int beat) {
        return LEFT_GUTTER_WIDTH + (HORIZONTAL_PIXELS_PER_BEAT * beat);
    }

    private void drawNote(Graphics2D g2, Note highestNote, int startBeat, Note note) {
        // Save the old color.
        Color oldColor = g2.getColor();

        // Calculate coordinates.
        int leftX = getLeftXForBeat(startBeat);
        int rightStartX = leftX + HORIZONTAL_PIXELS_PER_BEAT;
        int rightSustainX = leftX + (HORIZONTAL_PIXELS_PER_BEAT * note.getDuration());
        int topY = getTopYForNote(highestNote.getNoteNumber(), note.getNoteNumber());
        int bottomY = topY - VERTICAL_PIXELS_PER_NOTE;

        // Draw the start portion of the note.
        g2.setColor(Color.black);
        g2.fillRect(leftX, topY, rightStartX - leftX, topY - bottomY);

        // Draw the sustained portion of the note.
        g2.setColor(Color.green);
        g2.fillRect(rightStartX, topY, rightSustainX - rightStartX, topY - bottomY);

        // Restore the old color.
        g2.setColor(oldColor);
    }
}
