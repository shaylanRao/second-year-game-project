package sample.controllers.audio;

import javax.sound.sampled.*;

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


    public int type;
    public long clipTime;

    // a boolean flag keeping track of when the sound has done playing
    private boolean playingIsCompleted = false;

    // a reference to the SoundObject attached to the SoundPlayer ( which soundfile is being played )
    private final SoundObject soundObj;
    private Clip audioClip;



    // constructor
    public SoundPlayer(SoundObject soundObj)
    {
        this.soundObj = soundObj;
    }


    // Method: playSound()
    // - responsible for transforming the initial SoundObject > AudioInputStream > Clip
    //   and playing it through the end, unless a stop call takes place from the corresponding SoundObject.
    public void playSound(int type, long _clipTime)
    {
        this.type = type;
        int loop_count = type == 0? 1 : -1;

        try
        {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundObj.getFile());
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            audioClip          = (Clip) AudioSystem.getLine(info);
            audioClip.addLineListener(this);
            audioClip.open(audioStream);
            audioClip.setMicrosecondPosition(_clipTime % audioClip.getMicrosecondLength());
            if(type == 0)
                audioClip.start();
            else
                audioClip.loop(loop_count);

            while (!playingIsCompleted)
            {

                // get audio clip's position
                clipTime = audioClip.getMicrosecondPosition();


                // adjust clip's volume
                boolean is_muted = soundObj.isMuted_scaled();
                FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(20f * (float) Math.log10(is_muted? 0 : soundObj.getVolume_scaled()));


                try { Thread.sleep(0); }
                catch (InterruptedException ex) { ex.printStackTrace(); }
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