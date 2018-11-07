package cs3500.music.model;

import java.util.HashMap;

/**
 * Created by 28609 on 3/2/2017.
 * this class is mainly used to create a MusicModel1. with a static method create.
 */
public class MusicModel1Creator {


    /**
     * this static method is design to create a MusicModel1 with input volume to determine its volume.
     *
     * @param volume given the size of this music.
     * @return a musicModel1.
     */
    public static MusicModel1 create(int volume) {
        HashMap<Integer, HashMap<Integer, Beat>> wanted = new HashMap<>();
        for (int i = 0; i < 128; i++) {
            HashMap<Integer, Beat> tem = new HashMap<>();
            wanted.put(i, tem);
        }

        return new MusicModel1(wanted, volume, 300);


    }
}
