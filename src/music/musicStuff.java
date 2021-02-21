package music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;

public class musicStuff {

    void playMusic(String musicLocation)
    {
        try
        {
            File musicPath = new File(musicLocation);

            if(musicPath.exists())
            {
                AudioInputStream audioInput = getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();

                clip.loop(Clip.LOOP_CONTINUOUSLY);

            }
            else
            {
                System.out.println("Can't find file");

            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
