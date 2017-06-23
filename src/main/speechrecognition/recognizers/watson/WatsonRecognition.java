package main.speechrecognition.recognizers.watson;

import com.ibm.watson.developer_cloud.http.HttpMediaType;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeCallback;
import main.speechrecognition.audioproviders.Audible;
import main.speechrecognition.audioproviders.Microphone;
import main.speechrecognition.parsers.ResultParser;
import main.speechrecognition.parsers.watson.WatsonParser;

/**
 * Created by alvaro on 6/22/17.
 *  .inactivityTimeout(5) // use this to stop listening when the speaker pauses, i.e. for 5s
 */
public class WatsonRecognition {

    WatsonConfiguration configuration;
    Audible audible;
    private SpeechToText service;
    private RecognizeOptions recognitionOptions;
    private final ResultParser parser;

    public boolean isLstening() {
        return isLstening;
    }

    private boolean isLstening = false;

    public WatsonRecognition(){
        configuration = new WatsonConfiguration();
        audible = new Microphone();
        audible.startListening();
        parser = new WatsonParser(true);
        createService();
        buildRecognitionObject();
        createListener();
    }


    public void stopRecognition(){
        audible.stopListening();
        isLstening = false;
    }

    private void createListener() {
        isLstening = true;
        service.recognizeUsingWebSocket(audible.getAudioStream(), recognitionOptions, new BaseRecognizeCallback() {
            @Override
            public void onTranscription(SpeechResults speechResults) {
               String parsed = parser.parse(speechResults);
               System.out.println(parsed);
            }
        });
    }

    private void buildRecognitionObject() {
        recognitionOptions = new RecognizeOptions.Builder()
                .continuous(true)
                .interimResults(true)
                .timestamps(true)
                .wordConfidence(true)
                .contentType(buildContentType())
                .build();
    }

    private String buildContentType() {
        return HttpMediaType.AUDIO_RAW + "; rate=" + Microphone.SAMPLE_RATE;
    }

    private void createService() {
        service = new SpeechToText();
        service.setUsernameAndPassword(configuration.getUsername(), configuration.getPassword());
        service.setEndPoint( configuration.getEndPoint());
    }

}
