package main.speechrecognition.notification;

/**
 * Created by alvaro on 7/6/17.
 */
public interface WatsonSpeechObservable {
    void register(WatsonSpeechObserver observer);

    void unregister(WatsonSpeechObserver observer);

    void notifySpeechObservers(String spokenText);
}
