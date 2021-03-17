package sample.controllers.audio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/*
 * ____SoundPlayer class____

    > It's the class that internally implements the processing of a SoundObject
      and actually playing/looping the corresponding soundfile.

    **main role:
    {
        - play/loop & produce the actual 'sound' of the corresponding soundfile
    }

    **main methods:
    {
        - playSound()
        - loopSound()
        - stop()
    }

     ___specifications___
    -----------------------------------------
    SoundPlayer process each soundfile as a : File >> AudioInputStream >> Clip
    It also attaches a 'LineListener' to the final Clip representation of the soundfile
    in order to keep trach of its processing stages(to know when the sound starts, ends, etc...)

*/
public class SoundPlayer implements LineListener {


    // a boolean flag keeping track of when the sound has done playing
    private boolean playingIsCompleted = false;

    // a reference to the SoundObject attached to the SoundPlayer ( which soundfile is being played )
    private final SoundObject soundObj;

    // constructor
    public SoundPlayer(SoundObject soundObj)
    {
        this.soundObj = soundObj;
    }

    // Method: playSound()
    // - responsible for transforming the initial SoundObject > AudioInputStream > Clip
    //   and playing it through the end, unless a stop call takes place from the corresponding SoundObject.
    public void playSound()
    {
        try
        {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundObj.getFile());
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip     = (Clip) AudioSystem.getLine(info);
            audioClip.addLineListener(this);
            audioClip.open(audioStream);
            audioClip.start();

            while (!playingIsCompleted)
            {
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            audioClip.close();
            soundObj.removeSoundPlayer(this);

        }
        catch(Exception e)
        {

        }
    }


    // Method: loopSound()
    // - responsible for transforming the initial SoundObject > AudioInputStream > Clip
    //   and looping it through, unless a stop call takes place from the corresponding SoundObject.
    public void loopSound()
    {
        try
        {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundObj.getFile());
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.addLineListener(this);
            audioClip.open(audioStream);
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);

            while (!playingIsCompleted)
            {
                try {
                    Thread.sleep(1000);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            audioClip.close();
            soundObj.removeSoundPlayer(this);
        }
        catch(Exception e)
        {

        }
    }


    // Method: stop()
    // - responsible for 'stopping' the ongoing process of the corresponding SoundObject
    //   by setting the 'playingIsCompleted' flag = true;
    public void stop()
    {
        playingIsCompleted = true;
    }

    // Auxillary Method
    public boolean isCompleted(){ return playingIsCompleted; }


    // LineListener:update(event)
    // This is getting called every frame and updates the state of the playing sound.
    // It's the reason we know when the processing of the sound has completed, and thus exit
    // the processing of the corresponding sound.
    @Override
    public void update(LineEvent event)
    {
        LineEvent.Type type = event.getType();
        if (type == LineEvent.Type.STOP)
            playingIsCompleted = true;

    }
}
