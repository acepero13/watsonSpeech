package main.speechrecognition.notification;

/**
 * Created by alvaro on 7/6/17.
 */
public interface SpeechObserver {
    void onSpeech(String spokenText);
}
