package cs3500.music.provider.view;

import cs3500.music.provider.model.MusicOperations;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * N is the parameter for a variation of the Note class
 * Interface for views for music compositions.
 */
public interface MusicView<N> {

    /**
     * Set the current size of the frame for the scrollbars to update.
     *
     * @param width  - Canvas Width.
     * @param height - Canvas Height.
     */
    void setCanvasSize(int width, int height);

    /**
     * Sets the model that is rendered by the view.
     *
     * @param model The model to set.
     */
    void setModel(MusicOperations<N> model);

    /**
     * Sets the current beat to render on the view, if the view supports it.
     *
     * @param beat The beat number to set.
     */
    void setCurrentBeat(int beat);

    /**
     * Update the rendering of the view, called if the model has been updated.
     */
    void drawMusic();

    /**
     * Adds a KeyListener to the view, to respond to key pressed, if the view supports it.
     *
     * @param keyListener The listener to add.
     */
    void setKeyListener(KeyListener keyListener);

    /**
     * Adds a MouseListener to the view, to respond to mouse events, if the view supports it.
     *
     * @param mouseListener The listener to add.
     */
    void setMouseListener(MouseListener mouseListener);

    /**
     * From an x/y location, return the Note that is at that location.
     *
     * @param x The x position.
     * @param y The y position.
     * @return The note at the location, or null if that location does not represent a note. The
     * returned always has a duration of 1.
     */
    N getNoteAtLocation(int x, int y);
}
