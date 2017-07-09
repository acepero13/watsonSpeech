package vad.moannar.audio;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Created by alvaro on 7/9/17.
 */
public class MicrophoneAudioProvider extends AudioProvider {
    public MicrophoneAudioProvider(){
        super();
    }

    @Override
    public void createDispatcher() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(size, 0);
    }
}
