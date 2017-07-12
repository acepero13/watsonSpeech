package main;

import main.speechrecognition.notification.WatsonSpeechObserver;
import main.speechrecognition.recognizers.watson.WatsonRecognition;

import javax.sound.sampled.LineUnavailableException;

/**
 * Created by alvaro on 6/22/17.
 */
public class RecognitionSample {
    public static void main(String[] args) throws LineUnavailableException {

        WatsonRecognition watsonRecognition = new WatsonRecognition();
        watsonRecognition.startListening();
        watsonRecognition.register(new WatsonSpeechObserver() {
            @Override
            public void onSpeech(String spokenText) {
                System.out.println(spokenText);
            }
        });
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Fin.");
        watsonRecognition.stopRecognition();
    }
}
