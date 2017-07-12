package main.speechrecognition.audioproviders.microphone.recordaudio;

import main.speechrecognition.audioproviders.microphone.recordaudio.event.RecordAudioEvent;

/**
 * Created by alvaro on 7/12/17.
 */
public interface RecordAudioObservable {
    void register(RecordAudioObserver observer);
    void unregister(RecordAudioObserver observer);
    void notifyObservers(RecordAudioEvent event);
}
