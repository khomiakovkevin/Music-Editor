package cs3500.music.controller;

import cs3500.music.view.*;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by 28609 on 4/5/2017.
 */
public class Controller1Test {

    /*
     * to test getMu method
     */
    @Test
    public void getMu() throws Exception {

        StringBuffer out = new StringBuffer();
        Reader in = new StringReader("mystery-3.txt\nconsole");
        Controller1 con1 = new Controller1(in, out);
        IView view = con1.buildView();
        assertEquals(1400, con1.getMu().number());


    }

    /*
     * to test if we input a certain type, get the right type view.
     */
    @Test
    public void buildView() throws Exception {
        StringBuffer out = new StringBuffer();
        Reader in = new StringReader("mystery-3.txt\nconsole");
        Controller1 con1 = new Controller1(in, out);
        IView view = con1.buildView();
        assertEquals(true, view instanceof TextualView);
    }

    /*
     * to test if we input a certain type, get the right type view.
     */
    @Test
    public void buildView1() throws Exception {
        StringBuffer out = new StringBuffer();
        Reader in = new StringReader("mystery-3.txt\nvisual");
        Controller1 con1 = new Controller1(in, out);
        IView view = con1.buildView();
        assertEquals(true, view instanceof GuiViewFrame);
    }

    /*
     * to test if we input a certain type, get the right type view.
     */
    @Test
    public void buildView2() throws Exception {
        StringBuffer out = new StringBuffer();
        Reader in = new StringReader("mystery-3.txt\nmidi");
        Controller1 con1 = new Controller1(in, out);
        IView view = con1.buildView();
        assertEquals(true, view instanceof MidiViewImpl);
    }

    /*
     * to test if we input a certain type, get the right type view.
     */
    @Test
    public void buildView4() throws Exception {
        StringBuffer out = new StringBuffer();
        Reader in = new StringReader("mystery-3.txt\ncomposite");
        Controller1 con1 = new Controller1(in, out);
        IView view = con1.buildView();
        assertEquals(true, view instanceof CompositeView);
    }

}