package main.speechrecognition.recognizers.voiceactivated;

import main.speechrecognition.notification.SpeechObserver;

/**
 * Created by alvaro on 7/12/17.
 */
public class WatsonVoiceActivatedWrapper  {
    private static SpeechRecognitionVoiceActivated activated = new SpeechRecognitionVoiceActivated();

    public static void startListening(){
        activated.startListening();
    }

    public static  void register(SpeechObserver observer){
        activated.register(observer);
    }

    public static void unregister(SpeechObserver observer){
        activated.unregister(observer);
    }

    public static void forceStart(){
        activated.forceAwake();
    }

    public static void stopAwake(){
        activated.disableForceAwake();
    }



}
