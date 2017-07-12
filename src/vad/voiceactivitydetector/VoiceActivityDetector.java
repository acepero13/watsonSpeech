package vad.voiceactivitydetector;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import main.speechrecognition.audioproviders.Audible;
import vad.observer.VoiceActivityObserver;
import vad.observer.VoiceNotifiable;
import vad.observer.VoiceNotifier;
import vad.voiceactivitydetector.processors.VoiceActivityProcessor;
import vad.voiceactivitydetector.processors.energy.EnergyBased;
import vad.voiceactivitydetector.processors.pitch.PitchBased;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.util.LinkedList;

/**
 * Created by alvaro on 6/22/17.
 */
public class VoiceActivityDetector implements Audible, VoiceActivated, VoiceActivityObserver, Runnable {
    public static final int BUFFER_SIZE = 441;
    LinkedList<VoiceActivityProcessor> featureStrategies = new LinkedList<VoiceActivityProcessor>();
    private AudioDispatcher dispatcher;
    private boolean isSpeakingAccordingToEnergy = false;
    private boolean isSpeakingAccordingToPitch = false;
    private VoiceNotifier notifier;


    public VoiceActivityDetector() {
        featureStrategies.add(new EnergyBased(this));
        featureStrategies.add(PitchBased.YIN_Default(this));
        notifier = new VoiceNotifier();
    }

    public void startListening() {
        new Thread(this).start();
    }

    private void init() throws LineUnavailableException {
        listen();
        startProcessors();
        dispatcher.run();
    }

    private void startProcessors() {
        for (AudioProcessor processor : featureStrategies) {
            dispatcher.addAudioProcessor(processor);
        }
    }

    private void listen() throws LineUnavailableException {
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(BUFFER_SIZE, 0);
    }

    public AudioInputStream getAudioStream() {
        return null;
    }

    @Override
    public void stopListening() {

    }


    public void setEnergyActivity(boolean isSpeaking) {
        isSpeakingAccordingToEnergy = isSpeaking;
        notifyDetection(isSpeaking());
    }

    public void setPitchActivity(boolean isSpeaking) {
        isSpeakingAccordingToPitch = isSpeaking;
        notifyDetection(isSpeaking());
    }

    public boolean isSpeaking() {
        return isSpeakingAccordingToEnergy && isSpeakingAccordingToPitch;
    }

    public void register(VoiceNotifiable notifiable) {
        notifier.register(notifiable);
    }

    public void unregister(VoiceNotifiable notifiable) {
        notifier.unregister(notifiable);
    }

    public void notifyDetection(boolean speaking) {
        notifier.notifyDetection(speaking);
    }

    @Override
    public void run() {
        try {
            init();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
