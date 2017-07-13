package main.speechrecognition.recognizers;

import main.speechrecognition.audioproviders.AudioRecord;
import main.speechrecognition.recognizers.google.GoogleRecognition;
import main.speechrecognition.recognizers.watson.WatsonRecognition;

/**
 * Created by alvaro on 7/12/17.
 */
public class RecognitionSystemFactory {
    private final String recognitionSystem;
    private final AudioRecord audioRecord;

    public RecognitionSystemFactory(String recognition, AudioRecord audible){
        this.recognitionSystem = recognition;
        this.audioRecord = audible;
    }

    public SpeechRecognition create(){
        if(recognitionSystem.equals("watson")){
            return createWatson();
        }else if(recognitionSystem.equals("google")){
            return createGoogle();
        }
        return createDefault();
    }

    private SpeechRecognition createDefault() {
        return new DummySpeechRecognition();
    }

    private SpeechRecognition createGoogle() {
        return new GoogleRecognition();
    }

    private SpeechRecognition createWatson() {
        if (audioRecord == null)
            return new WatsonRecognition();

        return new WatsonRecognition(audioRecord);
    }

    private class DummySpeechRecognition extends SpeechRecognition{

        @Override
        public boolean isListening() {
            return false;
        }

        @Override
        public void startListening() {

        }

        @Override
        public void stopRecognition() {

        }
    }
}
