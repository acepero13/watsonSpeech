package main;

import vad.moannar.VAD;
import vad.voiceactivitydetector.VoiceActivityDetector;
import vad.voiceactivitydetector.observer.VoiceNotifiable;

/**
 * Created by alvaro on 7/9/17.
 */
public class VADMoattarSample {

        public static void main(String[] args){

            Thread thread = new Thread(new Runnable() {
                public void run() {
                   VAD vad = new VAD();
                   vad.process();
                }
            });
            thread.start();

            while (true){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }



}
