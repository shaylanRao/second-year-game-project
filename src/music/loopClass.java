package music;

import sun.audio.AudioPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class loopClass {
    public static void main(String[] args) throws FileNotFoundException {

        InputStream inputStream = new FileInputStream(new File("allMainPagesBgm.wav"));

        musicStuff musicObject = new musicStuff();

        AudioPlayer.player.start(inputStream);

    }
}
