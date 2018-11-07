package cs3500.music.provider.view;

import cs3500.music.provider.model.MusicOperations;
import cs3500.music.provider.model.Note;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * A window that renders music as a timeline and a piano.
 */
public class GuiViewFrame extends JFrame implements MusicView<Note> {

    private final TimelinePanel timelinePanel;
    private final PianoPanel pianoPanel;
    private final JScrollPane timelineScrollable;

    /**
     * Creates new GuiView.
     */
    public GuiViewFrame() {
        // Create component panels.
        JPanel containerPanel = new JPanel();
        this.timelinePanel = new TimelinePanel();
        this.pianoPanel = new PianoPanel();
        this.timelineScrollable = new JScrollPane(timelinePanel);

        // Create the layout.
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.add(this.timelineScrollable);
        containerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        containerPanel.add(this.pianoPanel);
        containerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Setup frame.
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().add(containerPanel);
        this.pack();
        this.setVisible(true);

    }

    @Override
    public void setCanvasSize(int width, int height) {
        timelinePanel.setPreferredSize(new Dimension(width, height));
        timelinePanel.revalidate();
        timelinePanel.repaint();
    }


    @Override
    public void setModel(MusicOperations<Note> model) {
        this.timelinePanel.setModel(model);
        this.pianoPanel.setModel(model);
    }

    @Override
    public void setCurrentBeat(int beat) {
        this.timelinePanel.setCurrentBeat(beat);
        this.pianoPanel.setCurrentBeat(beat);
    }

    @Override
    public void drawMusic() {
        this.timelineScrollable.repaint();
        this.timelineScrollable.getViewport().scrollRectToVisible(timelinePanel.getCurrentBeatRect());
        this.pianoPanel.repaint();
        //this.pack();
    }

    @Override
    public void setKeyListener(KeyListener keyListener) {
        this.addKeyListener(keyListener);
    }

    @Override
    public void setMouseListener(MouseListener mouseListener) {
        this.pianoPanel.addMouseListener(mouseListener);
    }

    @Override
    public Note getNoteAtLocation(int x, int y) {

        return this.pianoPanel.getNoteAtLocation(x, y);
    }
}
