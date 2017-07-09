package main.speechrecognition.audioproviders;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.TargetDataLine;

/**
 * Created by alvaro on 6/22/17.
 */
public interface Audible {
    void startListening();

    AudioInputStream getAudioStream();

    void stopListening();

    TargetDataLine getDataLine();

    AudioFormat getAudioFormat();
}
