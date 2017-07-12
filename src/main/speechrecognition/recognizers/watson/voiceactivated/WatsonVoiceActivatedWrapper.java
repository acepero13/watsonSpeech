package main.speechrecognition.recognizers.watson.voiceactivated;

import main.speechrecognition.notification.WatsonSpeechObservable;
import main.speechrecognition.notification.WatsonSpeechObserver;
import vad.observer.VoiceNotifiable;

/**
 * Created by alvaro on 7/12/17.
 */
public class WatsonVoiceActivatedWrapper  {
    private static WatsonVoiceActivated activated = new WatsonVoiceActivated();

    public static void startListening(){
        activated.startListening();
    }

    public static  void register(WatsonSpeechObserver observer){
        activated.register(observer);
    }

    public static void unregister(WatsonSpeechObserver observer){
        activated.unregister(observer);
    }

    public static void forceStart(){
        activated.forceAwake();
    }

    public static void stopAwake(){
        activated.disableForceAwake();
    }



}
