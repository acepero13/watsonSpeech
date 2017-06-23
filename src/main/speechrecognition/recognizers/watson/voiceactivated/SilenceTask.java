package main.speechrecognition.recognizers.watson.voiceactivated;

import java.util.TimerTask;

/**
 * Created by alvaro on 6/23/17.
 */
public class SilenceTask extends TimerTask {
    private final WatsonVoiceActivated watsonVoiceActivated;

    public SilenceTask(WatsonVoiceActivated watsonVoiceActivated){
        this.watsonVoiceActivated = watsonVoiceActivated;
    }

    @Override
    public void run() {
        watsonVoiceActivated.check();
    }
}
