package main;

import main.speechrecognition.notification.SpeechObserver;
import main.speechrecognition.recognizers.google.GoogleRecognition;

/**
 * Created by alvaro on 7/12/17.
 */
public class GoogleSampleRecognition {

    public static void main(String args[]){
        GoogleRecognition gs = new GoogleRecognition();
        gs.startListening();
        gs.register(new SpeechObserver() {
            @Override
            public void onSpeech(String spokenText) {
                System.out.println(spokenText);
            }
        });
        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
