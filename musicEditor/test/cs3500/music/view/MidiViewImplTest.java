package cs3500.music.view;

import cs3500.music.controller.Controller1;
import cs3500.music.model.MusicModel;
import org.junit.Test;

import javax.sound.midi.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by 28609 on 3/21/2017.
 */
public class MidiViewImplTest {

    public class MockSequencer implements Sequencer {
        Sequence seq;
        boolean isStarted = false;
        float tempo;

        @Override
        public void setSequence(Sequence sequence) throws InvalidMidiDataException {
            seq = sequence;
        }

        @Override
        public void setSequence(InputStream stream) throws IOException, InvalidMidiDataException {
            //empty
        }

        @Override
        public Sequence getSequence() {
            return this.seq;
        }

        @Override
        public void start() {
            isStarted = true;
        }

        @Override
        public void stop() {
            isStarted = false;
        }

        @Override
        public boolean isRunning() {
            return isStarted;
        }

        @Override
        public void startRecording() {
            //empty
        }

        @Override
        public void stopRecording() {
            //empty
        }

        @Override
        public boolean isRecording() {
            return false;
        }

        @Override
        public void recordEnable(Track track, int channel) {
            //empty
        }

        @Override
        public void recordDisable(Track track) {
            //empty
        }

        @Override
        public float getTempoInBPM() {
            return 0;
        }

        @Override
        public void setTempoInBPM(float bpm) {
            //empty
        }

        @Override
        public float getTempoInMPQ() {
            return 0;
        }

        @Override
        public void setTempoInMPQ(float mpq) {
            this.tempo = mpq;
        }

        @Override
        public void setTempoFactor(float factor) {
            //empty
        }

        @Override
        public float getTempoFactor() {
            return 0;
        }

        @Override
        public long getTickLength() {
            return 0;
        }

        @Override
        public long getTickPosition() {
            return 0;
        }

        @Override
        public void setTickPosition(long tick) {
            //empty
        }

        @Override
        public long getMicrosecondLength() {
            return 0;
        }

        @Override
        public Info getDeviceInfo() {
            return null;
        }

        @Override
        public void open() throws MidiUnavailableException {
            //empty
        }

        @Override
        public void close() {
            //empty
        }

        @Override
        public boolean isOpen() {
            return false;
        }

        @Override
        public long getMicrosecondPosition() {
            return 0;
        }

        @Override
        public int getMaxReceivers() {
            return 0;
        }

        @Override
        public int getMaxTransmitters() {
            return 0;
        }

        @Override
        public Receiver getReceiver() throws MidiUnavailableException {
            return null;
        }

        @Override
        public List<Receiver> getReceivers() {
            return null;
        }

        @Override
        public Transmitter getTransmitter() throws MidiUnavailableException {
            return null;
        }

        @Override
        public List<Transmitter> getTransmitters() {
            return null;
        }

        @Override
        public void setMicrosecondPosition(long microseconds) {
            //empty
        }

        @Override
        public void setMasterSyncMode(SyncMode sync) {
            //empty
        }

        @Override
        public SyncMode getMasterSyncMode() {
            return null;
        }

        @Override
        public SyncMode[] getMasterSyncModes() {
            return new SyncMode[0];
        }

        @Override
        public void setSlaveSyncMode(SyncMode sync) {
            //empty
        }

        @Override
        public SyncMode getSlaveSyncMode() {
            return null;
        }

        @Override
        public SyncMode[] getSlaveSyncModes() {
            return new SyncMode[0];
        }

        @Override
        public void setTrackMute(int track, boolean mute) {
            //empty
        }

        @Override
        public boolean getTrackMute(int track) {
            return false;
        }

        @Override
        public void setTrackSolo(int track, boolean solo) {
            //empty
        }

        @Override
        public boolean getTrackSolo(int track) {
            return false;
        }

        @Override
        public boolean addMetaEventListener(MetaEventListener listener) {
            return false;
        }

