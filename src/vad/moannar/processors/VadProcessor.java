package vad.moannar.processors;

import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import vad.moannar.features.Features;
import vad.moannar.features.MinFeatures;
import vad.observer.VoiceActivityObserver;
import vad.observer.VoiceNotifiable;
import vad.observer.VoiceNotifier;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by alvaro on 7/9/17.
 */
public class VadProcessor implements AudioProcessor, VoiceActivityObserver {

    public static final int THIRTY_FRAME_MARK = 30;
    private final VoiceNotifier notifier;

    private int  frameCounter = 0;
    private Features features;
    private HashMap<Integer, Boolean> voicedFrame = new HashMap<>();
    private MinFeatures minFeatures = new MinFeatures();
    private int speechStreak = 0;
    private int silenceStreak = 0;

    public VadProcessor(){
        notifier = new VoiceNotifier();
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
        if(featureCounter > 1){
            voicedFrame.put(frameCounter, true);
            speechStreak++;
            silenceStreak = 0;
        }else{
            silenceStreak++;
            speechStreak = 0;
            voicedFrame.put(frameCounter, false);
        }
    }

    private void notifySpeech() {
        if(speechStreak > 5){
            notifier.notifyDetection(true);
        }
        if(silenceStreak > 10){
            notifier.notifyDetection(false);
        }
    }

    private void createFeatures(AudioEvent audioEvent) {
        if(frameCounter == 0)
            features = new Features(audioEvent, true, minFeatures);
        else
            features = new Features(audioEvent, minFeatures);
    }

    private int calculateFeatures() {
        int frameCounter;
        if(isFirstThirtyFrames()){
            frameCounter = features.calculateMinimum();
        }else{
            frameCounter = features.calculate();
        }
        return frameCounter;
    }


    private boolean isFirstThirtyFrames() {
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
