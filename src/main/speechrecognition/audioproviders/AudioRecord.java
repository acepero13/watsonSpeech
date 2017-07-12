package main.speechrecognition.audioproviders;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.TargetDataLine;

/**
 * Created by alvaro on 7/12/17.
 */
public interface AudioRecord extends Audible {
    TargetDataLine getDataLine();
    AudioFormat getAudioFormat();
    AudioInputStream getAudioStream();
    void readData();

}
