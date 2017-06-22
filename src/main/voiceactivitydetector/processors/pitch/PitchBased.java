package main.voiceactivitydetector.processors.pitch;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchProcessor;
import main.voiceactivitydetector.VoiceActivityDetector;
import main.voiceactivitydetector.processors.VoiceActivityProcessor;

/**
 * Created by alvaro on 6/22/17.
 */
public class PitchBased extends PitchProcessor implements VoiceActivityProcessor {

    public static final int SAMPLE_RATE = 44100;
    private Boolean speaking = false;

    public PitchBased(PitchEstimationAlgorithm algorithm, float sampleRate, int bufferSize, PitchDetectionHandler handler) {
        super(algorithm, sampleRate, bufferSize, handler);
    }

    public static PitchBased YIN_Default(VoiceActivityDetector voiceActivityDetector){
        return new PitchBased(PitchEstimationAlgorithm.YIN, SAMPLE_RATE,
                VoiceActivityDetector.BUFFER_SIZE,
                new PitchHandler(voiceActivityDetector));

    }

    public boolean process(AudioEvent audioEvent) {
        return super.process(audioEvent);
    }

    public void processingFinished() {
        super.processingFinished();
    }

    public boolean isSpeaking() {
        return speaking;
    }

    public void markAsSpeaking() {
        this.speaking = true;
    }
}
