package main;

import vad.moannar.VAD;
import vad.observer.VoiceNotifiable;

/**
 * Created by alvaro on 7/9/17.
 */
public class VADMoattarSample {

    public static boolean isSpeakingStatus = false;

    public static void main(String[] args) {

        Thread thread = new Thread(new Runnable() {
            public void run() {
                VAD vad = new VAD();
                vad.register(new VoiceNotifiable() {

                    @Override
                    public void handleSpeakingActivity(boolean speaking) {
                        if (speaking && !isSpeakingStatus) {
                            System.out.println("Speaking");
                            isSpeakingStatus = true;
                        } else if (!speaking && isSpeakingStatus) {
                            System.out.println("Silence");
                            isSpeakingStatus = false;
                        }
                        if (speaking) {
                            System.out.print(".");
                        }
                    }
                });
                vad.process();
            }
        });
        thread.start();

        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
