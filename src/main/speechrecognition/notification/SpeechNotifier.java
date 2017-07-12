package main.speechrecognition.notification;

import java.util.LinkedList;

/**
 * Created by alvaro on 7/6/17.
 */
public class SpeechNotifier implements SpeechObservable {
    private LinkedList<SpeechObserver> observers = new LinkedList<>();

    @Override
    public void register(SpeechObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(SpeechObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifySpeechObservers(String spokenText) {
        for (SpeechObserver observer : observers) {
            observer.onSpeech(spokenText);
        }
    }
}
