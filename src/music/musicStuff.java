package music;


import java.io.FileInputStream;

public class musicStuff extends Thread{
    FileInputStream music;

    sun.audio.AudioStream play_music;

    musicStuff(){
        this.start();
    }


    public void run(){

        //循环播放(loop)
        while(true){
            try{
                music=new FileInputStream("loginPagesBgm.wav");
                play_music=new sun.audio.AudioStream(music);
            }catch(Exception e){System.out.println(e);}

            sun.audio.AudioPlayer.player.start(play_music);//start playing

            try{
                Thread.sleep(50000);//audio playing time
            }catch(Exception e){System.out.println(e);}
        }
    }


    public static void main(String[] args){
        new musicStuff();
    }
}
