package main.speechrecognition.audioproviders.microphone.recordaudio;

import main.speechrecognition.audioproviders.microphone.recordaudio.event.RecordAudioEvent;

/**
 * Created by alvaro on 7/12/17.
 */
public interface RecordAudioObserver {
    void update(RecordAudioEvent event);
}
