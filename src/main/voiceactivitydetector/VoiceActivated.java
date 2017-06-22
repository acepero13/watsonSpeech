package main.voiceactivitydetector;

/**
 * Created by alvaro on 6/22/17.
 */
public interface VoiceActivated {
    void setEnergyActivity(boolean isSpeaking);
    void setPitchActivity(boolean isSpeaking);
    boolean isSpeaking();
}
