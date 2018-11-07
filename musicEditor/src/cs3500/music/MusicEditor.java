package cs3500.music;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.controller.Controller1;
import cs3500.music.controller.MusicController;
import cs3500.music.view.IView;


/**
 * input a file name such as "mystery-3.txt" and a view way such as "console" or "visual" or "midi".
 * to get a certain view you want.
 */
public class MusicEditor {
  /**
   * the main method for our program.
   *
   * @param args represents the arguments
   * @throws IOException              an Exception
   * @throws InvalidMidiDataException an Exception
   */
  public static void main(String[] args) throws IOException, InvalidMidiDataException {

    MusicController controller = new Controller1(new InputStreamReader(System.in), System.out);
    IView view = controller.buildView();
    if (view != null) {
      try {
        view.initialize();
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
      }
    }

  }
}
