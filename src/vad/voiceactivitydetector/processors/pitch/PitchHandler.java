package vad.voiceactivitydetector.processors.pitch;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import vad.voiceactivitydetector.VoiceActivityDetector;

/**
 * Created by alvaro on 6/22/17.
 */
public class PitchHandler implements PitchDetectionHandler {

    private final VoiceActivityDetector context;

    PitchHandler(VoiceActivityDetector context){
        this.context = context;
    }
    public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
        if(pitchDetectionResult.getPitch() != -1) {
            double timeStamp = audioEvent.getTimeStamp();
            float pitch = pitchDetectionResult.getPitch();
            float probability = pitchDetectionResult.getProbability();
            if (probability > 0) {
                context.setPitchActivity(true);
                return;
            }
        }
        context.setPitchActivity(false);
    }
}
