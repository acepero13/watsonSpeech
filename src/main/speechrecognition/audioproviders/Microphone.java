package main.speechrecognition.audioproviders;

import javax.sound.sampled.*;

/**
 * Created by alvaro on 6/22/17.
 */
public class Microphone implements Audible {

    public static final int SAMPLE_RATE = 16000;
    public static final int SAMPLE_SIZE_IN_BITS = 16;
    public static final int CHANNELS = 1;
    private DataLine.Info info;
    private TargetDataLine line;
    private AudioInputStream audio;
    private AudioFormat format;

    public Microphone() {

    }

    public void startListening() {
        try {
            initMicrophone();
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void start() {
        line.start();
        audio = new AudioInputStream(line);
    }

    private void initMicrophone() throws Exception {
        AudioFormat format = createAudioFormatFromLine();
        checkForSupportedLine();
        openLine(format);
    }

    private void openLine(AudioFormat format) throws LineUnavailableException {
        line = (TargetDataLine) AudioSystem.getLine(info);
        line.open(format);
    }

    private AudioFormat createAudioFormatFromLine() {
        format = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, true, false);
        info = new DataLine.Info(TargetDataLine.class, format);
        return format;
    }

    private void checkForSupportedLine() throws Exception {
        if (!AudioSystem.isLineSupported(info)) {
            throw new Exception("Line is not supported");
        }
    }

    public AudioInputStream getAudioStream() {
        return audio;
    }

    public void stopListening() {
        line.stop();
        line.close();
    }

    @Override
    public TargetDataLine getDataLine() {
        return line;
    }

    @Override
    public AudioFormat getAudioFormat() {
        return format;
    }
}
