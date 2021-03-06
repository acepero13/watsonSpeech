package vad.voiceactivitydetector.processors.energy;

import be.tarsos.dsp.AudioEvent;
import vad.voiceactivitydetector.VoiceActivated;
import vad.voiceactivitydetector.processors.VoiceActivityProcessor;

/**
 * Created by alvaro on 6/22/17.
 */
public class EnergyBased implements VoiceActivityProcessor {

    public static final int SILENCE_THRESHOLD = -60;
    private final VoiceActivated voiceActivated;
    private boolean silence;

    public EnergyBased(VoiceActivated voiceActivated) {
        this.voiceActivated = voiceActivated;
    }

    public boolean process(AudioEvent audioEvent) {
        silence = audioEvent.isSilence(SILENCE_THRESHOLD);
        voiceActivated.setEnergyActivity(!silence);
        return true;
    }

    public void processingFinished() {

    }

    public boolean isSpeaking() {
        return silence;
    }
}
