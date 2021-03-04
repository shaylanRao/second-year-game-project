import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SoundObject
{

    private final File soundFile;
    private List<SoundPlayer> soundPlayers = new ArrayList<>();


    public SoundObject(String file_path)
    {
        soundFile = new File(file_path);
    }


    public void play()
    {
        SoundPlayer soundPlayer = new SoundPlayer(this);
        new Thread(() -> {
            soundPlayer.playSound();
        }).start();

        soundPlayers.add(soundPlayer);
    }
    public void loop()
    {
        SoundPlayer soundPlayer = new SoundPlayer(this);
        new Thread(() -> {
            soundPlayer.loopSound();
        }).start();

        soundPlayers.add(soundPlayer);
    }
    public void stop()
    {
        for(SoundPlayer player : soundPlayers)
            player.stop();
    }
    public void restart()
    {

    }


    public File getFile(){ return soundFile; }
    
    public String getFilePath(){ return soundFile.getAbsolutePath(); }

    public synchronized void removeSoundPlayer(SoundPlayer soundPlayerObj)
    { 
        soundPlayers.remove(soundPlayerObj);  
    }

    
}
