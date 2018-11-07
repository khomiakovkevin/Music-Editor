package cs3500.music.controller;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

import cs3500.music.model.Beat;
import cs3500.music.model.MusicModel;
import cs3500.music.model.MusicModel1;
import cs3500.music.provider.model.Adaptee;
import cs3500.music.provider.model.MusicOperations;
import cs3500.music.provider.model.Note;
import cs3500.music.provider.view.CompositeMusicView;
import cs3500.music.provider.view.MidiView;
import cs3500.music.provider.view.MusicConsoleView;
import cs3500.music.provider.view.MusicView;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;
import cs3500.music.view.CompositeView;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IView;
import cs3500.music.view.InteractiveView;
import cs3500.music.view.MidiViewImpl;
import cs3500.music.view.TextualView;

/**
 * Created by 28609 on 3/17/2017.
 */
public class Controller1 implements MusicController {
  private Readable rd;
  private Appendable ap;
  private MusicModel mu;
  private MusicOperations<Note> mo;

  /**
   * Constructor for our Controller1.
   *
   * @param rd represents the readable
   * @param ap represents the appendable
   */
  public Controller1(Readable rd, Appendable ap) {

    this.rd = rd;
    this.ap = ap;
    this.mu = null;
    this.mo = null;

  }

  /**
   * to get the Music Model.
   *
   * @return Music Mode
   */
  public MusicModel getMu() {
    return this.mu;
  }


  @Override
  public IView buildView() throws IOException, InvalidMidiDataException {

    Scanner scan = new Scanner(this.rd);
    scan.useDelimiter("\\s");
    String file = scan.next();
    String type = scan.next();
    scan.close();
    MusicReader mr = new MusicReader();
    HashMap<Integer, HashMap<Integer, Beat>> hash = new HashMap<>();

    for (int i = 0; i < 128; i++) {
      HashMap<Integer, Beat> tem = new HashMap<>();
      hash.put(i, tem);
    }
    CompositionBuilder<MusicModel> md = new MusicModel1.Builder().hash(hash).volume(30).tempo(300);
    try {
      mu = MusicReader.parseFile(new FileReader(file), md);
      mo = new Adaptee(mu);

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }


    if (type.equals("visual")) {
      return new GuiViewFrame(mu.map(), mu.number());

    } else if (type.equals("midi")) {
      IView midiView = null;
      try {
        midiView = new MidiViewImpl(mu.map(), mu.getTempo(), mu.number(),
                MidiSystem.getSequencer());
        return midiView;
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
      }

    } else if (type.equals("composite")) {
      CompositeView com = null;
      try {
        com = new CompositeView(mu, MidiSystem.getSequencer());
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
      }
      return com;
    } else if (type.equals("console")) {
      return new TextualView(mu);
    } else if (type.equals("interactive")) {
      try {
        return new InteractiveView(mu, MidiSystem.getSequencer());
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
      }
    } else if (type.equals("provider-console")) {
      MusicView<Note> procon = new MusicConsoleView(ap);
      procon.setModel(mo);
      procon.drawMusic();
      return null;
    } else if (type.equals("provider-visual")) {
      MusicView<Note> provisual = new cs3500.music.provider.view.GuiViewFrame();
      provisual.setModel(mo);
      provisual.drawMusic();
      return null;
    } else if (type.equals("provider-midi")) {
      MusicView<Note> promidi = null;
      try {
        promidi = new MidiView();
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
      }
      promidi.setModel(mo);
      promidi.drawMusic();
      return null;
    } else if (type.equals("provider-composite")) {
      MusicView<Note> provisual = new cs3500.music.provider.view.GuiViewFrame();
      MusicView<Note> promidi = null;
      try {
        promidi = new MidiView();
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
      }
      MusicView<Note> procom = new CompositeMusicView(provisual, promidi);
      procom.setModel(mo);
      procom.drawMusic();
      return null;

    }
    throw new IllegalArgumentException("no such type view");

  }

}
