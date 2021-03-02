
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class SoundPlayer implements LineListener {

    private boolean playingIsCompleted = false;
    private final SoundObject soundObj;

    
    public SoundPlayer(SoundObject soundObj)
    {
        this.soundObj = soundObj;
    }

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
    


    public void stop() 
    {
        playingIsCompleted = true;
    }
    
    public boolean isCompleted(){ return playingIsCompleted; }

    @Override
    public void update(LineEvent event) 
    {
        LineEvent.Type type = event.getType();
        if (type == LineEvent.Type.STOP) 
            playingIsCompleted = true;
        
    }
}
