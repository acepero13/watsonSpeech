package main.speechrecognition.recognizers.voiceactivated;

import java.util.TimerTask;

/**
 * Created by alvaro on 6/23/17.
 */
public class SilenceTask extends TimerTask {
    private final SpeechRecognitionVoiceActivated speechRecognitionVoiceActivated;

    public SilenceTask(SpeechRecognitionVoiceActivated speechRecognitionVoiceActivated) {
        this.speechRecognitionVoiceActivated = speechRecognitionVoiceActivated;
    }

    @Override
    public void run() {
        speechRecognitionVoiceActivated.check();
    }
}
