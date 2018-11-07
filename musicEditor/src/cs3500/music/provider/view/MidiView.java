package cs3500.music.provider.view;

import cs3500.music.provider.model.MusicOperations;
import cs3500.music.provider.model.Note;

import javax.sound.midi.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * A view that plays a MusicOperations through a MIDI controller.
 */
public class MidiView implements MusicView<Note> {

    private final Receiver receiver;
    private MusicOperations<Note> model;
    private int currentBeat;
    private Synthesizer synth;

    /**
     * Constructs a new MidiView.
     *
     * @throws MidiUnavailableException If getting the MIDI synthesizer or receiver fails.
     */
    public MidiView() throws MidiUnavailableException {
        this.model = null;
        this.currentBeat = 0;

        this.synth = MidiSystem.getSynthesizer();
        this.receiver = synth.getReceiver();
        synth.open();
    }

    /**
     * Convenience constructor for testing.
     * Constructs a MidiView.
     *
     * @param receiver - Takes in a Receiver object;
     */
    public MidiView(Receiver receiver) throws MidiUnavailableException {
        this.model = null;

        synth = MidiSystem.getSynthesizer();
        synth.open();
        this.receiver = receiver;
    }

    private void playNotes(int beat) throws InvalidMidiDataException {
        List<Note> notes = this.model.getNotesStartAtBeat(beat);
        for (Note note : notes) {
            long noteDurationMicroseconds = beatsToMicroseconds(note.getDuration());

            MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, 1, note.getNoteNumber(),
                    note.getVolume());
            MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, 1, note.getNoteNumber(),
                    note.getVolume());

            this.receiver.send(start, 0);
            this.receiver.send(stop,
                    this.synth.getMicrosecondPosition() + noteDurationMicroseconds);
        }
    }

    private long beatsToMicroseconds(int beats) {
        return (long) ((beats / (float) model.getTempo()) * 60000000);
    }

    /**
     * Set the current size of the frame for the scrollbars to update.
     *
     * @param width  - Canvas Width.
     * @param height - Canvas Height.
     */
    @Override
    public void setCanvasSize(int width, int height) {
        //Non-op - not necessary for this view
    }

    @Override
    public void setModel(MusicOperations<Note> model) {
        this.model = model;
    }

    @Override
    public void setCurrentBeat(int beat) {
        this.currentBeat = beat;
    }

    @Override
    public void drawMusic() {
        try {
            playNotes(this.currentBeat);
        } catch (InvalidMidiDataException e) {
            // Sending a MIDI message failed - no way to recover from this.
        }
    }

    @Override
    public void setKeyListener(KeyListener keyListener) {
        // No-op, as this view doesn't have anything to listen to.
        // Except some great music.
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
