package vad.moannar.audio;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioProcessor;
import vad.moannar.VAD;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Created by alvaro on 7/9/17.
 */
public abstract class AudioProvider {
    public static final int SAMPLE_RATE = 44100;
    protected int size;
    protected AudioDispatcher dispatcher;

    public AudioProvider() {
        size = calculateFrameSize();
    }

    private int calculateFrameSize() {
        return (int) (SAMPLE_RATE * (VAD.FRAME_DURATION / 1000.0));
    }

    public abstract void createDispatcher() throws IOException, UnsupportedAudioFileException, LineUnavailableException;

    public void addAudioProcessor(AudioProcessor processor) {
        try {
            createDispatcher();
            dispatcher.addAudioProcessor(processor);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    public void process() {
        dispatcher.run();
    }

}
