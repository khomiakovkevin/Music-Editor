package cs3500.music.provider.view;

import javax.sound.midi.MidiUnavailableException;

/**
 * A factory that returns views based on a string.
 */
public class ViewFactory {

    /**
     * Constructs and returns a MusicView.
     *
     * @param viewName One of "console", "visual", or "midi".
     * @return An instance of MusicView.
     * @throws MidiUnavailableException If MIDI initialization failed.
     */
    public static MusicView getView(String viewName) throws MidiUnavailableException {
        switch (viewName) {
            case "console":
                return new MusicConsoleView(System.out);
            case "visual":
                return new cs3500.music.provider.view.GuiViewFrame();
            case "midi":
                return new MidiView();
            case "composite":
                return new CompositeMusicView(new cs3500.music.provider.view.GuiViewFrame(), new MidiView());
            default:
                throw new RuntimeException("Invalid view name");
        }
    }
}
