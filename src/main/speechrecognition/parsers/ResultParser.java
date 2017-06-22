package main.speechrecognition.parsers;

import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;

/**
 * Created by alvaro on 6/22/17.
 */
public interface ResultParser {
    String parse(SpeechResults speechResults);
}
