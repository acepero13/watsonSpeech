package main.speechrecognition.recognizers.voiceactivated;

import main.speechrecognition.audioproviders.Audible;
import main.speechrecognition.audioproviders.AudioRecord;
import main.speechrecognition.notification.SpeechObservable;
import main.speechrecognition.notification.SpeechObserver;
import main.speechrecognition.recognizers.SpeechRecognition;
import main.speechrecognition.recognizers.google.GoogleRecognition;
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
public class SpeechRecognitionVoiceActivated implements VoiceNotifiable, SpeechObservable {
    private static final int SILENCE_THRESHOLD_IN_SECONDS = 5;
    private final AudioRecord audible;
    private final String speechRecognitionType;
    SpeechRecognition speechRecognition;
    VoiceActivityObserver voiceActivityDetector;
    private boolean voiceDetected = false;
    private TimerTask silenceTask = new SilenceTask(this);
    private LinkedList<SpeechObserver> cachedObservers = new LinkedList<>();
    static boolean forceAwake = false;
    private boolean starting = false;
    private SpeechRecognitionFactory speechRecognitionFactory;




    public void forceAwake(){
        forceAwake = true;
    }

    public void disableForceAwake(){
        forceAwake = false;
    }

    public SpeechRecognitionVoiceActivated() {

        audible = null;
        speechRecognitionType = "google";
        init();
    }

    public SpeechRecognitionVoiceActivated(String speechRecognitionType) {

        audible = null;
        this.speechRecognitionType = speechRecognitionType;
        init();
    }

    public SpeechRecognitionVoiceActivated(AudioRecord audible, String speechRecognitionType) {

        this.audible = audible;
        this.speechRecognitionType = speechRecognitionType;
        init();
    }

    private void init() {
        voiceActivityDetector = new VAD();
        voiceActivityDetector.register(this);
        speechRecognitionFactory = new SpeechRecognitionFactory(speechRecognitionType);
    }

    public void startListening() {
        //createRecognition();
        restartRecognition();
        ((Audible)voiceActivityDetector).startListening();
        startTimerSilenceTask();
    }


    private void startTimerSilenceTask() {
        Timer timer = new Timer();
        int milliseconds = SILENCE_THRESHOLD_IN_SECONDS * 1000;
        timer.schedule(silenceTask, milliseconds, milliseconds);
    }


    void check() {
        if (noVoiceDetectedWithinTime()) {
            speechRecognition.stopRecognition();
            unregisterCachedObservers();
            System.out.println("Pause of " + SILENCE_THRESHOLD_IN_SECONDS + " seconds without talking, stopping recognition");
            //createRecognition();
        }
        voiceDetected = false;
    }

    private void unregisterCachedObservers() {
        for (SpeechObserver observer : cachedObservers) {
            speechRecognition.unregister(observer);
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

    private synchronized void startRecognitionIfStopped() {
        if (!speechRecognition.isListening() && !starting) {
            this.starting = true;
            System.out.println("Start recognition after voice activity....");
            restartRecognition();
            starting = false;
        }
    }

    public void restartRecognition() {
        createRecognition();
        registerCachedObservers();
        speechRecognition.startListening();
    }

    private void createRecognition() {
        speechRecognition  = speechRecognitionFactory.create();
    }

    private void registerCachedObservers() {
        for (SpeechObserver observer : cachedObservers) {
            speechRecognition.register(observer);
        }
    }

    @Override
    public void register(SpeechObserver observer) {
        speechRecognition.register(observer);
        cachedObservers.add(observer);
    }

    @Override
    public void unregister(SpeechObserver observer) {
        speechRecognition.unregister(observer);
        cachedObservers.remove(observer);
    }

    @Override
    public void notifySpeechObservers(String spokenText) {
        speechRecognition.notifySpeechObservers(spokenText);
    }
}
