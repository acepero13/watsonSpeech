package vad.moannar;

import be.tarsos.dsp.AudioProcessor;
import main.speechrecognition.audioproviders.Audible;
import vad.moannar.audio.AudioProvider;
import vad.moannar.audio.MicrophoneAudioProvider;
import vad.moannar.processors.VadProcessor;
import vad.observer.VoiceActivityObserver;
import vad.observer.VoiceNotifiable;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.TargetDataLine;

/**
 * Created by alvaro on 7/9/17.
 */
public class VAD implements VoiceActivityObserver, Audible, Runnable {
    public static final int FRAME_DURATION = 10;
    private final AudioProcessor vadProcessor;
    AudioProvider provider;

    public VAD() {
        provider = new MicrophoneAudioProvider();
        vadProcessor = new VadProcessor();
        provider.addAudioProcessor(vadProcessor);
    }

    public void process() {
        processAudio();
    }

    private void processAudio() {
        provider.process();
    }

    @Override
    public void register(VoiceNotifiable notifiable) {
        getAsObserver().register(notifiable);
    }

    private VoiceActivityObserver getAsObserver() {
        return (VoiceActivityObserver) vadProcessor;
    }

    @Override
    public void unregister(VoiceNotifiable notifiable) {
        getAsObserver().unregister(notifiable);
    }

    @Override
    public void notifyDetection(boolean speaking) {
    }


    @Override
    public void startListening() {
        (new Thread(this)).start();
    }



    @Override
    public void stopListening() {

    }



    @Override
    public void run() {
        process();
    }
}
