package main;

import main.speechrecognition.notification.SpeechObserver;
import main.speechrecognition.recognizers.watson.voiceactivated.SpeechRecognitionVoiceActivated;

import javax.sound.sampled.LineUnavailableException;

/**
 * Created by alvaro on 6/23/17.
 */
public class VADWitRecognition {
    public static void main(String[] args) throws LineUnavailableException {
        SpeechRecognitionVoiceActivated activated = new SpeechRecognitionVoiceActivated();

        activated.startListening();
        activated.register(new SpeechObserver() {
            @Override
            public void onSpeech(String spokenText) {
                System.out.println(spokenText);
            }
        });
        try {
            Thread.sleep(17000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Fin.");
    }
}
