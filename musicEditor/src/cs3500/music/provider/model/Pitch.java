package cs3500.music.provider.model;

/**
 * Enum representing the different pitches that a note may have.
 */
public enum Pitch {
    C("C", 0),
    CSHARP("C#", 1),
    D("D", 2),
    DSHARP("D#", 3),
    E("E", 4),
    F("F", 5),
    FSHARP("F#", 6),
    G("G", 7),
    GSHARP("G#", 8),
    A("A", 9),
    ASHARP("A#", 10),
    B("B", 11);

    private String text;
    private int number;

    Pitch(String text, int number) {
        this.text = text;
        this.number = number;
    }

    /**
     * @return The note number of the note at octave 0. (ie, C = 0, C# = 1, etc)
     */
    public int toNoteNumber() {
        return number;
    }

    /**
     * @param number The note number of the pitch at octave 0. (ie, C = 0, C# = 1, etc)
     * @return The Pitch corresponding to the given number.
     * @throws IllegalArgumentException When {@code number} is outside the range [0, 11].
     */
    public static Pitch fromNoteNumber(int number) throws IllegalArgumentException {
        switch (number) {
            case 0:
                return C;
            case 1:
                return CSHARP;
            case 2:
                return D;
            case 3:
                return DSHARP;
            case 4:
                return E;
            case 5:
                return F;
            case 6:
                return FSHARP;
            case 7:
                return G;
            case 8:
                return GSHARP;
            case 9:
                return A;
            case 10:
                return ASHARP;
            case 11:
                return B;
            default:
                throw new IllegalArgumentException("note number must be in range [0, 11]");
        }
    }

    @Override
    public String toString() {
        return text;
    }
}
