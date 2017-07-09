package vad.moannar.audio;

import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

/**
 * Created by alvaro on 7/9/17.
 */
public class FileAudioProvider extends AudioProvider {

    private final String filename;

    public FileAudioProvider(String filename) {
        super();
        this.filename = filename;
    }

    public void createDispatcher() throws IOException, UnsupportedAudioFileException {
        File audioFile = new File(filename);
        dispatcher = AudioDispatcherFactory.fromFile(audioFile, size, 0);
    }
}
