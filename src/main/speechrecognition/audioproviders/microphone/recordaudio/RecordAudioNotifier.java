package main.speechrecognition.audioproviders.microphone.recordaudio;

import main.speechrecognition.audioproviders.microphone.recordaudio.event.RecordAudioEvent;

import java.util.LinkedList;

/**
 * Created by alvaro on 7/12/17.
 */
public class RecordAudioNotifier implements RecordAudioObservable {
    LinkedList<RecordAudioObserver> observers = new LinkedList<>();
    @Override
    public void register(RecordAudioObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(RecordAudioObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(RecordAudioEvent event) {
        for (RecordAudioObserver observer: observers) {
            observer.update(event);
        }
    }
}
