package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by 28609 on 3/3/2017.
 */
public class MusicModel1Test {

    @Test
    public void getPitchName() throws Exception {
        MusicModel now = MusicModel1Creator.create(60);
        assertEquals("C1", now.getPitchName(0));
        assertEquals("C#1", now.getPitchName(1));
        assertEquals("B1", now.getPitchName(11));
        assertEquals("C2", now.getPitchName(12));


    }

    @org.junit.Test
    public void getMusicState() throws Exception {

        MusicModel now = MusicModel1Creator.create(60);

        now.addNote(34, 37, 1, 3, 127);
        now.addNote(13, 15, 1, 5, 127);

        assertEquals(
                "    D#1  E1   F1   \n" +
                        "000                \n" +
                        "001                \n" +
                        "002                \n" +
                        "003                \n" +
                        "004                \n" +
                        "005                \n" +
                        "006                \n" +
                        "007                \n" +
                        "008                \n" +
                        "009                \n" +
                        "010                \n" +
                        "011                \n" +
                        "012                \n" +
                        "013           X    \n" +
                        "014           |    \n" +
                        "015           |    \n" +
                        "016                \n" +
                        "017                \n" +
                        "018                \n" +
                        "019                \n" +
                        "020                \n" +
                        "021                \n" +
                        "022                \n" +
                        "023                \n" +
                        "024                \n" +
                        "025                \n" +
                        "026                \n" +
                        "027                \n" +
                        "028                \n" +
                        "029                \n" +
                        "030                \n" +
                        "031                \n" +
                        "032                \n" +
                        "033                \n" +
                        "034 X              \n" +
                        "035 |              \n" +
                        "036 |              \n" +
                        "037 |              \n" +
                        "038                \n" +
                        "039                \n" +
                        "040                \n" +
                        "041                \n" +
                        "042                \n" +
                        "043                \n" +
                        "044                \n" +
                        "045                \n" +
                        "046                \n" +
                        "047                \n" +
                        "048                \n" +
                        "049                \n" +
                        "050                \n" +
                        "051                \n" +
                        "052                \n" +
                        "053                \n" +
                        "054                \n" +
                        "055                \n" +
                        "056                \n" +
                        "057                \n" +
                        "058                \n" +
                        "059                ",
                now.getMusicState());

    }

    @org.junit.Test
    public void combine() throws Exception {
        MusicModel now1 = MusicModel1Creator.create(30);
        MusicModel now2 = MusicModel1Creator.create(30);
        now1.addNote(23, 26, 1, 3, 127);
        now2.addNote(13, 15, 1, 5, 127);
        MusicModel now3 = now1.combine(now2);
        assertEquals(
                "    D#1  E1   F1   \n" +
                        "000                \n" +
                        "001                \n" +
                        "002                \n" +
                        "003                \n" +
                        "004                \n" +
                        "005                \n" +
                        "006                \n" +
                        "007                \n" +
                        "008                \n" +
                        "009                \n" +
                        "010                \n" +
                        "011                \n" +
                        "012                \n" +
                        "013           X    \n" +
                        "014           |    \n" +
                        "015           |    \n" +
                        "016                \n" +
                        "017                \n" +
                        "018                \n" +
                        "019                \n" +
                        "020                \n" +
                        "021                \n" +
                        "022                \n" +
                        "023 X              \n" +
                        "024 |              \n" +
                        "025 |              \n" +
                        "026 |              \n" +
                        "027                \n" +
                        "028                \n" +
                        "029                ",
                now3.getMusicState());

    }

    @org.junit.Test
    public void addNote() throws Exception {
        MusicModel now1 = MusicModel1Creator.create(30);
        MusicModel now2 = MusicModel1Creator.create(30);
        now1.addNote(24, 26, 1, 13, 127);
        now2.addNote(24, 26, 1, 13, 127);
        assertEquals(now1.getMusicState(), now2.getMusicState());


    }

    @org.junit.Test
    public void remove() throws Exception {
        MusicModel now1 = MusicModel1Creator.create(30);
        MusicModel now2 = MusicModel1Creator.create(30);

        now2.addNote(13, 15, 1, 5, 127);
        now2.remove(5, 13);
        assertEquals(now1.getMusicState(), now2.getMusicState());
    }

    @org.junit.Test
    public void edit() throws Exception {
    /*no idea so far!*/
    }

    @org.junit.Test
    public void addMusic() throws Exception {
        MusicModel now1 = MusicModel1Creator.create(30);
        MusicModel now2 = MusicModel1Creator.create(30);
        MusicModel now4 = MusicModel1Creator.create(60);
        now4.addNote(24, 27, 1, 12, 127);
        now4.addNote(54, 57, 1, 13, 127);
        now1.addNote(24, 27, 1, 12, 127);
        now2.addNote(24, 27, 1, 13, 127);
        MusicModel now3 = now1.addMusic(now2);
        assertEquals(now3.getMusicState(), now4.getMusicState());

    }

}