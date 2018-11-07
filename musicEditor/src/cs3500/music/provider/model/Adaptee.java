package cs3500.music.provider.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.model.Beat;
import cs3500.music.model.MusicModel;

/**
 * Created by 28609 on 4/14/2017.
 */
public class Adaptee implements MusicOperations<Note> {
  MusicModel mu;

  public Adaptee(MusicModel mu) {
    this.mu = mu;
  }

  /**
   * Adds a note to the piece.
   *
   * @param note      The note to play.
   * @param startBeat The beat to start playing it at.
   * @throws IllegalArgumentException   Thrown if {@code Note} is null, {@code startBeat} is less
   *                                    than zero.
   * @throws NoteAlreadyExistsException Thrown if the note to be added already exists in the piece
   *                                    of music.
   */
  @Override
  public void addNote(Note note, int startBeat) throws
          IllegalArgumentException, NoteAlreadyExistsException {
    mu.addNote(startBeat, startBeat + note.getDuration(), 1,
            note.getPitch().toNoteNumber() + note.getOctave() * 12, 5);

  }

  /**
   * Removes a note from the piece.
   *
   * @param note      The note to remove.
   * @param startBeat The beat the note to remove starts at.
   * @throws IllegalArgumentException Thrown if a note to be removed does not exist.
   */
  @Override
  public void removeNote(Note note, int startBeat) throws IllegalArgumentException {
    mu.remove(note.getPitch().toNoteNumber() + note.getOctave() * 12, startBeat);

  }

  /**
   * Set the piece's tempo in beats per minute.
   *
   * @param microsecondsPerBeat Accept a microseconds per beat input
   */
  @Override
  public void setTempo(int microsecondsPerBeat) {
    mu.setTempo(microsecondsPerBeat);

  }

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
  @Override
  public void replaceNote(Note oldNote, Note newNote, int startBeat)
          throws IllegalArgumentException, NoteAlreadyExistsException {
    mu.remove(oldNote.getPitch().toNoteNumber() + oldNote.getOctave() * 12, startBeat);
    mu.addNote(startBeat, startBeat + newNote.getDuration(), 1,
            newNote.getPitch().toNoteNumber() + newNote.getOctave() * 12, 5);

  }

  /**
   * @return The total number of beats the piece takes to play.
   */
  @Override
  public int getMusicDuration() {
    return mu.number();
  }

  /**
   * @return Get the tempo in Beats per minute.
   */
  @Override
  public int getTempo() {
    return mu.getTempo();
  }

  /**
   * Gets the beats that start at a certain beat.
   *
   * @param beat The beat to find notes at.
   * @return A list of notes.
   */
  @Override
  public List<Note> getNotesStartAtBeat(int beat) {
    List<Note> lon = new ArrayList<Note>();
    for (int i = 0; i < 128; i++) {
      Beat tem = mu.map().get(i).get(beat);
      if (tem != null && tem.type == 1) {
        Note no = new Note(Pitch.fromNoteNumber(tem.pitch % 12), tem.pitch / 12, tem.duration);
        lon.add(no);

      }
    }
    return lon;
  }

  /**
   * Gets the beats that are sustained at a certain beat.
   *
   * @param beat The beat to find notes at.
   * @return A list of notes.
   */
  @Override
  public List<Note> getNotesSustainedAtBeat(int beat) {
    List<Note> lon = new ArrayList<Note>();
    for (int i = 0; i < 128; i++) {
      Beat tem = mu.map().get(i).get(beat);
      if (tem != null && tem.type == 0) {
        Note no = new Note(Pitch.fromNoteNumber(tem.pitch % 12), tem.pitch / 12, tem.duration);
        lon.add(no);

      }
    }
    return lon;

  }

  /**
   * Gets the note which has the highest octave and pitch.
   *
   * @return A note object.
   */
  @Override
  public Note getHighestNote() {
    for (int i = 127; i >= 0; i--) {
      if (!mu.map().get(i).isEmpty()) {
        for (int j = 0; j < mu.number(); j++) {
          Beat tem = mu.map().get(i).get(j);
          if (tem != null) {
            Note no = new Note(Pitch.fromNoteNumber(tem.pitch % 12), tem.pitch / 12, tem.duration);
            return no;
          }
        }
      }
    }
    throw new IllegalArgumentException("no such note");
  }

  /**
   * Get the note which has the lowest octave and pitch.
   *
   * @return A note object.
   */
  @Override
  public Note getLowestNote() {
    for (int i = 0; i < 128; i++) {
      if (!mu.map().get(i).isEmpty()) {
        for (int j = 0; j < mu.number(); j++) {
          Beat tem = mu.map().get(i).get(j);
          if (tem != null) {
            Note no = new Note(Pitch.fromNoteNumber(tem.pitch % 12), tem.pitch / 12, tem.duration);
            return no;
          }
        }
      }
    }
    throw new IllegalArgumentException("no such note");
  }
}
