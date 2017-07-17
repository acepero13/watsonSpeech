package main.speechrecognition.recognizers.voiceactivated;

import main.speechrecognition.audioproviders.AudioRecord;
import main.speechrecognition.recognizers.SpeechRecognition;
import main.speechrecognition.recognizers.google.GoogleRecognition;
import main.speechrecognition.recognizers.watson.WatsonRecognition;

/**
 * Created by alvaro on 7/17/17.
 */
public class SpeechRecognitionFactory {
    private final String speechRecognitionName;
    private final AudioRecord audible;

    public SpeechRecognitionFactory(String speechRecognition){
        this.speechRecognitionName = speechRecognition;
        audible = null;
    }

    public SpeechRecognitionFactory(String speechRecognition, AudioRecord audible){
        this.audible = audible;
        this.speechRecognitionName = speechRecognition;
    }

    public SpeechRecognition create(){
        if(speechRecognitionName.equalsIgnoreCase("watson")){
            return createWatsonRecognition();
        }
        return createGoogleRecognition();


    }

    private SpeechRecognition createWatsonRecognition() {
        if (audible == null)
            return new WatsonRecognition();
        else
            return new WatsonRecognition(audible);
    }

    private SpeechRecognition createGoogleRecognition() {
        return new GoogleRecognition();

    }


}
