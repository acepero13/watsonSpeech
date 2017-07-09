package vad.moannar;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import vad.moannar.audio.AudioProvider;
import vad.moannar.audio.FileAudioProvider;
import vad.moannar.audio.MicrophoneAudioProvider;
import vad.moannar.processors.VadProcessor;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * Created by alvaro on 7/9/17.
 */
public class VAD {
    public static final int FRAME_DURATION = 10;
    AudioProvider provider;

    public VAD(){
        provider = new MicrophoneAudioProvider();
        AudioProcessor vadProcessor = new VadProcessor();
        provider.addAudioProcessor(vadProcessor);
    }

    public void process(){
        processAudio();
    }

    private void processAudio() {
        provider.process();
    }

}
