package main.speechrecognition.recognizers.google;

import com.google.api.gax.grpc.ApiStreamObserver;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.StreamingRecognitionResult;
import com.google.cloud.speech.v1.StreamingRecognizeResponse;
import main.speechrecognition.notification.SpeechNotifier;
import main.speechrecognition.notification.SpeechObservable;
import main.speechrecognition.notification.SpeechObserver;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by alvaro on 7/12/17.
 */
public class GoogleResponseApiStreamingObserver <T> implements ApiStreamObserver<T>, SpeechObservable {
    private final List<T> messages = new java.util.ArrayList<T>();
    private  SpeechNotifier notifier;
    private final GoogleRecognition recognition;
    private boolean isProcessed;

    public GoogleResponseApiStreamingObserver(GoogleRecognition recognition, SpeechNotifier notifier) {
        this.notifier = notifier;
        this.recognition = recognition;
    }

    public SpeechNotifier getNotifier(){
        return notifier;
    }

    public void setNotifier(SpeechNotifier notifier){
        this.notifier = notifier;
    }

    @Override
    public void onNext(T message) {
        isProcessed = false;
        messages.add(message);
        String spokenText = parseResults((StreamingRecognizeResponse) message);
        if(!spokenText.isEmpty()) {
            notifySpeechObservers(spokenText);
            onCompleted();
            this.recognition.restart();
        }

    }

    private String parseResults(StreamingRecognizeResponse message) {
        StringBuilder spokenText = new StringBuilder();
        for (StreamingRecognitionResult result: message.getResultsList()) {
            for (SpeechRecognitionAlternative alternative:result.getAlternativesList()) {
                spokenText.append(alternative.getTranscript());
            }
        }
        return spokenText.toString();
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onCompleted() {
        isProcessed = true;
    }

    public boolean hasBeenProcessed(){
        return isProcessed;
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
}
