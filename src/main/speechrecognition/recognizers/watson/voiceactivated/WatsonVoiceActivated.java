package main.speechrecognition.recognizers.watson.voiceactivated;

import main.speechrecognition.recognizers.watson.WatsonRecognition;
import main.voiceactivitydetector.VoiceActivityDetector;
import main.voiceactivitydetector.observer.VoiceNotifiable;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by alvaro on 6/23/17.
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
        voiceActivityDetector.startListening();
        startWatsonRecognition();
        startTimerSilenceTask();
    }

    private void startTimerSilenceTask() {
        Timer timer = new Timer();
        timer.schedule(silenceTask, 0, SILENCE_THRESHOLD_IN_SECONDS * 1000);
    }

    private void startWatsonRecognition() {
        System.out.println("Recognizing speech!!!, Activating recognition");
        watsonRecognition = new WatsonRecognition();
        //
    }

    public void check() {
        if(noVoiceDetectedWithinTime()){
            watsonRecognition.stopRecognition();
            System.out.println("No voice detected in " + SILENCE_THRESHOLD_IN_SECONDS + " seconds, stopping recognition");
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
            startWatsonRecognition();
        }
    }
}
