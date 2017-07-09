package main;

import vad.voiceactivitydetector.VoiceActivityDetector;
import vad.observer.VoiceNotifiable;

/**
 * Created by alvaro on 6/22/17.
 */
public class VADSample {

    public static void main(String[] args){

        Thread thread = new Thread(new Runnable() {
            public void run() {
                final VoiceActivityDetector voiceActivityDetector = new VoiceActivityDetector();
                voiceActivityDetector.register(new VoiceNotifiable() {
                    public void handleSpeakingActivity(boolean speaking) {
                        if(speaking)
                            System.out.println("Speaking....");
                    }
                });
                voiceActivityDetector.startListening();
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

