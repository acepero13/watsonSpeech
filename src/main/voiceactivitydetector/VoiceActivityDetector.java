package main.voiceactivitydetector;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import main.speechrecognition.audioproviders.Audible;
import main.voiceactivitydetector.processors.energy.EnergyBased;
import main.voiceactivitydetector.processors.pitch.PitchBased;
import main.voiceactivitydetector.processors.VoiceActivityProcessor;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import java.util.LinkedList;

/**
 * Created by alvaro on 6/22/17.
 */
public class VoiceActivityDetector implements Audible, VoiceActivated {
    public static final int BUFFER_SIZE = 441;
    private final VoiceNotifiable notifiable;
    LinkedList<VoiceActivityProcessor> featureStrategies = new LinkedList<VoiceActivityProcessor>();
    private AudioDispatcher dispatcher;
    private boolean isSpeakingAccordingToEnergy = false;
    private boolean isSpeakingAccordingToPitch = false;

    public VoiceActivityDetector(VoiceNotifiable notifiable){
        featureStrategies.add(new EnergyBased(this));
        featureStrategies.add(PitchBased.YIN_Default(this));
        this.notifiable = notifiable;
    }

    public void startListening() {
        try {
            listen();
            startProcessors();
            dispatcher.run();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void startProcessors() {
        for (AudioProcessor processor: featureStrategies) {
            dispatcher.addAudioProcessor(processor);
        }
    }

    private void listen() throws LineUnavailableException {
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(BUFFER_SIZE, 0);
    }

    public AudioInputStream getAudioStream() {
        return null;
    }

    public void stopListening() {

    }

    public void setEnergyActivity(boolean isSpeaking) {
        isSpeakingAccordingToEnergy = isSpeaking;
        notifiable.handleSpeakingActivity(isSpeaking());
    }

    public void setPitchActivity(boolean isSpeaking) {
        isSpeakingAccordingToPitch = isSpeaking;
        notifiable.handleSpeakingActivity(isSpeaking());
    }

    public boolean isSpeaking() {
        return isSpeakingAccordingToEnergy && isSpeakingAccordingToPitch;
    }
}
