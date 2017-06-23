package main.speechrecognition.recognizers.watson.voiceactivated;

import main.speechrecognition.recognizers.watson.WatsonRecognition;
import main.voiceactivitydetector.VoiceActivityDetector;
import main.voiceactivitydetector.observer.VoiceNotifiable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by alvaro on 6/23/17.
 *
 * TODO: Make function: markToSpeecj, indicates explicitly that the user wants to talk and it should wait at least
 * 3 times what the usuar silence pause is
 */
public class WatsonVoiceActivated implements VoiceNotifiable {
    private static final int SILENCE_THRESHOLD_IN_SECONDS = 5;
    WatsonRecognition watsonRecognition;
    VoiceActivityDetector voiceActivityDetector;
    private boolean voiceDetected = false;
    private TimerTask silenceTask = new SilenceTask(this);

    public WatsonVoiceActivated(){
        voiceActivityDetector = new VoiceActivityDetector();
        voiceActivityDetector.register(this);

    }

    public void startListening(){
        startWatsonRecognition();
        voiceActivityDetector.startListening();
        startTimerSilenceTask();
    }

    private void startTimerSilenceTask() {
        Timer timer = new Timer();
        int milliseconds = SILENCE_THRESHOLD_IN_SECONDS * 1000;
        timer.schedule(silenceTask, milliseconds, milliseconds);
    }

    private void startWatsonRecognition() {
        watsonRecognition = new WatsonRecognition();
    }

    public void check() {
        if(noVoiceDetectedWithinTime()){
            watsonRecognition.stopRecognition();
            System.out.println("Pause of " + SILENCE_THRESHOLD_IN_SECONDS + " seconds without talking, stopping recognition");
        }
        voiceDetected = false;
    }

    private boolean noVoiceDetectedWithinTime() {
        return !voiceDetected;
    }

    @Override
    public void handleSpeakingActivity(boolean speaking) {
        if(speaking) {
            voiceDetected = true;
            startRecognitionIfStopped();
        }
    }

    private void startRecognitionIfStopped() {
        if(!watsonRecognition.isLstening()){
            System.out.println("Start recognition after voice activity....");
            startWatsonRecognition();
        }
    }
}
