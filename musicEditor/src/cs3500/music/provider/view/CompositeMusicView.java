package cs3500.music.provider.view;

import cs3500.music.provider.model.MusicOperations;
import cs3500.music.provider.model.Note;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * Created by Agamemnon on 3/31/2017.
 */
public class CompositeMusicView implements MusicView<Note> {
    private final MusicView[] views;

    public CompositeMusicView(MusicView... views) {
        this.views = views;
    }

    /**
     * Set the current size of the frame for the scrollbars to update.
     *
     * @param width  - Canvas Width.
     * @param height - Canvas Height.
     */
    @Override
    public void setCanvasSize(int width, int height) {
        // Non-op - Not necessary for this view
    }

    @Override
    public void setModel(MusicOperations<Note> model) {
        for (MusicView view : views) {
            view.setModel(model);
        }
    }

    @Override
    public void setCurrentBeat(int beat) {
        for (MusicView view : views) {
            view.setCurrentBeat(beat);
        }
    }

    @Override
    public void drawMusic() {
        for (MusicView view : views) {
            view.drawMusic();
        }
    }

    @Override
    public void setKeyListener(KeyListener keyListener) {
        for (MusicView view : views) {
            view.setKeyListener(keyListener);
        }
    }

    @Override
    public void setMouseListener(MouseListener mouseListener) {
        for (MusicView view : views) {
            view.setMouseListener(mouseListener);
        }
    }

    /**
     * From an x/y location, return the Note that is at that location.
     * Note that for CompositeMusicView, the first non-null Note from a child MusicView is returned.
     * Therefore, you should not have more than one MusicView in a CompositeView that implements
     * mouse handling.
     *
     * @param x The x position.
     * @param y The y position.
     * @return The note at the location, or null if that location does not represent a note.
     */
    @Override
    public Note getNoteAtLocation(int x, int y) {
        Note note = null;
        for (MusicView view : views) {
            note = (Note) view.getNoteAtLocation(x, y);
            if (note != null) {
                break;
            }
        }
        return note;
    }
}
