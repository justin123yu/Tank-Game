package TankGame.GameProject;

import javax.sound.sampled.Clip;

public class Sound implements Runnable{

    Clip c;

    public Sound(Clip c){
        this.c = c;
    }

    public void stopSound(){
        if(c.isRunning()){
            c.stop();
        }
    }

    public void playSound(){
        if(c.isRunning()){
            stopSound();
        }
        c.setFramePosition(0);
        c.start();
    }

    public void run() {
        c.loop(Clip.LOOP_CONTINUOUSLY);
        this.playSound();

    }
}
