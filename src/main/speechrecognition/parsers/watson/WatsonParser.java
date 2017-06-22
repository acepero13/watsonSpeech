package main.speechrecognition.parsers.watson;

import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.Transcript;
import main.speechrecognition.parsers.ResultParser;

/**
 * Created by alvaro on 6/22/17.
 */
public class WatsonParser implements ResultParser {
    private final boolean onlyRespondToFinal;

    public WatsonParser(boolean onlyRespondToFinal) {
        this.onlyRespondToFinal = onlyRespondToFinal;
    }

    public WatsonParser() {
        onlyRespondToFinal = false;
    }

    public String parse(SpeechResults speechResults) {
        StringBuilder finalText = new StringBuilder();
        for (Transcript result: speechResults.getResults()) {
            if(!result.isFinal() && onlyRespondToFinal){
                continue;
            }else {
                finalText.append(getSpokenText(result));
            }
        }
        return finalText.toString();
    }

    private String getSpokenText(Transcript result) {
        if(!result.getAlternatives().isEmpty())
            return result.getAlternatives().get(0).getTranscript();//Return the first
        return "";
    }
}
