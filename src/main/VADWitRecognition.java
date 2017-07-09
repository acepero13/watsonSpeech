package main;

import main.speechrecognition.recognizers.watson.voiceactivated.WatsonVoiceActivated;

import javax.sound.sampled.LineUnavailableException;

/**
 * Created by alvaro on 6/23/17.
 */
public class VADWitRecognition {
    public static void main(String[] args) throws LineUnavailableException {
        WatsonVoiceActivated activated = new WatsonVoiceActivated();
        activated.startListening();
        try {
            Thread.sleep(17000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Fin.");
    }
}
