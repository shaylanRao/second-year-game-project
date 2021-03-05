package sample.audio;

import java.util.HashMap;
import java.util.Map;

/*
 * ____Sound Manager class____

	> It's the class that should be integrated/connected within the whole application
	  for sound management.

	> { How To Integrate }:
	 	1. create an instance of the class => SoundManager sound_mngr = new SoundManager();
	 	2. call method Init().             => sound_mngr.Init();

	> { How To Use }:
		1. Play a sound => sound_mngr.play("some_sound");
		2. Loop a sound => sound_mngr.loop("some_sound");
		3. Stop a sound => sound_mngr.stop("some_sound");


	**main role:
	{
		- store and process sounds.
		- play/loop/stop sounds
	}

 	**main methods:
 	{
 		- Init()

 		- play( sound )
 		- loop( sound )
 		- stop( sound )
 	}

 *
*/
public class SoundManager
{

    /*

    __Map<String, SoundObject> soundObjects__ :

        SoundManager stores sounds as SoundObjects ( see class : SoundObject.java ) inside a Map.
        Each Map Entry is <key,value> pair, where key=String, value=SoundObject.
        Thus, each Map Entry stores a SoundObject(value) to a specific and unique soundId(key).

        General access procedures on a Map consist of 2 main actions :
            1. store value 'v', on the key 'k'            => map.put(k, v);
            2. get the value corresponding to the key 'k' => map.get(k);

        In that sense, we store and get values from the soundObjects map by:
            1. store => soundObjects.put("soundId", SoundObject);
            2. get   => soundObjects.get("soundId");

            e.g:{

                . . .

                SoundObject engineSound = new SoundObject("../sounds/engine.wav"); // create a new SoundObject given a file path
                soundObjects.put("engine", engineSound); // put the SoundObject in the map, with the key="engine"

                . . .

                SoundObject engineSound = soundObjects.get("engine"); // retrieve the SoundObject corresponding to the key="engine"

                . . .

            }
    */
    Map<String, SoundObject> soundObjects = new HashMap<>();


    // default Constructor
    public SoundManager()
    {

    }

    // Method: Init
    // - Initializes SoundManager
    // - Loads Sounds
    public void Init()
    {
        loadSounds();
    }


    // Method: loadSounds
    // - responsible for loading all the sounds
    //
    // * We load a soundfile into a SoundObject by passing as an argument its filepath (e.g: .../sounds/somesound.wav)
    // * We use the .put(key, value) map function to store each sound in the soundObjects map.
    // * e.g, if you want to load a soundfile with path="c:/user/documents/.../sounds/sound1.wav"
    //        - soundObjects.put("someLabel", new SoundObject("c:/user/documents/.../sounds/sound1.wav"));
    //
    //           . . .
    //
    //        - SoundManager.play("someLabel"); // in order to play that sound
    //
    private void loadSounds()
    {
        soundObjects.put("button", new SoundObject("src\\sample\\resources\\audio\\button.wav")); // load button.wav soundfile into "button" label
        soundObjects.put("engine", new SoundObject("src\\sample\\resources\\audio\\engine.wav"));
        soundObjects.put("mainBg", new SoundObject("src\\sample\\resources\\audio\\Bgm.wav"));
        soundObjects.put("playBg", new SoundObject("src\\sample\\resources\\audio\\playPagesBgm.wav"));

        // ...

        // soundObjects.put("bg_music", new SoundObject("sounds/bg_audio.wav")); // load some background music...
    }


    // Method: play(soundId)
    // - plays the SoundObject matching the corresponding soundId
    // e.g:{
    //         SoundManager.play("button");
    //	   }
    public void play(String soundId)
    {
        soundObjects.get(soundId).play();
    }

    // Method: loop(soundId)
    // - loops the SoundObject matching the corresponding soundId
    // e.g:{
    //         SoundManager.loop("background_music");
    //	   }
    public void loop(String soundId)
    {
        soundObjects.get(soundId).loop();
    }

    // Method: stp(soundId)
    // - stop the SoundObject matching the corresponding soundId
    // e.g:{
    //         SoundManager.stop("background_music");
    //	   }
    public void stop(String soundId)
    {
        soundObjects.get(soundId).stop();
    }



}
