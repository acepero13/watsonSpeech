package vad.voiceactivitydetector.processors;

import be.tarsos.dsp.AudioProcessor;

/**
 * Created by alvaro on 6/22/17.
 */
public interface VoiceActivityProcessor extends AudioProcessor {
    boolean isSpeaking();
}
