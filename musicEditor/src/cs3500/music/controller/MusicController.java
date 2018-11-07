package cs3500.music.controller;

import cs3500.music.view.IView;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;

/**
 * Created by 28609 on 3/17/2017. the controller interface has a method buildView();
 * when we run the main method in th MusicEditor class.
 * a controller will be built to control the following activity.
 */
public interface MusicController {


    /**
     * a method to build a view according to different filename "mystery-1.txt" or "mystery-3.txt",
     * according to different type: "console", "visual", and "midi" and.
     */
    IView buildView() throws IOException, InvalidMidiDataException;

}
