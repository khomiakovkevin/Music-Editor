package cs3500.music.provider.model;

import java.util.List;

/**
 * Represents the operations that may be done on a piece of music.
 */
public interface MusicOperations<N> {

    /**
     * Adds a note to the piece.
     *
     * @param note      The note to play.
     * @param startBeat The beat to start playing it at.
     * @throws IllegalArgumentException   Thrown if {@code Note} is null, {@code startBeat} is less than
     *                                    zero.
     * @throws NoteAlreadyExistsException Thrown if the note to be added already exists in the piece
     *                                    of music.
     */
    void addNote(N note, int startBeat) throws IllegalArgumentException, NoteAlreadyExistsException;

    /**
     * Removes a note from the piece.
     *
     * @param note      The note to remove.
     * @param startBeat The beat the note to remove starts at.
     * @throws IllegalArgumentException Thrown if a note to be removed does not exist.
     */
    void removeNote(N note, int startBeat) throws IllegalArgumentException;

    /**
     * Set the piece's tempo in beats per minute.
     *
     * @param microsecondsPerBeat Accept a microseconds per beat input
     */
    void setTempo(int microsecondsPerBeat);

    /**
     * Replaces a note at a certain beat with another.
     *
     * @param oldNote   The note to remove from the piece.
     * @param newNote   The new note to add to the piece.
     * @param startBeat The beat where the replacement should take place.
     * @throws IllegalArgumentException   Thrown if {@code oldNote} does not exist, {@code newNote} is
     *                                    null, or {@code startBeat} is less than zero.
     * @throws NoteAlreadyExistsException Thrown if the note to be added already exists in the piece
     *                                    of music.
     */
    void replaceNote(N oldNote, N newNote, int startBeat) throws IllegalArgumentException,
            NoteAlreadyExistsException;

    /**
     * @return The total number of beats the piece takes to play.
     */
    int getMusicDuration();

    /**
     * @return Get the tempo in Beats per minute.
     */
    int getTempo();

    /**
     * Gets the beats that start at a certain beat.
     *
     * @param beat The beat to find notes at.
     * @return A list of notes.
     */
    List<N> getNotesStartAtBeat(int beat);

    /**
     * Gets the beats that are sustained at a certain beat.
     *
     * @param beat The beat to find notes at.
     * @return A list of notes.
     */
    List<N> getNotesSustainedAtBeat(int beat);

    /**
     * Gets the note which has the highest octave and pitch.
     *
     * @return A note object.
     */
    N getHighestNote();

    /**
     * Get the note which has the lowest octave and pitch.
     *
     * @return A note object.
     */
    N getLowestNote();

}
