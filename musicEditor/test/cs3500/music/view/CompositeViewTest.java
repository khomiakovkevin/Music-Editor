package cs3500.music.view;

import org.junit.Test;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by 28609 on 4/5/2017. I create a Mock CompositeView to test. I use a List of string to
 * represent a list keyevent. eg. {s,s,s,s,home, end} means I press s key 4 times,
 * home key one time and end end key one time.
 */
public class CompositeViewTest {
    public class MockCompositeView implements IView {
        boolean forSKey;/* true for the view is playing*/
        boolean forHomeKey; /* true for the view is currently on home*/
        boolean forEndKey; /* true for the view is currently on end*/

        List<String> Keyevent;

        MockCompositeView(boolean forSKey, boolean forHomeKey, boolean forEndKey,
                          List<String> Keyevent) {
            this.forSKey = forSKey;
            this.forHomeKey = forHomeKey;
            this.forEndKey = forEndKey;
            this.Keyevent = Keyevent;

        }

        /**
         * the method to build a view according to IView implementations
         *
         * @throws InvalidMidiDataException if there is invalid Midi data.
         * @throws MidiUnavailableException if Midi is unavailable.
         */
        @Override
        public void initialize() throws InvalidMidiDataException, MidiUnavailableException {
            for (String s : Keyevent) {
                if (s.equals("s")) {
                    forSKey = !forSKey;
                } else if (s.equals("home")) {
                    if (forHomeKey) {
                        forEndKey = false;
                    } else {
                        forHomeKey = true;
                        forEndKey = false;
                    }
                } else {
                    if (forEndKey) {
                        forHomeKey = false;
                    } else {
                        forEndKey = true;
                        forHomeKey = false;
                    }
                }
            }

        }

    }

    /*
     * to test the mockCompositeView make right response to mock Keyevent.
     */
    @Test
    public void initialize1() throws Exception {

        List<String> los1 = new ArrayList<>();
        los1.add("s");
        los1.add("s");
        los1.add("home");
        los1.add("end");
        MockCompositeView mc = new MockCompositeView(true, true,
                false, los1);
        mc.initialize();
        assertEquals(true, mc.forSKey);
        assertEquals(true, mc.forEndKey);
        assertEquals(false, mc.forHomeKey);

    }

    /*
    * to test the mockCompositeView make right response to mock Keyevent.
    */
    @Test
    public void initialize2() throws Exception {

        List<String> los1 = new ArrayList<>();
        los1.add("s");
        los1.add("home");
        los1.add("end");
        los1.add("home");
        MockCompositeView mc = new MockCompositeView(true, true,
                false, los1);
        mc.initialize();
        assertEquals(false, mc.forSKey);
        assertEquals(false, mc.forEndKey);
        assertEquals(true, mc.forHomeKey);

    }

}