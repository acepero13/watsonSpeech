package main.speechrecognition.recognizers.watson.voiceactivated;

import main.speechrecognition.audioproviders.Audible;
import main.speechrecognition.audioproviders.AudioRecord;
import main.speechrecognition.notification.SpeechObservable;
import main.speechrecognition.notification.SpeechObserver;
import main.speechrecognition.recognizers.watson.WatsonRecognition;
import vad.moannar.VAD;
import vad.observer.VoiceActivityObserver;
import vad.observer.VoiceNotifiable;
import vad.voiceactivitydetector.VoiceActivityDetector;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by alvaro on 6/23/17.
 * <p>
 * TODO: Make function: markToSpeecj, indicates explicitly that the user wants to talk and it should wait at least
 * 3 times what the usuar silence pause is
 */
public class WatsonVoiceActivated implements VoiceNotifiable, SpeechObservable {
    private static final int SILENCE_THRESHOLD_IN_SECONDS = 5;
    private final AudioRecord audible;
    WatsonRecognition watsonRecognition;
    VoiceActivityObserver voiceActivityDetector;
    private boolean voiceDetected = false;
    private TimerTask silenceTask = new SilenceTask(this);
    private LinkedList<SpeechObserver> cachedObservers = new LinkedList<>();
    static boolean forceAwake = false;


    public void forceAwake(){
        forceAwake = true;
    }

    public void disableForceAwake(){
        forceAwake = false;
    }

    public WatsonVoiceActivated() {
        voiceActivityDetector = new VAD();
        voiceActivityDetector.register(this);
        audible = null;
    }

    public WatsonVoiceActivated(AudioRecord audible) {
        voiceActivityDetector = new VoiceActivityDetector();
        voiceActivityDetector.register(this);
        this.audible = audible;

    }

    public void startListening() {
        createWatsonRecognition();
        restartRecognition();
        ((Audible)voiceActivityDetector).startListening();
        startTimerSilenceTask();
    }


    private void startTimerSilenceTask() {
        Timer timer = new Timer();
        int milliseconds = SILENCE_THRESHOLD_IN_SECONDS * 1000;
        timer.schedule(silenceTask, milliseconds, milliseconds);
    }

    private void createWatsonRecognition() {
        if (audible == null)
            watsonRecognition = new WatsonRecognition();
        else if(watsonRecognition == null)
            watsonRecognition = new WatsonRecognition(audible);
    }

    void check() {
        if (noVoiceDetectedWithinTime()) {
            watsonRecognition.stopRecognition();
            unregisterCachedObservers();
            System.out.println("Pause of " + SILENCE_THRESHOLD_IN_SECONDS + " seconds without talking, stopping recognition");
            createWatsonRecognition();
        }
        voiceDetected = false;
    }

    private void unregisterCachedObservers() {
        for (SpeechObserver observer : cachedObservers) {
            watsonRecognition.unregister(observer);
        }
    }

    private boolean noVoiceDetectedWithinTime() {
        return !voiceDetected && !forceAwake;
    }

    @Override
    public void handleSpeakingActivity(boolean speaking) {
        if (speaking) {
            voiceDetected = true;
            startRecognitionIfStopped();
        }
    }

    private void startRecognitionIfStopped() {
        if (!watsonRecognition.isLstening()) {
            System.out.println("Start recognition after voice activity....");
            restartRecognition();
        }
    }

    public void restartRecognition() {
        createWatsonRecognition();
        registerCachedObservers();
        watsonRecognition.startListening();
    }

    private void registerCachedObservers() {
        for (SpeechObserver observer : cachedObservers) {
            watsonRecognition.register(observer);
        }
    }

    @Override
    public void register(SpeechObserver observer) {
        watsonRecognition.register(observer);
        cachedObservers.add(observer);
    }

    @Override
    public void unregister(SpeechObserver observer) {
        watsonRecognition.unregister(observer);
        cachedObservers.remove(observer);
    }

    @Override
    public void notifySpeechObservers(String spokenText) {
        watsonRecognition.notifySpeechObservers(spokenText);
    }
}
