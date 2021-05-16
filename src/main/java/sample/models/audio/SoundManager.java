package sample.models.audio;

import java.io.File;
import java.text.DecimalFormat;
import java.util.*;

/*
 * ____Sound Manager class____

	> It's the class that should be integrated/connected within the whole application
	  for sound management.

	> { How To Integrate }:
	 	1. create an instance of the class => SoundManager sound_mngr = new SoundManager();
	 	2. call (>) [method]: Init().             => sound_mngr.Init();

	> { How To Use }:
		1. Play a sound => sound_mngr.play("some_sound");
		2. Loop a sound => sound_mngr.loop("some_sound");
		3. Stop a sound => sound_mngr.stop("some_sound");


	**main role:
	{
		- store and process sounds.
		- play/loop/stop sounds
	}

 	**main (>) [method]:s:
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
    private static Map<String, SoundObject> soundObjects = new HashMap<>();
    private static boolean IS_INIT = false;

    private static final String AUDIO_FILES_PATH = "target/classes/audio/";

    // sound parameters
    private static float master_volume   = 1.0f;
    private static float GAME_SFX_VOLUME = 1.0f;
    private static float UI_SFX_VOLUME   = 1.0f;
    private static float MUSIC_VOLUME    = 1.0f;
    private static float BG_MUSIC_VOLUME = 1.0f;

    private static boolean is_muted          = false;
    private static boolean is_game_sfx_muted = false;
    private static boolean is_ui_sfx_muted   = false;
    private static boolean is_music_muted    = false;
    private static boolean is_bg_music_muted = false;

    // (>) [Method]:: Init
    //
    // - Initializes SoundManager
    public static boolean Init()
    {
        if(IS_INIT)
            return true;

        return (IS_INIT = loadSounds());
    }


    // (>) [Method]:: loadSounds
    //
    // - responsible for loading all the sounds
    private static boolean loadSounds()
    {
        try
        {
            //{       'soundId' ,      'soundFile' ,          'soundType'         }
            LoadSound("button", "Button.wav", SoundObject.UI_SFX);
            LoadSound("bgm", "Bgm.wav",       SoundObject.BG_MUSIC);
            LoadSound("playBgm", "playPagesBgm .wav", SoundObject.BG_MUSIC);
            LoadSound("engine", "Engine.wav", SoundObject.GAME_SFX);
            LoadSound("drift","Drift.wav", SoundObject.GAME_SFX);
            LoadSound("powerUp","SpeedUp.wav",SoundObject.GAME_SFX);
            LoadSound("prop","CrashProps.wav",SoundObject.GAME_SFX);
            LoadSound("bananaFall","bananaFall.wav",SoundObject.GAME_SFX);
            LoadSound("SpeedBoost","BoostSfx.wav",SoundObject.GAME_SFX);
            LoadSound("OilFall","OilFall.wav",SoundObject.GAME_SFX);


            // example:
            //
            //      ....
            //
            //      LoadSound("engine", "sounds/engine.wav", SoundObject.BG_MUSIC);
            //
            //      ....
            //

        }
        catch(Exception e)
        {
            System.out.print("\n ~ SoundManager::loadSounds() [exception] : ");
            e.printStackTrace();
            return false;
        }

        return true;
    }


    // (>) [Method]:: LoadSound(soundId, soundFile, SoundObject.Type)
    //
    // - checks that inputs are valid and throws specific exceptions if not.
    // - loads the given soundFile and matches it with the requested soundId
    private static void LoadSound(String soundId, String soundFile, int type) throws Exception
    {

        soundFile = AUDIO_FILES_PATH + soundFile;

        // check if given soundFile path is set to 'null'
        if(soundFile == null) throw new Exception(" -/- Path to File is 'null'");

        // check if file exists && is not a directory
        File file = new File(soundFile);
        if(file.exists() && !file.isDirectory())
        {
            // check if file is type of '.wav'
            if(soundFile.contains("."))
            {
                String ext = soundFile.substring(soundFile.lastIndexOf(".") + 1, soundFile.length()).toLowerCase();
                if(!ext.equals("wav")) throw new Exception(" -/- Not a '.wav' File!");
            }
            else throw new Exception(" -/- Not a valid File!");
        }
        else
        {
            throw new Exception(" -/- Not a valid File!");
        }

        // Load Sound if all good
        soundObjects.put(soundId, new SoundObject(soundFile, type));
    }


    // (>) [Method]:: ConfigureSounds( .. )
    //
    // - configure sounds
    public static void configureSounds()
    {
        if(!IS_INIT)
            return;

        setVolume(0.15f, SoundObject.BG_MUSIC);
        setVolume("button", 0.1f);
        setVolume("engine" , 0.1f);

    }


    // - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //
    //               Process per SoundId
    //
    // - - - - - - - - - - - - - - - - - - - - - - - - - - -



    // (>) [Method]:: play( soundId )
    //
    // - plays the SoundObject matching the corresponding soundId
    // e.g:{
    //         SoundManager.play("button");
    //	   }
    public static void play( String soundId )
    {
        soundObjects.get(soundId).play(0);
    }




    // (>) [Method]:: loop( soundId )
    //
    // - loops the SoundObject matching the corresponding soundId
    // e.g:{
    //         SoundManager.loop("background_music");
    //	   }
    public static void loop( String soundId )
    {
        soundObjects.get(soundId).loop(0);
    }




    // (>) [Method]:: stop( soundId )
    //
    // - stop the SoundObject matching the corresponding soundId
    // e.g:{
    //         SoundManager.stop("background_music");
    //	   }
    public static void stop( String soundId )
    {
        soundObjects.get(soundId).stop();
    }




    // (>) [Method]:: pause( soundId )
    //
    // - pauses the SoundObject matching the corresponding soundId
    // e.g:{
    //         SoundManager.pause("background_music");
    //	   }
    public static void pause( String soundId )
    {
        soundObjects.get(soundId).pause(1);
    }




    // (>) [Method]:: resume( soundId )
    //
    // - resumes the SoundObject matching the corresponding soundId
    // e.g:{
    //         SoundManager.resume("background_music");
    //	   }
    public static void resume( String soundId )
    {
        soundObjects.get(soundId).resume(1);
    }




    // (>) [Method]:: restart( soundId )
    //
    // - restart the SoundObject matching the corresponding soundId
    // e.g:{
    //         SoundManager.restart("background_music");
    //	   }
    public static void restart( String soundId )
    {
        soundObjects.get(soundId).restart();
    }




    // - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //
    //                Process All Sounds
    //
    // - - - - - - - - - - - - - - - - - - - - - - - - - - -


    // (>) [Method]:: pauseAll()
    //
    // - pauses all sounds
    public static void pauseAll()
    {
        soundObjects.forEach((k,v) -> v.pause(1));
    }




    // (>) [Method]:: resumeAll()
    //
    // - resumes all paused sounds
    public static void resumeAll()
    {
        soundObjects.forEach((k,v) -> v.resume(1));
    }




    // (>) [Method]:: restartAll()
    //
    // - restarts all sounds
    public static void restartAll()
    {
        soundObjects.forEach((k,v) -> v.restart());
    }



    // (>) [Method]:: stopAll()
    //
    // - stops all sounds
    public static void stopAll()
    {
        soundObjects.forEach((k,v) -> v.stop());
    }




    // - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //
    //            Process per SoundType
    //
    // - - - - - - - - - - - - - - - - - - - - - - - - - - -


    // (>) [Method]:: pauseAll(sound_type)
    //
    // - pauses all sounds of given sound_type
    public static void pauseAll(int sound_type)
    {
        soundObjects.entrySet().stream()
                .filter((e) -> (e.getValue().getType() == sound_type))
                .forEach((e) -> e.getValue().pause(1));
    }




    // (>) [Method]:: resumeAll(sound_type)
    //
    // - resumesAll all sounds of given sound_type
    public static void resumeAll(int sound_type)
    {
        soundObjects.entrySet().stream()
                .filter((e) -> (e.getValue().getType() == sound_type))
                .forEach((e) -> e.getValue().resume(1));
    }




    // (>) [Method]:: restartsAll(sound_type)
    //
    // - restarts all sounds of given sound_type
    public static void restartAll(int sound_type)
    {
        soundObjects.entrySet().stream()
                .filter((e) -> (e.getValue().getType() == sound_type))
                .forEach((e) -> e.getValue().restart());
    }




    // (>) [Method]:: stopAll()
    //
    // - stops all sounds
    public static void stopAll(int sound_type)
    {
        soundObjects.entrySet().stream()
                .filter((e) -> (e.getValue().getType() == sound_type))
                .forEach((e) -> e.getValue().stop());
    }




    // - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //
    //             Process volume per SoundId
    //
    // - - - - - - - - - - - - - - - - - - - - - - - - - - -


    // (>) [Method]:: setSoundVolume(SoundId, value)
    //
    // - sets the volume for given sound
    public static void setVolume(String soundId, float value)
    {
        soundObjects.get(soundId).setVolume(value);
    }




    // (>) [Method]:: mute(SoundId, state)
    //
    // - mutes / un-mutes sound
    public static void mute(boolean state, String soundId){ soundObjects.get(soundId).mute(state);}




    // (>) [Method]:: adjustVolume(SoundId, dv)
    //
    // - adjusts the volume for given sound such that : volume = volume + dv
    public static void adjustVolume(String soundId, float dv)
    {
        soundObjects.get(soundId).adjustVolume(dv);
    }




    // (>) [Method]:: getSoundVolume(soundId)
    //
    // - returns the volume of given sound
    public static float getVolume(String soundId)
    {
        return soundObjects.get(soundId).getVolume();
    }




    // (>) [Method]:: isMuted(soundId)
    //
    // - returns true/false if sound if muted
    public static boolean isMuted(String soundId){ return soundObjects.get(soundId).isMuted(); }




    // - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //
    //             Process master volume
    //
    // - - - - - - - - - - - - - - - - - - - - - - - - - - -


    // (>) [Method]:: setMasterVolume(value)
    //
    // - sets the master volume
    public static void setMasterVolume(float value)
    {
        value = value > 1? 1 : value;
        value = value < 0? 0 : value;
        master_volume = value;
    }




    // (>) [Method]:: muteMater(boolean state)
    //
    // - mutes / un-mutes master volume
    public static void muteMaster(boolean state){ is_muted = state; System.out.println(" - muting master: "+state); }




    // (>) [Method]:: deltaMasterVolume(dv)
    //
    // - adjusts the master volume such that : master_volume = master_volume + dv
    public static void adjustMasterVolume(float dv)
    {
        setMasterVolume(master_volume + dv);
    }




    // (>) [Method]:: getMasterVolume()
    //
    // - returns the master volume
    public static float getMasterVolume()
    {
        DecimalFormat df = new DecimalFormat("###.###");
        return Float.parseFloat(df.format(master_volume));
    }




    // (>) [Method]:: isMasterMuted()
    //
    // - returns true/false if master volume is muted
    public static boolean isMasterMuted()
    {
        return is_muted;
    }




    // - - - - - - - - - - - - - - - - - - - - - - - - - - -
    //
    //            Process volume per Sound Type
    //
    // - - - - - - - - - - - - - - - - - - - - - - - - - - -


    // (>) [Method]:: setVolume(value, sound_type)
    //
    // - sets the volume for given type { sfx, music, etc.. }
    public static void setVolume(float value, int sound_type)
    {
        value = value > 1? 1 : value;
        value = value < 0? 0 : value;
        switch (sound_type)
        {
            case SoundObject.GAME_SFX: GAME_SFX_VOLUME = value; break;
            case SoundObject.MUSIC:    MUSIC_VOLUME    = value; break;
            case SoundObject.BG_MUSIC: BG_MUSIC_VOLUME = value; break;
            case SoundObject.UI_SFX:   UI_SFX_VOLUME   = value; break;
        }
    }




    // (>) [Method]:: adjustVolume(value, sound_type)
    //
    // - adjusts the volume for given type { sfx, music, etc.. }
    public static void adjustVolume(float value, int sound_type)
    {
        setVolume(getVolume(sound_type)+value, sound_type);
    }




    // (>) [Method]:: mute(sound_type, state)
    //
    // - mutes/un-mutes given sound type
    public static void mute(boolean state, int sound_type)
    {
        switch (sound_type)
        {
            case SoundObject.GAME_SFX: is_game_sfx_muted = state; break;
            case SoundObject.MUSIC:    is_music_muted    = state; break;
            case SoundObject.BG_MUSIC: is_bg_music_muted = state; break;
            case SoundObject.UI_SFX:   is_ui_sfx_muted   = state; break;
        }
    }




    // (>) [Method]:: getVolume(sound_type)
    //
    // - returns the volume of given type { sfx, music, etc.. }
    public static float getVolume(int sound_type)
    {
        switch (sound_type)
        {
            case SoundObject.GAME_SFX: return GAME_SFX_VOLUME;
            case SoundObject.MUSIC:    return MUSIC_VOLUME;
            case SoundObject.BG_MUSIC: return BG_MUSIC_VOLUME;
            case SoundObject.UI_SFX:   return UI_SFX_VOLUME;
        }
        return 0f;
    }




    // (>) [Method]:: isMuted(sound_type)
    //
    // - returns true/false if sound type is muted
    public static boolean isMuted(int sound_type)
    {
        switch (sound_type)
        {
            case SoundObject.GAME_SFX: return is_game_sfx_muted;
            case SoundObject.MUSIC:    return is_music_muted;
            case SoundObject.BG_MUSIC: return is_bg_music_muted;
            case SoundObject.UI_SFX:   return is_ui_sfx_muted;
        }

        return false;
    }



}
