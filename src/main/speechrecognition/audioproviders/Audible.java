package main.speechrecognition.audioproviders;

import javax.sound.sampled.AudioInputStream;

/**
 * Created by alvaro on 6/22/17.
 */
public interface Audible {
    void startListening();
    AudioInputStream getAudioStream();
    void stopListening();
}
