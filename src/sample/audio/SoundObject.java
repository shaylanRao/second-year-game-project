package sample.audio;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



/*
 * ____SoundObject class____

    > It's the class that represents and wraps the actual soundfile and its corresponding actions(play, loop, stop, etc...)

    **main role:
    {
        - play/loop/stop the corresponding soundfile
    }

    **main methods:
    {
        - play()
        - loop()
        - stop()
    }



     ___specifications___
    -----------------------------------------
        The SoundObject class stores the path of the corresponding soundfile (e.g: "c:/users/.../sounds/soundfile.wav")
    as a java.io.File object.
    The java.io.File representation is necessary for the play/loop/stop procedures of the actual sound file.

    In order to play/loop a SoundObject, we need to create a new SoundPlayer(see class: SoundPlayer.java)
    and pass the SoundObject to it, and then let the SoundPlayer play/loop it.
    Every SoundObject needs to get played in a seperate Thread to avoid blocking the main Thread until the
    sound processing has completed.
        Thus, a new Thread is created and attached to the SoundPlayer responsible for playing/looping the SoundObject.

        e.g(abstract):{

            SoundPlayer player = new SoundPlayer( SoundObject ); // create a new SoundPlayer with a SoundObject

            Thread handling_thread = new Thread( player ) ... // create a thread and 'attach' to it the SoundPlayer

            handling_thread.start() ... // start the thread and let the sound processing inside that thread.
        }

    In that way, a SoundObject can be played multiple times 'concurrently' without blocking any other process.
    In order to keep track of each seperate SoundPlayer responsible for playing the current SoundObject
    we store the created SoundPlayer in a list:soundPlayers, every time play/loop is being called.
 *
*/
public class SoundObject
{


    private final File soundFile;
    private List<SoundPlayer> soundPlayers = new ArrayList<>();


    // constructor
    public SoundObject(String file_path)
    {
        soundFile = new File(file_path);
    }


    // Method: play()
    // {1}: create a new SoundPlayer, pass the SoundObject to it.
    // {2}: create a new thread, 'attach' the SoundPlayer to it.
    // {3}: start the sound processing in the separate thread.
    // {4}: append the newly created SoundPlayer to the 'soundPlayers' list.
    public void play()
    {

        SoundPlayer soundPlayer = new SoundPlayer(this); // {1}

        Thread handling_thread = new Thread(){           // {2}
            public void run(){
                soundPlayer.playSound();
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
    public void loop()
    {
        SoundPlayer soundPlayer = new SoundPlayer(this); // {1}

        Thread handling_thread = new Thread(){           // {2}
            public void run(){
                soundPlayer.loopSound();
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

    public void restart()
    {

    }


    // - - - - - - - - - - - - - - - - - - - - - - - - -
    //
    //             Auxillary Functions
    //
    // - - - - - - - - - - - - - - - - - - - - - - - - -
    public File getFile(){ return soundFile; }

    public String getFilePath(){ return soundFile.getAbsolutePath(); }

    public synchronized void removeSoundPlayer(SoundPlayer soundPlayerObj)
    {
        soundPlayers.remove(soundPlayerObj);
    }


}
