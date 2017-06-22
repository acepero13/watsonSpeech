package main.voiceactivitydetector.observer;

/**
 * Created by alvaro on 6/22/17.
 */
public interface VoiceActivityObserver {
    void register(VoiceNotifiable notifiable);
    void unregister(VoiceNotifiable notifiable);
     void notifyDetection(boolean speaking);

}
