package sample.models.audio;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
 * ____SoundObject class____

    > It's the class that represents and wraps the actual soundFile and its corresponding actions(play, loop, stop, etc...)

    **main role:
    {
        - play/loop/stop the corresponding soundFile
    }

    **main methods:
    {
        - play()
        - loop()
        - stop()
    }



     ___specifications___
    -----------------------------------------
        The SoundObject class stores the path of the corresponding soundFile (e.g: "c:/users/.../sounds/soundFile.wav")
    as a java.io.File object.
    The java.io.File representation is necessary for the play/loop/stop procedures of the actual sound file.

    In order to play/loop a SoundObject, we need to create a new SoundPlayer(see class: SoundPlayer.java)
    and pass the SoundObject to it, and then let the SoundPlayer play/loop it.
    Every SoundObject needs to get played in a separate Thread to avoid blocking the main Thread until the
    sound processing has completed.
        Thus, a new Thread is created and attached to the SoundPlayer responsible for playing/looping the SoundObject.

        e.g(abstract):{

            SoundPlayer player = new SoundPlayer( SoundObject ); // create a new SoundPlayer with a SoundObject

            Thread handling_thread = new Thread( player ) ... // create a thread and 'attach' to it the SoundPlayer

            handling_thread.start() ... // start the thread and let the sound processing inside that thread.
        }

    In that way, a SoundObject can be played multiple times 'concurrently' without blocking any other process.
    In order to keep track of each separate SoundPlayer responsible for playing the current SoundObject
    we store the created SoundPlayer in a list:soundPlayers, every time play/loop is being called.
 *
*/
public class SoundObject
{


    private final File soundFile;
    private final List<SoundPlayer> soundPlayers        = new ArrayList<>();
    private final Map<Integer, Long>  suspended_players = new HashMap<>();
    private float sound_volume = 1.0f;
    private boolean is_muted = false;

    private final int type;

    // Sound Types
    public static final int GAME_SFX = 0;
    public static final int UI_SFX   = 1;
    public static final int MUSIC    = 2;
    public static final int BG_MUSIC = 3;



    // constructor
    public SoundObject(String file_path, int type)
    {
        soundFile = new File(file_path);
        this.type = type;
    }


    // Method: play()
    // {1}: create a new SoundPlayer, pass the SoundObject to it.
    // {2}: create a new thread, 'attach' the SoundPlayer to it.
    // {3}: start the sound processing in the separate thread.
    // {4}: append the newly created SoundPlayer to the 'soundPlayers' list.
    public void play(long _clipTime)
    {

        SoundPlayer soundPlayer = new SoundPlayer(this); // {1}

        Thread handling_thread = new Thread(){           // {2}
            public void run(){
                soundPlayer.playSound(0, _clipTime);
            }
        };

        handling_thread.start();                         // {3}

        soundPlayers.add(soundPlayer);                   // {4}

    }


    // Method: loop()
    // {1}: create a new SoundPlayer, pass the SoundObject to it.
    // {2}: create a new thread, 'attach' the SoundPlayer to it.
    // {3}: start the sound processing in the separate thread.
    // {4}: append the newly created SoundPlayer to the 'soundPlayers' list.
    public void loop(long _clipTime)
    {
        SoundPlayer soundPlayer = new SoundPlayer(this); // {1}

        Thread handling_thread = new Thread(){           // {2}
            public void run(){
                soundPlayer.playSound(1, _clipTime);
            }
        };

        handling_thread.start();                         // {3}

        soundPlayers.add(soundPlayer);                   // {4}

    }


    // Method: stop()
    // - stop each soundPlayer created for playing this SoundObject
    //
    // * we iterate through each SoundPlayer created and assigned for playing the current SoundObject
    // * and we call the method: SoundPlayer.stop() which in turn stops the playing process of the sound.
    public void stop()
    {
        for(SoundPlayer player : soundPlayers)
            player.stop();
    }


    // Method: pause()
    // - pause each soundPlayer created for playing this SoundObject
    //
    // * we iterate through each SoundPlayer created and assigned for playing the current SoundObject
    // * and we call the method: SoundPlayer.pause() which in turn pauses the process of the sound.
    public void pause(int _setClipTime)
    {
        suspended_players.clear();
        for(SoundPlayer player : soundPlayers)
        {
            suspended_players.put(player.type, _setClipTime == 1? player.clipTime : 0);
            player.stop();
        }
    }


    // Method: resume()
    // - resume each soundPlayer created for playing this SoundObject
    //
    // * we iterate through each SoundPlayer created and assigned for playing the current SoundObject
    // * and we call the method: SoundPlayer.resume() which in turn resumes the process of the sound.
    public void resume(int _setClipTime)
    {
        for(long key : suspended_players.keySet())
        {
            if(key == 0)
                play(_setClipTime == 1? suspended_players.get(key) : 0);
            else
                loop(_setClipTime == 1? suspended_players.get(key) : 0);
        }

        suspended_players.clear();
    }


    // Method: restart()
    // - restarts sound
    public void restart()
    {
        if(suspended_players.size() == 0)
            pause(0);

        resume(0);
    }


    // Method: setVolume(value)
    // - sets the volume of the sound
    public void setVolume(float value)
    {
        value = value > 1? 1 : value;
        value = value < 0? 0 : value;
        sound_volume = value;
    }


    // Method: mute(boolean state)
    // - mutes / un-mutes sound
    public void mute(boolean state){ is_muted = state; }


    // Method: deltaVolume(value)
    // - adjusts the volume of the sound
    public void adjustVolume( float dv )
    {
        setVolume(sound_volume + dv);
    }


    // Method: getVolume()
    // - gets the volume of current SoundObject
    public float getVolume()
    {
        DecimalFormat df = new DecimalFormat("###.###");
        return Float.parseFloat(df.format(sound_volume));
    }


    // Method: getVolume_scaled()
    // - gets the scaled volume of current SoundObject
    public float getVolume_scaled()
    {
    	float master_volume = SoundManager.getMasterVolume();
        float type_volume   = SoundManager.getVolume(type);
        float scaled_volume = sound_volume * type_volume * master_volume;
        DecimalFormat df = new DecimalFormat("###.###");
        return Float.parseFloat(df.format(scaled_volume));
    }


    // Method: isMuted()
    // - returns true/false if muted
    public boolean isMuted(){ return is_muted; }


    // Method: isMuted_scaled()
    // - returns true/false if muted {or} type muted {or} master muted
    public boolean isMuted_scaled(){
        return isMuted()
        		|| SoundManager.isMasterMuted()
                || SoundManager.isMuted(type);
    }


    // - - - - - - - - - - - - - - - - - - - - - - - - -
    //
    //             Auxiliary Functions
    //
    // - - - - - - - - - - - - - - - - - - - - - - - - -

    public int getType(){ return type; }

    public File getFile(){ return soundFile; }

    public synchronized void removeSoundPlayer(SoundPlayer soundPlayerObj)
    {
        soundPlayers.remove(soundPlayerObj);
    }


}
