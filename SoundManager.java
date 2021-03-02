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
        soundObjects.put("BGM", new SoundObject("BGM.wav"));
        soundObjects.put("Button", new SoundObject("Button.wav"));
        soundObjects.put("CarSelect", new SoundObject("CarSelect.wav"));
        soundObjects.put("CountDown", new SoundObject("CountDown.wav"));
        soundObjects.put("CrashProps", new SoundObject("CrashProps.wav"));
        soundObjects.put("Drift", new SoundObject("Drift.wav"));
        soundObjects.put("Engine", new SoundObject("Engine.wav"));
        soundObjects.put("GoverFail", new SoundObject("GoverFail.wav"));
        soundObjects.put("GoverWin", new SoundObject("GoverWin.wav"));
        soundObjects.put("Login", new SoundObject("Login.wav"));
        soundObjects.put("PlayPagesBGM", new SoundObject("PlayPagesBGM.wav"));
        soundObjects.put("SpeedUp", new SoundObject("SpeedUp.wav"));
        soundObjects.put("Starting", new SoundObject("Starting.wav"));
        soundObjects.put("ThrowProps", new SoundObject("ThrowProps.wav"));
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
