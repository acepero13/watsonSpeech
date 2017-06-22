package main;

import main.voiceactivitydetector.VoiceActivityDetector;
import main.voiceactivitydetector.VoiceNotifiable;

/**
 * Created by alvaro on 6/22/17.
 */
public class Test {

    public static void main(String[] args){

        Thread thread = new Thread(new Runnable() {
            public void run() {
                final VoiceActivityDetector voiceActivityDetector = new VoiceActivityDetector(new VoiceNotifiable() {
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

