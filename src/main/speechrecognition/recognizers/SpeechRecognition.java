package main.speechrecognition.recognizers;

import main.speechrecognition.notification.SpeechNotifier;
import main.speechrecognition.notification.SpeechObservable;
import main.speechrecognition.notification.SpeechObserver;

/**
 * Created by alvaro on 7/12/17.
 */
public abstract class SpeechRecognition implements SpeechObservable {


    protected  SpeechNotifier notifier;


    public SpeechRecognition(){
        notifier = new SpeechNotifier();
    }
    public SpeechRecognition(SpeechNotifier notifier){
        this.notifier = notifier;
    }

    public SpeechNotifier getNotifier() {
        return notifier;
    }

    @Override
    public void register(SpeechObserver observer) {
        notifier.register(observer);
    }

    @Override
    public void unregister(SpeechObserver observer) {
        notifier.unregister(observer);
    }

    @Override
    public void notifySpeechObservers(String spokenText) {
        notifier.notifySpeechObservers(spokenText);
    }

    public abstract boolean isListening();
    public abstract void startListening();

    public abstract void stopRecognition();
}
