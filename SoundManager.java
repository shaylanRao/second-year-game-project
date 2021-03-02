import java.util.HashMap;
import java.util.Map;

public class SoundManager 
{

    Map<String, SoundObject> soundObjects = new HashMap<>();

    public SoundManager()
    {
        
    }
    public void Init()
    {
        loadSounds();
    }
    private void loadSounds()
    {
        soundObjects.put("button", new SoundObject("sounds/button.wav"));
        soundObjects.put("engine", new SoundObject("sounds/engine.wav"));

        soundObjects.put("crash", new SoundObject("path..."));
        
        // ...
        
        soundObjects.put("bg_music", new SoundObject("sounds/bg_audio.wav"));
    }


    public void play(String soundId)
    {
        soundObjects.get(soundId).play();
    }
    public void loop(String soundId)
    {
        soundObjects.get(soundId).loop();
    }
    public void stop(String soundId)
    {
        soundObjects.get(soundId).stop();
    }


    
}