        @Override
        public void removeMetaEventListener(MetaEventListener listener) {
            //empty
        }

        @Override
        public int[] addControllerEventListener(ControllerEventListener listener, int[] controllers) {
            return new int[0];
        }

        @Override
        public int[] removeControllerEventListener(ControllerEventListener listener,
                                                   int[] controllers) {
            return new int[0];
        }

        @Override
        public void setLoopStartPoint(long tick) {
            //empty
        }

        @Override
        public long getLoopStartPoint() {
            return 0;
        }

        @Override
        public void setLoopEndPoint(long tick) {
            //empty
        }

        @Override
        public long getLoopEndPoint() {
            return 0;
        }

        @Override
        public void setLoopCount(int count) {
            //empty
        }

        @Override
        public int getLoopCount() {
            return 0;
        }
    }

    /*
     * to test the sequencer get right MIDI information
     */
    @Test
    public void testMidi1() throws MidiUnavailableException {
        StringBuffer out = new StringBuffer();
        Reader in = new StringReader("mary-little-lamb.txt\nconsole");
        Controller1 con1 = new Controller1(in, out);
        try {
            con1.buildView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }


        MusicModel now = con1.getMu();
        MockSequencer scer = new MockSequencer();

        MidiViewImpl midi = new MidiViewImpl(now.map(), now.getTempo(), now.number(), scer);
        try {
            midi.initialize();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        Sequence sec = scer.getSequence();

        long len = sec.getTickLength();
        assertEquals(now.getTempo(), (int) scer.tempo);
        assertEquals(sec.getTracks()[0].size(), 69);


    }

    /*
     * to test the sequencer get right MIDI information
     */
    @Test
    public void testMidi2() throws MidiUnavailableException {
        StringBuffer out = new StringBuffer();
        Reader in = new StringReader("mystery-2.txt\nconsole");
        Controller1 con1 = new Controller1(in, out);
        try {
            con1.buildView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }


        MusicModel now = con1.getMu();
        MockSequencer scer = new MockSequencer();

        MidiViewImpl midi = new MidiViewImpl(now.map(), now.getTempo(), now.number(), scer);
        try {
            midi.initialize();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        Sequence sec = scer.getSequence();

        long len = sec.getTickLength();
        assertEquals(111600, (int) scer.tempo);
        assertEquals(sec.getTracks()[0].size(), 1645);

    }

    /*
     * to test the sequencer get right MIDI information
     */
    @Test
    public void testMidi3() throws MidiUnavailableException {
        StringBuffer out = new StringBuffer();
        Reader in = new StringReader("mystery-1.txt\nconsole");
        Controller1 con1 = new Controller1(in, out);
        try {
            con1.buildView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

        MusicModel now = con1.getMu();
        MockSequencer scer = new MockSequencer();

        MidiViewImpl midi = new MidiViewImpl(now.map(), now.getTempo(), now.number(), scer);
        try {
            midi.initialize();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        Sequence sec = scer.getSequence();

        long len = sec.getTickLength();
        assertEquals(71428, (int) scer.tempo);
        assertEquals(sec.getTracks()[0].size(), 2559);

    }

    /*
     * to test the sequencer get right MIDI information
     */
    @Test
    public void testMidi4() throws MidiUnavailableException {
        StringBuffer out = new StringBuffer();
        Reader in = new StringReader("mystery-3.txt\nconsole");
        Controller1 con1 = new Controller1(in, out);
        try {
            con1.buildView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }


        MusicModel now = con1.getMu();
        MockSequencer scer = new MockSequencer();

        MidiViewImpl midi = new MidiViewImpl(now.map(), now.getTempo(), now.number(), scer);
        try {
            midi.initialize();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        Sequence sec = scer.getSequence();

        long len = sec.getTickLength();
        assertEquals(120000, (int) scer.tempo);
        assertEquals(sec.getTracks()[0].size(), 1421);
    }
}