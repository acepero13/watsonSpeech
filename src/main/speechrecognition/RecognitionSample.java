package main.speechrecognition;

import main.speechrecognition.recognizers.watson.WatsonRecognition;

import javax.sound.sampled.LineUnavailableException;

/**
 * Created by alvaro on 6/22/17.
 */
public class RecognitionSample {
    public static void main(String [] args) throws LineUnavailableException {

        WatsonRecognition.startWatsonRecognitionThread();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Fin.");
    }
}
