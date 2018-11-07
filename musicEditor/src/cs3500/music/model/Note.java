package cs3500.music.model;

/**
 * Created by 28609 on 3/1/2017.
 * this class to represent a note, it has three fields: pitch, instrument, duration.
 */
public class Note {
    private int pitch;
    private int instrument;
    private int duration;
    private int volume;

    /**
     * the constructor of a note.
     *
     * @param pitch      a number to represent a pitch;
     * @param instrument a number to represent an instrument;
     * @param duration   an int to represent duration counted as beat.
     * @param volume     an int to represent how loud this note should be played.
     */
    public Note(int pitch, int instrument, int duration, int volume) {
        this.pitch = pitch;
        this.instrument = instrument;
        this.duration = duration;
        this.volume = volume;

    }
}
