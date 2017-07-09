package main.speechrecognition.notification;

import java.util.LinkedList;

/**
 * Created by alvaro on 7/6/17.
 */
public class WatsonSpeechNotifier implements WatsonSpeechObservable {
    private LinkedList<WatsonSpeechObserver> observers = new LinkedList<>();

    @Override
    public void register(WatsonSpeechObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(WatsonSpeechObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifySpeechObservers(String spokenText) {
        for (WatsonSpeechObserver observer: observers ) {
            observer.onSpeech(spokenText);
        }
    }
}
