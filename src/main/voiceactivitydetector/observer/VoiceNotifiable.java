package main.voiceactivitydetector.observer;

/**
 * Created by alvaro on 6/22/17.
 */
public interface VoiceNotifiable {
    void handleSpeakingActivity(boolean speaking);
}
