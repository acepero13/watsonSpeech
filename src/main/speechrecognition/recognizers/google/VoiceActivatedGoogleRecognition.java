package main.speechrecognition.recognizers.google;

import main.speechrecognition.audioproviders.Audible;
import main.speechrecognition.notification.SpeechObservable;
import main.speechrecognition.notification.SpeechObserver;
import vad.moannar.VAD;
import vad.observer.VoiceNotifiable;

/**
 * Created by alvaro on 7/13/17.
 */
public class VoiceActivatedGoogleRecognition implements VoiceNotifiable, SpeechObservable {
    private final GoogleRecognition googleRecognition;
    private final VAD voiceActivityDetector;

    public  VoiceActivatedGoogleRecognition(){
        this.googleRecognition = new GoogleRecognition();
        voiceActivityDetector = new VAD(3,1000);
        voiceActivityDetector.register(this);
    }

    @Override
    public void handleSpeakingActivity(boolean speaking) {

    }

    @Override
    public void register(SpeechObserver observer) {
        googleRecognition.register(observer);
    }

    @Override
    public void unregister(SpeechObserver observer) {
        googleRecognition.unregister(observer);
    }

    @Override
    public void notifySpeechObservers(String spokenText) {
        googleRecognition.notifySpeechObservers(spokenText);
    }

    public void startListening() {
        voiceActivityDetector.startListening();
        googleRecognition.startListening();
    }
}
