package main.speechrecognition.notification;

/**
 * Created by alvaro on 7/6/17.
 */
public interface SpeechObservable {
    void register(SpeechObserver observer);

    void unregister(SpeechObserver observer);

    void notifySpeechObservers(String spokenText);
}
