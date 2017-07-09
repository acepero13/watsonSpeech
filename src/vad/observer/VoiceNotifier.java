package vad.observer;

import java.util.LinkedList;

/**
 * Created by alvaro on 7/9/17.
 */
public class VoiceNotifier implements VoiceActivityObserver {
    private LinkedList<VoiceNotifiable> observers = new LinkedList<VoiceNotifiable>();

    public void register(VoiceNotifiable notifiable) {
        observers.add(notifiable);
    }

    public void unregister(VoiceNotifiable notifiable) {
        observers.remove(notifiable);
    }

    public void notifyDetection(boolean speaking) {
        for (VoiceNotifiable notifiable : observers) {
            notifiable.handleSpeakingActivity(speaking);
        }
    }
}
