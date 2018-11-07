package cs3500.music.provider.model;

import java.util.Objects;

/**
 * A Note represents a pitch in an octave that lasts for a certain number of duration.
 * <p>
 * <p>Notes are immutable.
 */
public class Note implements Comparable<Note> {

    private final int noteNumber;
    private final int duration;
    private final int volume;

    /**
     * Creates a new note from a MIDI note number.
     *
     * @param noteNumber The MIDI note number. Octave must be between 0 and 10 (inclusive).
     * @param duration   The number of beats the note should last. Must be positive.
     * @throws IllegalArgumentException Thrown if any of the above checks fail.
     */
    public Note(int noteNumber, int duration) throws IllegalArgumentException {
        if (noteNumber < 0) {
            throw new IllegalArgumentException("note number must be positive");
        }
        if (octaveFromNoteNumber(noteNumber) < 0 || octaveFromNoteNumber(noteNumber) > 10) {
            throw new IllegalArgumentException("octave must be between 0 and 10 (inclusive)");
        }
        if (duration <= 0) {
            throw new IllegalArgumentException("duration must be greater than zero");
        }

        this.volume = 5;
        this.noteNumber = noteNumber;
        this.duration = duration;
    }

    /**
     * Creates a new note.
     *
     * @param pitch    The pitch of the note. Must be non-null.
     * @param octave   The octave of the note. Must be between 0 and 10 (inclusive).
     * @param duration The number of beats the note should last. Must be positive.
     * @throws IllegalArgumentException Thrown if any of the above checks fail.
     */
    public Note(Pitch pitch, int octave, int duration) throws IllegalArgumentException {
        if (pitch == null) {
            throw new IllegalArgumentException("Pitch may not be null");
        }
        if (octave < 0 || octave > 10) {
            throw new IllegalArgumentException("octave must be between 0 and 10 (inclusive)");
        }
        if (duration < 0) {
            throw new IllegalArgumentException("duration must be greater than zero");
        }

        this.noteNumber = toNoteNumber(pitch, octave);

        this.volume = 5;

        this.duration = duration;
    }

    /**
     * Creates a new note.
     *
     * @param noteNumber The MIDI note number. Octave must be between 0 and 10 (inclusive).
     * @param duration   The number of beats the note should last. Must be positive.
     * @throws IllegalArgumentException Thrown if any of the above checks fail.
     */
    public Note(int noteNumber, int duration, int volume) throws IllegalArgumentException {
        if (octaveFromNoteNumber(noteNumber) < 0 || octaveFromNoteNumber(noteNumber) > 10) {
            throw new IllegalArgumentException("octave must be between 0 and 10 (inclusive)");
        }

        if (duration <= 0) {
            throw new IllegalArgumentException("duration must be greater than zero");
        }

        if (volume <= 0) {
            throw new IllegalArgumentException("Volume must be greater than 0");
        }

        this.noteNumber = noteNumber;

        this.volume = volume;

        this.duration = duration;
    }

    /**
     * @return The pitch of the note.
     */
    public Pitch getPitch() {
        return pitchFromNoteNumber(noteNumber);
    }

    /**
     * @return The volume of the note.
     */
    public int getVolume() {
        return volume;
    }

    /**
     * @return The octave that the note's pitch is in.
     */
    public int getOctave() {
        return octaveFromNoteNumber(noteNumber);
    }

    /**
     * @return The MIDI note number.
     */
    public int getNoteNumber() {
        return noteNumber;
    }

    /**
     * @return The number of duration the note lasts.
     */
    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return String.format("%s%d", getPitch(), getOctave());
    }

    @Override
    public int compareTo(Note o) {
        return Integer.compare(this.getNoteNumber(), o.getNoteNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Note note = (Note) o;

        return (getPitch() == note.getPitch())
                && (getOctave() == note.getOctave());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPitch(), getOctave());
    }

    private static int toNoteNumber(Pitch pitch, int octave) {
        return (octave * 12) + pitch.toNoteNumber();
    }

    private static Pitch pitchFromNoteNumber(int number) {
        return Pitch.fromNoteNumber(number % 12);
    }

    private static int octaveFromNoteNumber(int number) {
        return number / 12;
    }
}
