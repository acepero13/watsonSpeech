package main.speechrecognition.audioproviders.microphone;

import main.speechrecognition.audioproviders.AudioRecord;
import main.speechrecognition.audioproviders.microphone.recordaudio.event.MicrophoneOnReadEvent;
import main.speechrecognition.audioproviders.microphone.recordaudio.event.RecordAudioEvent;
import main.speechrecognition.audioproviders.microphone.recordaudio.RecordAudioNotifier;
import main.speechrecognition.audioproviders.microphone.recordaudio.RecordAudioObservable;
import main.speechrecognition.audioproviders.microphone.recordaudio.RecordAudioObserver;

import javax.sound.sampled.*;

/**
 * Created by alvaro on 6/22/17.
 */
public class Microphone implements AudioRecord, RecordAudioObservable, Runnable {

    public static final int SAMPLE_RATE = 16000;
    public static final int SAMPLE_SIZE_IN_BITS = 16;
    public static final int CHANNELS = 1;
    private DataLine.Info info;
    private TargetDataLine line;
    private AudioInputStream audio;
    private AudioFormat format;
    private RecordAudioNotifier notifier;
    private boolean isListening = false;

    public Microphone() {
        notifier = new RecordAudioNotifier();
    }

    public void startListening() {
        try {
            initMicrophone();
            start();
            isListening = true;
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
        isListening = false;
    }

    @Override
    public TargetDataLine getDataLine() {
        return line;
    }

    @Override
    public AudioFormat getAudioFormat() {
        return format;
    }

    @Override
    public void readData() {
        while (isListening){
            byte[] data = new byte[line.getBufferSize() / 5];
            int countRead = line.read(data, 0, data.length);
            RecordAudioEvent event = new MicrophoneOnReadEvent(data);
            notifyObservers(event);
        }
    }

    @Override
    public void register(RecordAudioObserver observer) {
        notifier.register(observer);
    }

    @Override
    public void unregister(RecordAudioObserver observer) {
        notifier.unregister(observer);
    }

    @Override
    public void notifyObservers(RecordAudioEvent event) {
        notifier.notifyObservers(event);
    }

    @Override
    public void run() {
        startListening();
        readData();
    }
}
