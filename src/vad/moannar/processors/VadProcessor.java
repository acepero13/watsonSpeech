package vad.moannar.processors;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import vad.moannar.features.Features;
import vad.moannar.features.MinFeatures;
import vad.observer.VoiceActivityObserver;
import vad.observer.VoiceNotifiable;
import vad.observer.VoiceNotifier;

import java.util.HashMap;

/**
 * Created by alvaro on 7/9/17.
 */
public class VadProcessor implements AudioProcessor, VoiceActivityObserver {

    public static final int THIRTY_FRAME_MARK = 30;
    public static final int SPEECH_THRESHOLD = 5;
    public static final int SILENCE_THRESHOLD = 20;
    private final VoiceNotifier notifier;
    private final int consecutiveSpeechFramesThreshold;
    private final int consecutiveSilenceFramesThreshold;

    private int frameCounter = 0;
    private Features features;
    private HashMap<Integer, Boolean> voicedFrame = new HashMap<>();
    private MinFeatures minFeatures = new MinFeatures();
    private int speechStreak = 0;
    private int silenceStreak = 0;

    public VadProcessor() {
        notifier = new VoiceNotifier();
        consecutiveSpeechFramesThreshold = SPEECH_THRESHOLD;
        consecutiveSilenceFramesThreshold = SILENCE_THRESHOLD;
    }

    public VadProcessor(int consecutiveSpeechFramesThreshold, int consecutiveSilenceFramesThreshold) {
        notifier = new VoiceNotifier();
        this.consecutiveSpeechFramesThreshold = consecutiveSpeechFramesThreshold;
        this.consecutiveSilenceFramesThreshold = consecutiveSilenceFramesThreshold;
    }


    @Override
    public boolean process(AudioEvent audioEvent) {
        createFeatures(audioEvent);
        int featureCounter = calculateFeatures();
        markSpeechFrame(featureCounter);
        notifySpeech();
        frameCounter++;
        return true;
    }

    private void markSpeechFrame(int featureCounter) {
        if (featureCounter > 1) {
            voicedFrame.put(frameCounter, true);
            speechStreak++;
            silenceStreak = 0;
        } else {
            silenceStreak++;
            speechStreak = 0;
            voicedFrame.put(frameCounter, false);
        }
    }

    private void notifySpeech() {
        if (speechStreak > consecutiveSpeechFramesThreshold) {
            notifier.notifyDetection(true);
        }
        if (silenceStreak > consecutiveSilenceFramesThreshold) {
            notifier.notifyDetection(false);
        }
    }

    private void createFeatures(AudioEvent audioEvent) {
        if (frameCounter == 0)
            features = new Features(audioEvent, true, minFeatures);
        else
            features = new Features(audioEvent, minFeatures);
    }

    private int calculateFeatures() {
        int frameCounter;
        if (isFrameForThresholdInitialization()) {
            frameCounter = features.calculateMinimum();
        } else {
            frameCounter = features.calculate();
        }
        return frameCounter;
    }


    private boolean isFrameForThresholdInitialization() {
        return frameCounter < THIRTY_FRAME_MARK;
    }

    @Override
    public void processingFinished() {
    }

    @Override
    public void register(VoiceNotifiable notifiable) {
        notifier.register(notifiable);
    }

    @Override
    public void unregister(VoiceNotifiable notifiable) {
        notifier.unregister(notifiable);
    }

    @Override
    public void notifyDetection(boolean speaking) {
        notifier.notifyDetection(speaking);
    }
}
